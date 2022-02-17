package example.mongodb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Model {

    private String id;
    private ModelType type;
    private String relativeFile;
    private Status status;
    private ModelInfo info;
    private List<ModelStat> stats;
}
