package mar.mongodb.beans;

import lombok.*;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EcoreMetamodel implements Metamodel {

    @Singular private List<String> packages;
    @Singular private List<String> classes;
    @Singular private List<String> attributes;
    @Singular private List<String> references;
    @Singular private List<String> enums;
    @Singular private List<String> datatypes;
}