package mar.validation;

import com.google.common.base.Preconditions;
import com.google.common.collect.Iterables;
import com.google.common.collect.Multimap;
import mar.beans.*;
import mar.services.ModelService;

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

        // TODO: handle errors in database connection
        modelService = AnalyserMain.modelService;

        List<Model> allModels = modelService.findModelsExcludingByStatus(Status.NOT_PROCESSED);
        allModels.forEach(m -> alreadyChecked.put(m.getModelId(), m.getStatus()));

        modelService.deleteAllModelsByStatus(Status.NOT_PROCESSED);
    }

    public void setAutocommit(boolean autocommit) {
        try {
            this.connection.setAutoCommit(autocommit);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
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

    public void commit() {
        try {
            if (!connection.getAutoCommit())
                connection.commit();
        } catch (SQLException e) {
            // throw new IllegalStateException(e);
        }
    }

    @Nonnull
    public static String getConnectionString(File file) {
        return "jdbc:sqlite:" + file.getAbsolutePath();
    }

    @Nonnull
    public static String getValidModelsQuery() {
        return "select relative_file, id, metadata_document from models where status = '" + Status.VALID.name() + "' or status = '" + Status.NO_VALIDATE.name() + "'";
    }

    @Nonnull
    public List<String> getValidModels() throws SQLException {
        PreparedStatement statement = connection.prepareStatement(getValidModelsQuery());
        statement.execute();

        List<String> result = new ArrayList<>();
        ResultSet rs = statement.getResultSet();
        while (rs.next()) {
            result.add(rs.getString(1));
        }

        return result;
    }

//    public List<Model> getValidModels(@Nonnull Function<String, String> relativePathTransformer) throws SQLException {
//        List<Model> models = new ArrayList<>(1024);
//        PreparedStatement statement = connection.prepareStatement(getValidModelsQuery());
//        statement.execute();
//
//        ResultSet rs = statement.getResultSet();
//        while (rs.next()) {
//            String id = rs.getString(2);
//            File file = new File(relativePathTransformer.apply(rs.getString(1)));
//            String metadata = rs.getString(3);
//            models.add(new Model(id, file, metadata));
//        }
//
//        return models;
//    }

    @CheckForNull
    public Status addFile(@Nonnull String modelId, @Nonnull Type type, @Nonnull String relativeName, @Nonnull String hash) {
        AtomicReference<Status> status = new AtomicReference<>(Status.NOT_PROCESSED);

        // We check that the hash is not repeated
        String originalModelId = null;
        List<Model> duplicatedModels = modelService.findModelsByHashNotDuplicated(hash);

        if (duplicatedModels.size() > 0) {
            originalModelId = Iterables.getLast(duplicatedModels).getModelId();
            status.set(Status.DUPLICATED);
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

    @CheckForNull
    private Status getStatus(@Nonnull String modelId) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT status FROM models WHERE id = ?");
            stm.setString(1, modelId);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (!rs.next())
                return null;
            return Status.valueOf(rs.getString(1));
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @CheckForNull
    public String getRelativeFile(@Nonnull String modelId) {
        try {
            PreparedStatement stm = connection.prepareStatement("SELECT relative_file FROM models WHERE id = ?");
            stm.setString(1, modelId);
            stm.execute();
            ResultSet rs = stm.getResultSet();
            if (!rs.next())
                return null;
            return rs.getString(1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateStatus(@Nonnull String modelId, @Nonnull Status status) {
        modelService.updateModelStatus(modelId, status);
    }

    public void updateMetadata(@Nonnull String modelId, @CheckForNull String jsonDocument) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE models SET metadata_document = ? WHERE id = ?");
            preparedStatement.setString(1, jsonDocument);
            preparedStatement.setString(2, modelId);
            int count = preparedStatement.executeUpdate();
            Preconditions.checkState(count == 1);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addProperties(@Nonnull String modelId, @Nonnull Map<String, Integer> stats, @Nonnull Metadata metadata,
                              @Nonnull Map<String, List<String>> metamodel) {
        modelService.updateModelProperties(modelId, stats, metadata, metamodel);
    }

//    @Nonnull
//    public List<Model> findByMetadata(@Nonnull String key, @Nonnull String
//            value, @Nonnull Function<String, String> relativePathTransformer) {
//        try (PreparedStatement stm = connection.prepareStatement("SELECT m.id, relative_file, metadata_document FROM models m, metadata mm WHERE m.id = mm.id AND m.status IN ('VALID', 'INVALID') AND mm.type = ? AND mm.value = ?")) {
//            stm.setString(1, key);
//            stm.setString(2, value);
//            stm.execute();
//
//            List<Model> result = new ArrayList<>();
//            ResultSet rs = stm.getResultSet();
//            while (rs.next()) {
//                String id = rs.getString(1);
//                File relativeFile = new File(relativePathTransformer.apply(rs.getString(2)));
//                String metadataDocument = rs.getString(3);
//                result.add(new Model(id, relativeFile, metadataDocument));
//            }
//            return result;
//        } catch (SQLException e) {
//            throw new RuntimeException(e);
//        }
//    }

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