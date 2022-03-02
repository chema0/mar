package mar;

import mar.mongodb.beans.Model;
import mar.mongodb.beans.Type;
import mar.mongodb.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQlDataFetchers {

    @Autowired
    private ModelService modelService;

    public Model getModel(String id) {
        return modelService.findModelByModelId(id);
    }

    public Iterable<Model> getModels(int first, Type type) {
        return modelService.findModelsByType(first, type);
    }
}
