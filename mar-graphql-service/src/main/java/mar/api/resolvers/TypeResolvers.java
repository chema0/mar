package mar.api.resolvers;

import graphql.language.InlineFragment;
import graphql.language.Selection;
import graphql.schema.TypeResolver;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class TypeResolvers {

    public TypeResolver InterfaceResolver() {
        return env -> {
            List<Selection> selections = env.getField().getSingleField().getSelectionSet().getSelections();

            Optional<Selection> selection = selections
                    .stream()
                    .filter(s -> s instanceof InlineFragment)
                    .findFirst();

            if (selection.isPresent()) {
                InlineFragment inlineFragment = (InlineFragment) selection.get();

                return env.getSchema()
                        .getObjectType(inlineFragment.getTypeCondition().getName());
            }

            return env.getSchema().getObjectType("EcoreStats");
        };
    };

}
