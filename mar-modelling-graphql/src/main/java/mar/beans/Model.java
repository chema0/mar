package mar.beans;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "models")
public class Model {

    @Id
    private String id;

    @Field("model_id")
    private String modelId;
    private Type type;

    @Field("relative_file")
    private String relativeFile;
    private String hash;
    private Status status;

    @Field("duplicate_of")
    private String duplicateOf;
    private Map<String, Integer> stats;
    private Metadata metadata;
    private Map<String, List<String>> metamodel;
}
