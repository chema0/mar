package mar.validation;

import mar.models.model.Metadata;
import mar.models.model.Model;
import mar.models.model.Status;
import mar.models.model.Type;
import mar.models.service.ModelsService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

public class AnalysisDB implements Closeable {

    ModelsService modelsService;

    @Nonnull
    private Map<String, Status> alreadyChecked = new HashMap<>();

    public AnalysisDB(ModelsService modelsService) {
        this.modelsService = modelsService;

        List<Model> allModels = modelsService.findModelsExcludingByStatus(Status.NOT_PROCESSED);
        allModels.forEach(m -> alreadyChecked.put(m.getModelId(), m.getStatus()));

        modelsService.deleteAllModelsByStatus(Status.NOT_PROCESSED);
    }

    @Override
    // TODO: check
    public void close() {

    }

    @CheckForNull
    public Status addFile(@Nonnull String modelId, @Nonnull Type type, @Nonnull String relativeName, @Nonnull String hash) {
        AtomicReference<Status> status = new AtomicReference<>(Status.NOT_PROCESSED);

        // We check that the hash is not repeated
        String originalModelId = null;
        List<Model> duplicatedModels = modelsService.findModelsByHashNotDuplicated(hash);

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

        modelsService.insertModel(model);

        return status.get();
    }

    @CheckForNull
    public Status hasFile(@Nonnull String modelId) {
        return alreadyChecked.get(modelId);
    }

    public void updateStatus(@Nonnull String modelId, @Nonnull Status status) {
        modelsService.updateModelStatus(modelId, status);
    }

    public void addProperties(@Nonnull String modelId, @Nonnull Map<String, Integer> stats, @Nonnull Metadata elements,
                              @Nonnull Map<String, List<String>> metamodel) {
        modelsService.updateModelProperties(modelId, stats, elements, metamodel);
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