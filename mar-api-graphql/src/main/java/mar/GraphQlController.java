package mar;

import graphql.schema.DataFetchingEnvironment;
import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.SelectedField;
import mar.mongodb.beans.Model;
import mar.mongodb.beans.Type;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.Map;

@Controller
public class GraphQlController {

    @Autowired
    GraphQlDataFetchers dataFetchers;

    @QueryMapping
    public Model model(@Argument String id) {
        return dataFetchers.getModel(id);
    }

    @QueryMapping
     public Iterable<Model> models(@Argument Integer first, @Argument Type type, DataFetchingEnvironment env, DataFetchingFieldSelectionSet selectionSet) {
        List<SelectedField> fields = selectionSet.getFields("metamodel/packages");

        if (fields.size() > 0) {
            SelectedField packages = fields.get(0);
            Map<String, Object> arguments = packages.getArguments();
            System.out.println("Packages keyword: " + arguments.get("keyword"));
        }

        return dataFetchers.getModels(first, type);
    }

}
