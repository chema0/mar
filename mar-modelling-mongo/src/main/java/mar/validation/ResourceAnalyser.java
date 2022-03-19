package mar.validation;

import com.google.common.io.BaseEncoding;
import mar.modelling.loader.ILoader;
import mar.mongodb.beans.Metadata;
import mar.mongodb.beans.Status;
import mar.mongodb.beans.Type;
import mar.validation.server.RemoteModelAnalyser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.security.MessageDigest;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Analyse file resources against provided validators.
 *
 * @author jesus
 */
public class ResourceAnalyser implements AutoCloseable {

    private static final Logger LOG = LogManager.getLogger(ResourceAnalyser.class);

    @Nonnull
    private final IFileProvider fileProvider;
    @Nonnull
    private final AnalysisDB validationDB;
    @Nonnull
    private final ISingleFileAnalyser analyser;
    @Nonnull
    private final Type type;

    private int parallelThreads = -1;

    public static enum Option {

    }

    public static class OptionMap extends HashMap<String, String> {
        private static final long serialVersionUID = 4847913571593080424L;

        @Nonnull
        public String getOption(String key) {
            if (!containsKey(key))
                throw new IllegalArgumentException("No key " + key);
            return get(key);
        }
    }

    public static interface Factory {
        public void configureEnvironment();

        public ISingleFileAnalyser newAnalyser(@CheckForNull OptionMap options);

        public ILoader newLoader();

        public String getId();

        public default ISingleFileAnalyser newResilientAnalyser(@CheckForNull OptionMap options) {
            return new ResilientAnalyser(newAnalyser(options));
        }

        public default ISingleFileAnalyser newAnalyser() {
            return newAnalyser(null);
        }

        public default ISingleFileAnalyser newRemoteAnalyser() {
            return newRemoteAnalyser(null);
        }

        public default ISingleFileAnalyser newResilientAnalyser() {
            return newResilientAnalyser(null);
        }

        public default ISingleFileAnalyser newRemoteAnalyser(@CheckForNull OptionMap options) {
            return new RemoteModelAnalyser(getId(), options);
        }
    }

    public ResourceAnalyser(@Nonnull ISingleFileAnalyser analyser, @Nonnull IFileProvider fileProvider, @Nonnull String type, @Nonnull File resultDB) {
        this.fileProvider = fileProvider;
        this.analyser = analyser;
        this.validationDB = new AnalysisDB();
        this.type = Type.valueOf(type.toUpperCase());
    }

    public ResourceAnalyser withParallelThreads(int parallelThreads) {
        this.parallelThreads = parallelThreads;
        // Don't do this and let the ValidationDB be inconsistent if it fails in the middle
        // In case, we probably just want to delete the file and fix the problem
        // this.validationDB.setAutocommit(isParallel);
        return this;
    }

    public boolean isParallel() {
        return parallelThreads > 1;
    }

    @Override
    public void close() throws Exception {
        this.validationDB.close();
    }


    public void check() throws IOException {
        List<? extends IFileInfo> localFiles = fileProvider.getLocalFiles();
        if (isParallel()) {
            ForkJoinPool myPool = new ForkJoinPool(parallelThreads);
            try {
                myPool.submit(() ->
                        localFiles.parallelStream().forEach(f -> checkFile(f, validationDB))
                ).get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        } else {
            for (IFileInfo f : localFiles) {
                checkFile(f, validationDB);
            }
        }
    }

    private void checkFile(@Nonnull IFileInfo f, @Nonnull AnalysisDB validationDB) {
        if (ignore(f)) {
            return;
        }

        if (validationDB.hasFile(f.getModelId()) != null) {
            System.out.println("Skipping " + f.getAbsolutePath());
            // TODO: We can have a flag to retry for some cases
            return;
        }

        String hash = toHash(f.getFullFile());
        if (hash == null) {
            System.out.println("Can't process " + f.getAbsolutePath());
            // TODO: Record in DB
            return;
        }

        Status status = validationDB.addFile(f.getModelId(), type, f.getRelativePath(), hash);
        switch (status) {
            case DUPLICATED:
                System.out.println("Duplicated: " + f.getAbsolutePath());
                return;
            case NOT_PROCESSED:
                // Inserted ok
                break;
            default:
                throw new IllegalStateException();
        }

        try {
            LOG.info("Processing " + f.getAbsolutePath());
            System.out.println("Processing " + f.getAbsolutePath());
            this.process(f, validationDB);
        } catch (Throwable e) {
            e.printStackTrace();
            LOG.error(e);
            validationDB.updateStatus(f.getModelId(), Status.NOT_HANDLED);
        }

    }

    private final AtomicInteger processCounter = new AtomicInteger(0);

    private void process(@Nonnull IFileInfo file, @Nonnull AnalysisDB validationDB) {
        AnalysisResult r = analyser.analyse(file);
        validationDB.updateStatus(r.getModelId(), r.getStatus());

        Map<String, Integer> stats = r.getStats();

        /* Map<String, List<String>> metadata = r.getMetadata();
        if (metadata != null) {
            metadata.forEach((type, values) -> {
                values.forEach(value -> {
                    validationDB.addMetadata(r.getModelId(), type, value);
                });
            });
        } */

        Metadata metadata = new Metadata();
        AnalysisMetadataDocument document = null;
        if (r.getJsonMetadata() != null) {
            document = AnalysisMetadataDocument.loadFromJSON(r.getJsonMetadata());
            metadata.setName(document.getExplicitName());
            metadata.setDescription(document.getDescription());
        }

        Map<String, List<String>> metamodel = r.getMetamodel();

        validationDB.addProperties(r.getModelId(), stats, metadata, metamodel);

        // TODO: check this
        // if (processCounter.incrementAndGet() % 100 == 0) {
           // validationDB.commit();
        // }
    }

    protected boolean ignore(IFileInfo f) {
        return false;
    }

    @CheckForNull
    private String toHash(@Nonnull File file) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] contents = Files.readAllBytes(file.getAbsoluteFile().toPath());
            md.update(contents);
            byte[] digest = md.digest();
            String myHash = BaseEncoding.base16().lowerCase().encode(digest).toUpperCase();
            // String myHash = DatatypeConverter.printHexBinary(digest).toUpperCase(); // No longer available in Java 11
            return myHash;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
