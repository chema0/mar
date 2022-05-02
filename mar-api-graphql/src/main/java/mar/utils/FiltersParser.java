package mar.utils;

import graphql.schema.SelectedField;
import mar.models.model.LogicalFilter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FiltersParser {

    private static FiltersParser instance;

    public static FiltersParser getInstance() {
        if (instance == null) {
            instance = new FiltersParser();
        }

        return instance;
    }

    public List<LogicalFilter> filtersFromStats(List<SelectedField> fields) {

        List<LogicalFilter> logicalFilters = new ArrayList<>();

        fields.forEach(f -> {
            Map<String, Object> args = f.getArguments();

            if (args.containsKey("filter")) {
                LinkedHashMap input = (LinkedHashMap) args.get("filter");

                logicalFilters.add(new LogicalFilter(f.getName(),
                        "$" + input.get("op").toString().toLowerCase(), (Integer) input.get("value")));
            }
        });

        return logicalFilters;
    }

}
