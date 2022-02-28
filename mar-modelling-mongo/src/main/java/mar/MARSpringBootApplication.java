package mar;

import mar.mongodb.services.ModelService;
import mar.validation.AnalyserMain;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MARSpringBootApplication implements CommandLineRunner {

    @Autowired
    ModelService modelService;

    public static void main(String[] args) {
        SpringApplication.run(MARSpringBootApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        AnalyserMain.main(args, modelService);
    }
}