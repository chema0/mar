package mar.models.repository;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import java.net.InetAddress;
import java.net.UnknownHostException;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

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

        ConnectionString connectionString;

        if (address == null) {
            connectionString = new ConnectionString("mongodb://localhost:27017");
        } else {
            connectionString = new ConnectionString("mongodb://mongo_db:27017");

        }

        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        CodecRegistry pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        MongoClientSettings settings = MongoClientSettings.builder()
                .codecRegistry(pojoCodecRegistry)
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(settings);
    }

    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoClient(), TEST_DATABASE);
    }
}