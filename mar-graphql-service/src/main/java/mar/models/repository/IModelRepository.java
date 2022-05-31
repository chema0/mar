package mar.models.repository;

import mar.models.model.*;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IModelRepository extends MongoRepository<Model, ObjectId> {

    /* READ methods */

    /**
     * Retrieve a <code>Model</code>s from the data store by id.
     *
     * @param modelId the id to search for
     * @return a <code>Model</code>
     */
    Model findByModelId(String modelId);

    /**
     * Retrieve all <code>Model</code>s from the data store paginated.
     *
     * @return a <code>Page</code> of <code>Model</code>s
     */
    @NonNull
    Page<Model> findAll(@NonNull Pageable pageable);

    /**
     * Retrieve all <code>Model</code>s from the data store by type and paginated.
     *
     * @param type     the type to search for
     * @param pageable the pagination information
     * @return a <code>Page</code> of <code>Model</code>s
     */
    Page<Model> findByType(Type type, Pageable pageable);

    /**
     * Retrieve all <code>Model</code>s from the data store whose status is not <code>Status.NOT_PROCESSED</code>.
     *
     * @param status the status to exclude by
     * @return a <code>List</code> of <code>Model</code>s
     */
    List<Model> findByStatusNot(Status status);

    /**
     * Retrieve all <code>Model</code>s from the data store by status.
     *
     * @param status the status to search for
     * @return a <code>List</code> of <code>Model</code>s
     */
    List<Model> findByStatus(Status status);

    /**
     * Retrieve all <code>Model</code>s from the data store by hash and non duplicated.
     *
     * @param hash the hash to search for
     * @return a <code>List</code> of <code>Model</code>s
     */
    List<Model> findByHashAndDuplicateOfIsNull(String hash);

    /**
     * Retrieve all <code>Model</code>s from the data store by its name.
     *
     * @param keyword to be contained in the name of the model
     * @return a <code>List</code> of <code>Model</code>s
     */
    @Query(value="{ 'metadata.name': { $regex: '?0' } }")
    List<Model> findByNameContains(String keyword);

    /* DELETE methods */

    /**
     * Delete all <code>Model</code>s from the data store by status.
     *
     * @param status the status to delete by
     * @return a <code>List</code> of <code>Model</code>s
     */
    List<Model> deleteByStatus(Status status);
}
