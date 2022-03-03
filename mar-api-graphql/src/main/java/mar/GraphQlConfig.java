package mar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

    @Autowired
    GraphQlDataFetchers dataFetchers;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .type("Query", builder -> builder
                        .dataFetcher("model", dataFetchers.getModelDataFetcher())
                        .dataFetcher("models", dataFetchers.getModelsDataFetcher()))
                .type("EcoreMetamodel", builder -> builder
                        .dataFetcher("metamodel", dataFetchers.getEcoreMetamodelDataFetcher()));
    }
}

