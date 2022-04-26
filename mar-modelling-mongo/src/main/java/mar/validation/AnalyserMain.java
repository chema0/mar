package mar.validation;

import mar.indexer.common.cmd.CmdOptions;
import mar.indexer.common.configuration.IndexJobConfigurationData;
import mar.indexer.common.configuration.SingleIndexJob;
import mar.ingestion.CrawlerDB;
import mar.models.service.ModelsService;
import mar.validation.ISingleFileAnalyser.Remote;
import mar.validation.ResourceAnalyser.Factory;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * An application to validate crawled models according to some validator(s) and
 * output the information that can be used by the indexers. Uses Spring to inject
 * dependencies for service layer.
 *
 * @author jesus
 * @author chema
 */
@SpringBootApplication
@ComponentScan(basePackages = "mar")
@Command(name = "analyser", mixinStandardHelpOptions = true, description = "Analyses files according to a given validator")
public class AnalyserMain implements Callable<Integer> {

    @Parameters(index = "0", description = "The configuration file.")
    private File configurationFile;

    @Option(required = true, names = {"-t", "--type"}, description = "The model type: ecore, bpmn2, uml")
    private String type;

    @Option(required = false, names = {"-r",
            "--repository"}, description = "A specific repository in the configuration file")
    private String repository = null;

    @Option(required = false, names = {"-mode"}, description = "Can be: plain, remote, resilient")
    private String mode = "remote";

    @Option(required = false, names = {"-parallel"}, description = "Use parallel mode")
    private Integer parallel;

    ModelsService modelsService;

    public AnalyserMain(ModelsService modelsService) {
        this.modelsService = modelsService;
    }

    @Override
    public Integer call() throws Exception {
        IndexJobConfigurationData configuration = CmdOptions
                .readConfiguration("file:/" + configurationFile.getAbsolutePath());

        Factory factory = AnalyserRegistry.INSTANCE.getFactory(type);
        if (factory == null) {
            showAvailableAnalysers();
            return -1;
        }

        factory.configureEnvironment();

        List<SingleIndexJob> repositories;
        if (repository != null) {
            SingleIndexJob repo = configuration.getRepo(repository);
            repositories = Collections.singletonList(repo);
        } else {
            repositories = configuration.getRepositoriesOfType(type);
        }

        AnalysisDB outputAnalysisDB = new AnalysisDB(modelsService);

        for (SingleIndexJob repo : repositories) {

            CrawlerDB crawler = new CrawlerDB(type, repo.getOrigin(), repo.getRootFolder(), repo.getCrawlerDbFile());

            if (mode.equals("plain")) {
                ISingleFileAnalyser singleAnalyser = factory.newAnalyser();
                try (ResourceAnalyser analyser = new ResourceAnalyser(singleAnalyser, new IFileProvider.DBFileProvider(crawler), type, outputAnalysisDB)) {
                    analyser.check();
                }
            } else if (mode.equals("resilient")) {
                ISingleFileAnalyser singleAnalyser = factory.newResilientAnalyser();
                try (ResourceAnalyser analyser = new ResourceAnalyser(singleAnalyser, new IFileProvider.DBFileProvider(crawler), type, outputAnalysisDB)) {
                    if (parallel != null && parallel > 1)
                        analyser.withParallelThreads(parallel);
                    analyser.check();
                }
            } else if (mode.equals("remote")) {
                try (ISingleFileAnalyser.Remote singleAnalyser = (Remote) factory.newRemoteAnalyser()) {
                    try (ResourceAnalyser analyser = new ResourceAnalyser(singleAnalyser, new IFileProvider.DBFileProvider(crawler), type, outputAnalysisDB)) {
                        if (parallel != null && parallel > 1)
                            analyser.withParallelThreads(parallel);
                        analyser.check();
                    }
                }
            } else {
                System.out.println("Invalid mode " + mode + ". " + "Available are: plain, remote, resilient.");
                return -1;
            }
        }

        return 0;
    }

    public static void main(String[] args) {
        SpringApplication.run(AnalyserMain.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            ModelsService service = null;

            try {
                service = (ModelsService) ctx.getBean("modelsService");
            } catch (NoSuchBeanDefinitionException e) {
                e.printStackTrace();
                System.exit(-1);
            }

            int exitCode = new CommandLine(new AnalyserMain(service)).execute(args);
            System.exit(exitCode);
        };
    }

    private static void showAvailableAnalysers() {
        System.out.println("Available analysers: ");
        AnalyserRegistry.INSTANCE.forEach((str, factory_) -> {
            System.out.println("  " + str);
        });
    }

}
