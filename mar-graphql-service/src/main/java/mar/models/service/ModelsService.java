package mar.models.service;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.lang.Nullable;
import mar.models.model.*;
import mar.models.repository.IModelRepository;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ModelsService {

    @Autowired
    private MongoClient mongoClient;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private IModelRepository modelRepository;

    public Model insertModel(Model model) {
        return modelRepository.insert(model);
    }

    public Model findModelByModelId(String id) {
        return modelRepository.findByModelId(id);
    }

    public Iterable<Model> findAllModels(int limit) {
        return modelRepository.findAll(Pageable.ofSize(limit));
    }

    public List<Model> findModelsExcludingByStatus(Status status) {
        return modelRepository.findByStatusNot(status);
    }

    public List<Model> findModelsByStatus(Status status) {
        return modelRepository.findByStatus(status);
    }

    public List<Model> findModelsByHashNotDuplicated(String hash) {
        return modelRepository.findByHashAndDuplicateOfIsNull(hash);
    }

    public Iterable<Model> findModelsByType(int limit, Type type) {
        return modelRepository.findByType(type, Pageable.ofSize(limit));
    }

    public Iterable<Model> findModelsByTypeWithFilters(int limit, Type type,
                                                       List<LogicalFilter> logicalFilters, List<NameFilter> nameFilters) {

        Document query = new Document();

        if (type != null) {
            query.append("type", type.name());
            // query.addCriteria(Criteria.where("type").is(type));
        }

        if (logicalFilters != null) {
            query.append("$and",
                    logicalFilters.stream()
                            .map(logicalFilter -> new Document("stats." + logicalFilter.getElement(),
                                    new Document("$" + logicalFilter.getOperator(), logicalFilter.getValue())))
                            .collect(Collectors.toList()));
        }

        if (nameFilters != null) {
            query.append("$and",
                    nameFilters.stream()
                            .map(nameFilter -> new Document("elements." + nameFilter.getElement(),
                                    new Document("$in", nameFilter.getNames())))
                            .collect(Collectors.toList()));
        }

        // Access to Mongo and filter the documents using the Mongo cursor

        MongoCollection<Model> collection = mongoTemplate
                .getCollection("models")
                .withDocumentClass(Model.class);

        return collection.find(query).limit(limit);
    }

    @Nullable
    public Model updateModelStatus(String modelId, Status status) {
        Model model = modelRepository.findByModelId(modelId);

        if (model != null) {
            model.setStatus(status);
            return modelRepository.save(model);
        }

        return null;
    }

    @Nullable
    public Model updateModelProperties(String modelId, Map<String, Integer> stats, Metadata metadata,
                                       Map<String, List<String>> metamodel) {
        Model model = modelRepository.findByModelId(modelId);

        if (model != null) {
            model.setStats(stats);
            model.setMetadata(metadata);
            model.setElements(metamodel);
            return modelRepository.save(model);
        }

        return null;
    }

    public List<Model> deleteAllModelsByStatus(Status status) {
        return modelRepository.deleteByStatus(status);
    }
}
