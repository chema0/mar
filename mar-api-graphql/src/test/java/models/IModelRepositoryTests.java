package models;

import mar.GraphqlAPIApplication;
import mar.models.model.Model;
import mar.models.model.Status;
import mar.models.model.Type;
import mar.models.repository.IModelRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ContextConfiguration(classes = GraphqlAPIApplication.class)
@DataMongoTest
public class IModelRepositoryTests {

    // Default Pageable object
    private final Pageable pageable = Pageable.ofSize(5);

    // Default hash code
    private final String hash = String.valueOf(hashCode());

    @Autowired
    private IModelRepository repository;

    @BeforeEach
    void init() {
        repository.deleteAll();

        Model model;

        model = Model.builder()
                .modelId("genmymodel:ecore:/_test")
                .type(Type.ECORE)
                .relativeFile("_test.ecore")
                .hash(hash)
                .status(Status.VALID)
                .duplicateOf(null)
                .build();

        repository.insert(model);

        model = Model.builder()
                .modelId("genmymodel:ecore:/_test_1")
                .type(Type.ECORE)
                .relativeFile("_test_1.ecore")
                .hash(hash)
                .status(Status.VALID)
                .duplicateOf(null)
                .build();

        repository.insert(model);

        model = Model.builder()
                .modelId("genmymodel:ecore:/_test_2")
                .type(Type.UML)
                .relativeFile("_test_2.uml")
                .hash(hash)
                .status(Status.VALID)
                .duplicateOf(null)
                .build();

        repository.insert(model);
    }

    @Test
    void insertTest() {
        Model model = Model.builder()
                .modelId("genmymodel:uml:/_test_3")
                .type(Type.UML)
                .relativeFile("_test_3.uml")
                .hash(String.valueOf(hashCode()))
                .status(Status.VALID)
                .duplicateOf(null)
                .build();

        repository.insert(model);
    }

    @Test
    void findModelByIdTest() {
        String modelId = "genmymodel:ecore:/_test";

        Model model = repository.findByModelId(modelId);

        assertEquals(model.getModelId(), modelId);
    }

    @Test
    void findModelsByTest() {
        Model model = Model.builder()
                .modelId("genmymodel:uml:/_test_1")
                .type(Type.UML)
                .relativeFile("_test.uml")
                .hash(String.valueOf(hashCode()))
                .status(Status.VALID)
                .duplicateOf(null)
                .build();

        repository.insert(model);

        List<Model> models = repository.findModelsBy(pageable).toList();

        assertEquals(models.size(), 4);
    }

    @Test
    void findByTypeTest() {
        List<Model> ecoreModels = repository.findByType(Type.ECORE, pageable)
                .toList();

        assertEquals(ecoreModels.size(), 2);
    }

    @Test
    void findByStatusNotTest() {
        Model validModel = repository.findByStatusNot(Status.NO_VALIDATE)
                .stream()
                .findFirst()
                .orElse(null);

        assert validModel != null;

        assertEquals(validModel.getStatus(), Status.VALID);
    }

    @Test
    void findByStatus() {
        Model validModel = repository.findByStatus(Status.VALID)
                .stream()
                .findFirst()
                .orElse(null);

        assert validModel != null;

        assertEquals(validModel.getStatus(), Status.VALID);
    }

    @Test
    void findByHashAndDuplicateOfIsNullTest() {
        Model model = repository.findByHashAndDuplicateOfIsNull(hash)
                .stream()
                .findFirst()
                .orElse(null);

        assert model != null;

        assertEquals(model.getHash(), hash);
    }

    @Test
    void deleteByStatusTest() {
        repository.deleteByStatus(Status.VALID);

        assertEquals(repository.findAll().size(), 0);
    }

}
