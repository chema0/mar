package mar;

import graphql.schema.DataFetchingFieldSelectionSet;
import mar.models.model.LogicalFilter;
import mar.models.model.Model;
import mar.models.model.NameFilter;
import mar.models.model.Type;
import mar.models.service.ModelsService;
import mar.utils.FiltersParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class GraphQlController {

    @Autowired
    private ModelsService modelsService;

    FiltersParser parser = FiltersParser.getInstance();

    @QueryMapping
    public Model model(@Argument String id) {
        return modelsService.findModelByModelId(id);
    }

    @QueryMapping
    public Iterable<Model> models(@Argument Integer first, @Argument Type type, DataFetchingFieldSelectionSet selectionSet) {

        List<LogicalFilter> statsFilters = parser.filtersFromStats(selectionSet.getFields("stats/*"));

        List<NameFilter> elementsFilters = parser.filtersFromElements(selectionSet.getFields("elements/*"));

        if (statsFilters.size() > 0 || elementsFilters.size() > 0) {
            return modelsService.findModelsByTypeWithFilters(first, type,
                    statsFilters.size() > 0 ? statsFilters : null,
                    elementsFilters.size() > 0 ? elementsFilters : null);
        }

        return modelsService.findModelsByType(first, type);
    }

}
