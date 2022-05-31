package mar.api.resolvers;

import graphql.language.Field;
import graphql.language.InlineFragment;
import graphql.language.Selection;
import graphql.schema.TypeResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class TypeResolvers {

    public TypeResolver interfaceResolver() {
        return env -> {
            List<Selection> selections = env.getField().getSingleField().getSelectionSet().getSelections();

            Map<String, Integer> resultObject = env.getObject();

            Optional<Selection> selection = selections
                    .stream()
                    .filter(s -> {
                        if (s instanceof InlineFragment) {
                            InlineFragment fragment = (InlineFragment) s;

                            return fragment.getSelectionSet().getSelections()
                                    .stream()
                                    .anyMatch(field ->
                                            resultObject.get(((Field) field).getName()) != null
                                    );
                        }

                        return false;
                    })
                    .findFirst();

            if (selection.isPresent()) {
                InlineFragment inlineFragment = (InlineFragment) selection.get();

                return env.getSchema()
                        .getObjectType(inlineFragment.getTypeCondition().getName());
            }

            return env.getSchema().getObjectType("EcoreStats");
        };
    }

    ;

}
