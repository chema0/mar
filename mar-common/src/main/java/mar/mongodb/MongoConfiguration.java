package mar.mongodb;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Configuration
@EnableMongoRepositories
public class MongoConfiguration {

    public @Bean
    MongoClient mongoClient() {
        InetAddress address = null;
        try {
            address = InetAddress.getByName("mongo_db");
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        String connectionString;
        if (address != null) {
            connectionString = "mongodb://mongo_db:27017";
        } else {
            connectionString = "mongodb://localhost:27017";
        }

        System.out.println("Connection string: " + connectionString);
        return MongoClients.create(connectionString);
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), "mar");
    }
}