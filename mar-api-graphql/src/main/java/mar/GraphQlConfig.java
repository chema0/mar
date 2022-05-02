package mar;

import mar.resolvers.TypeResolvers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphQlConfig {

    @Autowired
    TypeResolvers typeResolvers;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .type("Stats",
                        typeWiring -> typeWiring.typeResolver(typeResolvers.InterfaceResolver()))
                .type("Elements",
                        typeWiring -> typeWiring.typeResolver(typeResolvers.InterfaceResolver()));
    }
}

