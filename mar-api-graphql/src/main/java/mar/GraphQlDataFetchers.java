package mar;

import graphql.schema.DataFetcher;
import mar.mongodb.beans.Model;
import mar.mongodb.beans.Type;
import mar.mongodb.services.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GraphQlDataFetchers {

    @Autowired
    private ModelService modelService;

    public DataFetcher getModelDataFetcher() {
        return dataFetchingEnvironment -> {
            String id = dataFetchingEnvironment.getArgument("id");
            return modelService.findModelByModelId(id);
        };
    }

    public DataFetcher getModelsDataFetcher() {
        return dataFetchingEnvironment -> {
            int first = dataFetchingEnvironment.getArgument("first");
            Type type = Type.valueOf(dataFetchingEnvironment.getArgument("type"));
            return modelService.findModelsByType(first, type);
        };
    }

    public DataFetcher getEcoreMetamodelDataFetcher() {
        return dataFetchingEnvironment -> {
            String keyword = dataFetchingEnvironment.getArgument("keyword");
            System.out.println(keyword);
            return null;
        };
    }

    public Model getModel(String id) {
        return modelService.findModelByModelId(id);
    }

    public Iterable<Model> getModels(int first, Type type) {
        return modelService.findModelsByType(first, type);
    }
}
