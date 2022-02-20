package mar.bean;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "models")
public class Model {

    @Id
    private String id;
    private String modelId;
    private ModelType type;
    private String relativeFile;
    private String hash;
    private Status status;
    private String duplicateOf;
    private ModelInfo info;
    private List<ModelStat> stats;
}
