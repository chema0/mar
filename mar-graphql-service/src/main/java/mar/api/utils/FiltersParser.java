package mar.api.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import graphql.schema.SelectedField;
import mar.models.model.LogicalFilter;
import mar.models.model.NameFilter;
import mar.api.inputs.LogicalFilterInput;
import mar.api.inputs.NameFilterInput;

import java.util.ArrayList;
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

    public List<LogicalFilter> filtersFromStats(List<SelectedField> fields) {

        List<LogicalFilter> filters = new ArrayList<>();

        fields.forEach(f -> {
            Map<String, Object> args = f.getArguments();

            if (args.containsKey("filter")) {
                LogicalFilterInput input = mapper.convertValue(args.get("filter"), LogicalFilterInput.class);

                filters.add(new LogicalFilter(f.getName(),
                        input.getOp().name().toLowerCase(), input.getValue()));
            }
        });

        return filters;
    }

    public List<NameFilter> filtersFromElements(List<SelectedField> fields) {

        List<NameFilter> filters = new ArrayList<>();

        fields.forEach(f -> {
            Map<String, Object> args = f.getArguments();

            if (args.containsKey("filter")) {
                NameFilterInput input = mapper.convertValue(args.get("filter"), NameFilterInput.class);

                filters.add(new NameFilter(f.getName(),
                        input.getValues()));
            }
        });

        return filters;
    }

}
