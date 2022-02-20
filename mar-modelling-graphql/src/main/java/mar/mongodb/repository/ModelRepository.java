package mar.mongodb.repository;

import mar.bean.Model;
import mar.bean.Status;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;

public interface ModelRepository extends MongoRepository<Model, ObjectId> {

    /* READ */

    Model findByModelId(String modelId);

    @Query("{ status: { $ne: 'NOT_PROCESSED' } }")
    List<Model> findExcludingByStatus(Status status);

    List<Model> findByStatus(Status status);

    @Query("{ 'hash': ?0, 'duplicate_of': null }")
    List<Model> findByHashNotDuplicated(String hash);

    /* UPDATE */

    /* DELETE */

    List<Model> deleteByStatus(Status status);
}
