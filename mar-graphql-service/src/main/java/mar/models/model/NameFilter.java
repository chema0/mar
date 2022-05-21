package mar.models.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class NameFilter {

    private String element;
    private List<String> names;

}
