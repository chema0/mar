package mar.beans;

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

//    public void addPackage(String value) {
//        this.packages.add(value);
//    }
//
//    public void addClass(String value) {
//        this.classes.add(value);
//    }
//
//    public void addAttribute(String value) {
//        this.attributes.add(value);
//    }
//
//    public void addReference(String value) {
//        this.references.add(value);
//    }
//
//    public void addEnum(String value) {
//        this.enums.add(value);
//    }
//
//    public void addDatatype(String value) {
//        this.datatypes.add(value);
//    }

}