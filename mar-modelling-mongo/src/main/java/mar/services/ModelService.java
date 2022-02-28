package mar.services;

import com.google.common.collect.Multimap;
import com.mongodb.lang.Nullable;
import mar.beans.*;
import mar.mongodb.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    /* CREATE */

    public Model insertModel(Model model) {
        return modelRepository.insert(model);
    }

    /* READ */

    public List<Model> findModelsExcludingByStatus(Status status) {
        return modelRepository.findExcludingByStatus(status);
    }

    ;

    public List<Model> findModelsByStatus(Status status) {
        return modelRepository.findByStatus(status);
    }

    public List<Model> findModelsByHashNotDuplicated(String hash) {
        return modelRepository.findByHashNotDuplicated(hash);
    }

    /* UPDATE */

    @Nullable
    public Model updateModelStatus(String modelId, Status status) {
        Model model = modelRepository.findByModelId(modelId);

        if (model != null) {
            model.setStatus(status);
            return modelRepository.save(model);
        }

        return null;
    }

    @Nullable
    public Model updateModelProperties(String modelId, Map<String, Integer> stats, Metadata metadata,
                                       Map<String, List<String>> metamodel) {
        Model model = modelRepository.findByModelId(modelId);

        if (model != null) {
            model.setStats(stats);
            model.setMetadata(metadata);
            model.setMetamodel(metamodel);
            return modelRepository.save(model);
        }

        return null;
    }

    /* DELETE */

    public List<Model> deleteAllModelsByStatus(Status status) {
        return modelRepository.deleteByStatus(status);
    }
}
