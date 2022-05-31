package mar.models.controller;

import mar.GraphqlAPIApplication;
import mar.api.GraphQlController;
import mar.models.model.Metadata;
import mar.models.model.Model;
import mar.models.model.Type;
import mar.models.service.ModelsService;
import org.bson.types.ObjectId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.graphql.GraphQlTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.graphql.test.tester.GraphQlTester;
import org.springframework.test.context.ContextConfiguration;

import java.util.*;

import static org.mockito.BDDMockito.given;

@ContextConfiguration(classes = GraphqlAPIApplication.class)
@GraphQlTest(GraphQlController.class)
public class GraphQlControllerTest {

    @Autowired
    private GraphQlTester graphQlTester;

    @MockBean
    private ModelsService modelsService;

    @MockBean
    private MappingMongoConverter mappingMongoConverter;

    private List<Model> models;

    @BeforeEach
    void setup() {
        this.models = new ArrayList<>();

        for (int i = 0; i < 10; i++) {
            Metadata metadata = new Metadata("model", "", "");

            Map<String, Integer> stats = new HashMap<>();
            stats.put("total", 1);

            Map<String, List<String>> elements = new HashMap<>();

            Model model = Model.builder()
                    .id(new ObjectId())
                    .modelId("model_" + i)
                    .type(Type.UML)
                    .metadata(metadata)
                    .stats(stats)
                    .elements(elements)
                    .build();

            this.models.add(model);
        }
    }

    @Test
    void modelsTest() {
        given(modelsService.findModelsByType(10, Type.UML))
                .willReturn(Collections.checkedList(models, Model.class));

        this.graphQlTester.documentName("mar/models")
                .variable("type", "UML")
                .execute()
                .path("data/models")
                .entity(List.class)
                .matches(list -> list.size() == 10);
    }

    @Test
    void modelsWrongFieldErrorTest() {
        given(modelsService.findAllModels(10))
                .willReturn(Collections.checkedList(models, Model.class));

        this.graphQlTester.documentName("mar/models")
                .execute()
                .errors()
                .satisfy(responseErrors -> responseErrors.forEach(System.out::println));
    }

}
