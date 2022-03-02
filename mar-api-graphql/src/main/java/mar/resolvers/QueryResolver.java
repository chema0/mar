package mar.resolvers;

import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import mar.mongodb.beans.Model;
import mar.mongodb.beans.Type;
import mar.mongodb.repository.ModelRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
@RequiredArgsConstructor
public class QueryResolver implements GraphQLQueryResolver {

    private final ModelRepository modelRepository;

    public Model model(String id) {
        return modelRepository.findByModelId(id);
    }

    public Iterable<Model> models(int limit, Type type) {
        Pageable pageable = PageRequest.of(1, limit);
        return modelRepository.findByType(type, pageable);
    }

}
