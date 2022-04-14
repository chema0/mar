package mar;

import mar.models.model.Model;
import mar.models.model.Type;
import mar.models.service.ModelsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQlDataFetchers {

    @Autowired
    private ModelsService modelsService;

    public Model getModel(String id) {
        return modelsService.findModelByModelId(id);
    }

    public Iterable<Model> getModels(int first, Type type) {
        return modelsService.findModelsByType(first, type);
    }
}
