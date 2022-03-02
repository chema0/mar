package mar.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import mar.mongodb.beans.Model;
import mar.mongodb.beans.Type;
import mar.mongodb.services.ModelService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {

    private final ModelService modelService;

    public Model model(String id) {
        return modelService.findModelByModelId(id);
    }

    public Iterable<Model> models(int limit, Type type) {
        return modelService.findModelsByType(limit, type);
    }

}
