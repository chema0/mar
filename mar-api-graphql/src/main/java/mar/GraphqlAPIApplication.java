package mar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

@EnableMongoAuditing
@SpringBootApplication
public class GraphqlAPIApplication {

    public static void main(String[] args) {
        SpringApplication.run(GraphqlAPIApplication.class, args);
    }

}
