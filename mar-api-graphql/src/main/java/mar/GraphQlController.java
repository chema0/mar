package mar;

import mar.mongodb.beans.Model;
import mar.mongodb.beans.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

@Controller
public class GraphQlController {

    @Autowired
    GraphQlDataFetchers dataFetchers;

    @QueryMapping
    public Model model(@Argument String id) {
        return dataFetchers.getModel(id);
    }

    @QueryMapping
    public Iterable<Model> models(@Argument Integer first, @Argument Type type) {
        return dataFetchers.getModels(first, type);
    }
}
