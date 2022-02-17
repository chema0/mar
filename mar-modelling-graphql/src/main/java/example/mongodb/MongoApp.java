package example.mongodb;

import static org.springframework.data.mongodb.core.query.Criteria.where;

import com.mongodb.client.MongoClient;
import example.mongodb.model.Model;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.query.Query;

import com.mongodb.client.MongoClients;

public class MongoApp {

    private static final Log log = LogFactory.getLog(MongoApp.class);


    public static void main(String[] args) throws Exception {
        MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "mar"));
    }
}