package mar.api;

import graphql.schema.DataFetchingFieldSelectionSet;
import graphql.schema.SelectedField;
import mar.models.model.LogicalFilter;
import mar.models.model.Model;
import mar.models.model.NameFilter;
import mar.models.model.Type;
import mar.models.service.ModelsService;
import mar.api.utils.FiltersParser;
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

        // String keyword = null;

        // if (selectionSet.contains("metadata/name")) {
            // Object value = selectionSet.getFields("metadata/name")
                    // .get(0)
                    // .getArguments()
                    // .get("keyword");

            // if (value != null) {
                // keyword = value.toString();
            // }
        // }

        List<LogicalFilter> statsFilters = parser.filtersFromStats(selectionSet.getFields("stats/*"));

        List<NameFilter> elementsFilters = parser.filtersFromElements(selectionSet.getFields("elements/*"));

        boolean isFiltering = statsFilters.size() > 0 || elementsFilters.size() > 0;

        if (isFiltering) {
            return modelsService.findModelsByTypeWithFilters(first, type,
                    statsFilters.size() > 0 ? statsFilters : null,
                    elementsFilters.size() > 0 ? elementsFilters : null);
        }

        if (type != null) {
            return modelsService.findModelsByType(first, type);
        }

        return modelsService.findAllModels(first);
    }

}
