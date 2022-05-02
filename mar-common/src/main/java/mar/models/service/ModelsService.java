package mar.models.service;

import com.mongodb.lang.Nullable;
import mar.models.model.Metadata;
import mar.models.model.Model;
import mar.models.model.Status;
import mar.models.model.Type;
import mar.models.repository.IModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ModelsService {

    @Autowired
    private IModelRepository modelRepository;

    public Model insertModel(Model model) {
        return modelRepository.insert(model);
    }

    public Model findModelByModelId(String id) {
        return modelRepository.findByModelId(id);
    }

    public List<Model> findModelsExcludingByStatus(Status status) {
        return modelRepository.findExcludingByStatus(status);
    }

    public List<Model> findModelsByStatus(Status status) {
        return modelRepository.findByStatus(status);
    }

    public List<Model> findModelsByHashNotDuplicated(String hash) {
        return modelRepository.findByHashNotDuplicated(hash);
    }

    public Iterable<Model> findModelsByType(int limit, Type type) {
        Pageable pageable = PageRequest.of(1, limit);
        return modelRepository.findByType(type, pageable);
    }

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
            model.setElements(metamodel);
            return modelRepository.save(model);
        }

        return null;
    }

    public List<Model> deleteAllModelsByStatus(Status status) {
        return modelRepository.deleteByStatus(status);
    }
}
