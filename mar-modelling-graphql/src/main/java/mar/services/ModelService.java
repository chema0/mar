package mar.services;

import com.mongodb.lang.Nullable;
import mar.bean.Model;
import mar.bean.ModelInfo;
import mar.bean.ModelStat;
import mar.bean.Status;
import mar.mongodb.repository.ModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    };

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
    public Model updateModelProperties(String modelId, ModelInfo modelInfo, List<ModelStat> modelStats) {
        Model model = modelRepository.findByModelId(modelId);

        if (model != null) {
            model.setInfo(modelInfo);
            model.setStats(modelStats);
            return modelRepository.save(model);
        }

        return null;
    }

    /* DELETE */

    public List<Model> deleteAllModelsByStatus(Status status) {
        return modelRepository.deleteByStatus(status);
    }
}
