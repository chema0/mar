package mar.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.com.google.common.collect.Iterables;
import graphql.schema.SelectedField;
import mar.models.model.LogicalFilter;
import mar.models.model.NameFilter;
import mar.api.inputs.LogicalFilterInput;
import mar.api.inputs.NameFilterInput;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FiltersParser {

    private static FiltersParser instance;

    private final ObjectMapper mapper = new ObjectMapper();

    public static FiltersParser getInstance() {
        if (instance == null) {
            instance = new FiltersParser();
        }

        return instance;
    }

    public Map<String, List<LogicalFilter>> filtersFromStats(List<SelectedField> fields) {

        Map<String, List<LogicalFilter>> filters = new HashMap<>();

        fields.forEach(f -> {
            Map<String, Object> args = f.getArguments();

            String[] splittedField = f.getFullyQualifiedName().split("\\.");
            String type = splittedField[1];

            if (args.containsKey("filter")) {
                LogicalFilterInput input = mapper.convertValue(args.get("filter"), LogicalFilterInput.class);

                filters.putIfAbsent(type, new ArrayList<>());

                List<LogicalFilter> values = filters.get(type);
                values.add(new LogicalFilter(f.getName(), input.getOp().name().toLowerCase(), input.getValue()));

                filters.put(type, values);
            }
        });

        return filters;
    }

    public Map<String, List<NameFilter>> filtersFromElements(List<SelectedField> fields) {

        Map<String, List<NameFilter>> filters = new HashMap<>();

        fields.forEach(f -> {
            Map<String, Object> args = f.getArguments();

            String[] splittedField = f.getFullyQualifiedName().split("\\.");
            String type = splittedField[1];

            if (args.containsKey("filter")) {
                NameFilterInput input = mapper.convertValue(args.get("filter"), NameFilterInput.class);

                filters.putIfAbsent(type, new ArrayList<>());

                List<NameFilter> values = filters.get(type);
                values.add(new NameFilter(f.getName(), input.getValues()));

                filters.put(type, values);
            }
        });

        return filters;
    }

}
