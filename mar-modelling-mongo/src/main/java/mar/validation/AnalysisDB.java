package mar.validation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import mar.mongodb.beans.Metadata;
import mar.mongodb.beans.Model;
import mar.mongodb.beans.Status;
import mar.mongodb.beans.Type;
import mar.mongodb.services.ModelService;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AnalysisDB implements Closeable {

    @Nonnull
    private Connection connection;
    private ModelService modelService;
    @Nonnull
    private Map<String, Status> alreadyChecked = new HashMap<>();

    public AnalysisDB() {
        // FIXME: change this
        modelService = AnalyserMain.modelService;

        List<Model> allModels = modelService.findModelsExcludingByStatus(Status.NOT_PROCESSED);
        allModels.forEach(m -> alreadyChecked.put(m.getModelId(), m.getStatus()));

        modelService.deleteAllModelsByStatus(Status.NOT_PROCESSED);
    }

    @Override
    public void close() throws IOException {
        if (connection != null) {
            try {
                if (!connection.getAutoCommit())
                    connection.commit();
                connection.close();
            } catch (SQLException e) {
                throw new IOException(e);
            }
        }
    }

    @CheckForNull
    public Status addFile(@Nonnull String modelId, @Nonnull Type type, @Nonnull String relativeName, @Nonnull String hash) {
        AtomicReference<Status> status = new AtomicReference<>(Status.NOT_PROCESSED);

        // We check that the hash is not repeated
        String originalModelId = null;
        List<Model> duplicatedModels = modelService.findModelsByHashNotDuplicated(hash);

        if (duplicatedModels.size() > 0) {
            return Status.DUPLICATED;
        }

        // We can insert
        Model model = Model.builder()
                .modelId(modelId)
                .type(type)
                .relativeFile(relativeName)
                .hash(hash)
                .status(status.get())
                .duplicateOf(originalModelId)
                .build();

        modelService.insertModel(model);

        return status.get();
    }

    @CheckForNull
    public Status hasFile(@Nonnull String modelId) {
        return alreadyChecked.get(modelId);
    }

    public void updateStatus(@Nonnull String modelId, @Nonnull Status status) {
        modelService.updateModelStatus(modelId, status);
    }

    public void addProperties(@Nonnull String modelId, @Nonnull Map<String, Integer> stats, @Nonnull Metadata metadata,
                              @Nonnull Map<String, List<String>> metamodel) {
        modelService.updateModelProperties(modelId, stats, metadata, metamodel);
    }

    public static class AnalysisModel {
        @Nonnull
        private String id;
        @Nonnull
        private File file;
        @Nonnull
        private String metadata;

        public AnalysisModel(@Nonnull String id, @Nonnull File file, String metadata) {
            this.id = id;
            this.file = file;
            this.metadata = metadata;
        }


        public String getId() {
            return id;
        }

        @Nonnull
        public File getFile() {
            return file;
        }

        public String getMetadata() {
            return metadata;
        }
    }

}