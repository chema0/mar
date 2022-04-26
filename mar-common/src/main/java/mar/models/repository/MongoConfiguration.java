package mar.models.repository;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration {

    private final static String DATABASE = "mar";
    private final static String TEST_DATABASE = "mar-test";

    public @Bean
    MongoClient mongoClient() {
        InetAddress address = null;

        System.out.println("Connecting to the database...");

        try {
            address = InetAddress.getByName("mongo_db");
        } catch (UnknownHostException e) {
            // e.printStackTrace();
        }

        if (address == null) {
            return MongoClients.create("mongodb://localhost:27017");
        }

        return MongoClients.create("mongodb://mongo_db:27017");
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), TEST_DATABASE);
    }
}