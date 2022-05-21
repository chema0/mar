package mar.models.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class LogicalFilter {

    private String element;
    private String operator;
    private Integer value;

}
