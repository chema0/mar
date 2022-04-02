package mar.analysis.ecore;

import mar.analysis.smells.Smell;
import mar.analysis.smells.ecore.EcoreSmellCatalog;
import mar.modelling.loader.ILoader;
import mar.validation.AnalysisMetadataDocument;
import mar.validation.ResourceAnalyser;
import mar.validation.ResourceAnalyser.OptionMap;
import mar.validation.SingleEMFFileAnalyser;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.*;
import org.eclipse.emf.ecore.impl.EValidatorRegistryImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.EcoreResourceFactoryImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;

import javax.annotation.CheckForNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class SingleEcoreFileAnalyser extends SingleEMFFileAnalyser {

    public static final String ID = "ecore";

    public static class Factory implements ResourceAnalyser.Factory {

        @Override
        public void configureEnvironment() {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("ecore", new EcoreResourceFactoryImpl());
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
        }

        @Override
        public String getId() {
            return ID;
        }

        @Override
        public SingleEcoreFileAnalyser newAnalyser(@CheckForNull OptionMap options) {
            return new SingleEcoreFileAnalyser();
        }

        @Override
        public ILoader newLoader() {
            return new EcoreLoader();
        }
    }

    @Override
    protected boolean checkResource(String modelId, Resource r) {
        return validate(r) == 0;
    }


    // Return the number of validation errors
    private int validate(Resource r) {
        EValidatorRegistryImpl registry = new org.eclipse.emf.ecore.impl.EValidatorRegistryImpl();
        registry.put(EcorePackage.eINSTANCE, new CustomEcoreValidator());
        Diagnostician diagnostician = new Diagnostician(registry);
        for (EObject obj : r.getContents()) {
            Diagnostic d = diagnostician.validate(obj);
            if (d.getSeverity() == Diagnostic.ERROR) {
                return 1 + d.getChildren().size();
            }
        }
        return 0;
    }

    @Override
    protected AnalysisData getAdditionalAnalysis(Resource r) {
        List<String> uris = new ArrayList<String>();

        List<String> packages = new ArrayList<String>();
        List<String> classes = new ArrayList<String>();
        List<String> enums = new ArrayList<String>();
        List<String> datatypes = new ArrayList<String>();
        List<String> attributes = new ArrayList<String>();
        List<String> references = new ArrayList<String>();

        Map<String, List<String>> metamodel = new HashMap<>();

        TreeIterator<EObject> it = r.getAllContents();
        while (it.hasNext()) {
            EObject obj = it.next();
//            if (obj instanceof EPackage) {
//                packages.add(((EPackage) obj).getName());
//                String nsURI = ((EPackage) obj).getNsURI();
//                if (nsURI != null)
//                    uris.add(nsURI);
//            } else if (obj instanceof EClass) {
//                classes.add(((EClass) obj).getName());
//            } else if (obj instanceof EAttribute) {
//                attributes.add(((EAttribute) obj).getEAttributeType().getName());
//            } else if (obj instanceof EReference) {
//                references.add(((EReference) obj).getName());
//            } else if (obj instanceof EEnum) {
//                enums.add(((EEnum) obj).getName());
//            } else if (obj instanceof EDataType) {
//                datatypes.add(((EDataType) obj).getName());
//            }

//            if (eClassName != null) {
//                // String key = "diagram_" + type;
//                List<String> elements = metamodel.get(eClassName);
//
//                if (elements == null) {
                    // elements.add(objName);
//                    elements = new ArrayList<>();
//                }
//
//                metamodel.put(eClassName, elements);
//            }
            String eClassName = obj.eClass().getName();

            try {
                String objName = ((ENamedElement) obj).getName();
                System.out.println("eClassName: " + eClassName + ", objName: " + objName);
                List<String> elements = metamodel.get(eClassName);

                if (elements == null) {
                    elements = new ArrayList<>();
                }

                elements.add(objName);
                metamodel.put(eClassName, elements);
            } catch (Exception e) {
                // e.printStackTrace();
            }
        }

        Map<String, Integer> stats = new HashMap<>();
        AtomicInteger numElements = new AtomicInteger();

        metamodel.forEach((k, v) -> {
            stats.put(k, v.size());
            numElements.addAndGet(v.size());
        });

        stats.put("elements", numElements.get());

//        int numElements = packages.size() + classes.size() + enums.size() + attributes.size() + references.size();
//        stats.put("elements", numElements);
//        stats.put("packages", packages.size());
//        stats.put("classes", classes.size());
//        stats.put("enums", enums.size());
//        stats.put("datatypes", datatypes.size());
//        stats.put("attributes", attributes.size());
//        stats.put("references", references.size());

        // TODO: check this
        // int numValidationErrors = validate(r);
        // stats.put("errors", numValidationErrors);

        Map<String, List<String>> metadata = null;
        if (!uris.isEmpty()) {
            metadata = new HashMap<String, List<String>>();
            metadata.put("nsURI", uris);
        }

        /* Metamodel */

//        Map<String, List<String>> metamodel = new HashMap<>();
//        metamodel.put("packages", packages);
//        metamodel.put("classes", classes);
//        metamodel.put("enums", enums);
//        metamodel.put("datatypes", datatypes);
//        metamodel.put("attributes", attributes);
//        metamodel.put("references", references);

        // TODO: check this
        // Metadata as a document
        //Map<Object, Object> document = new HashMap<>();
        //Map<String, Integer> smellDocument = new HashMap<>();
        //document.put("smells", smellDocument);

        AnalysisMetadataDocument document = new AnalysisMetadataDocument();
        document.setNumElements(numElements.get());
        Map<String, List<Smell>> smells = EcoreSmellCatalog.INSTANCE.detectSmells(r);
        if (!smells.isEmpty()) {
            smells.forEach((k, v) -> {
                document.addSmell(k, v);
            });
        }

        return new AnalysisData(stats, metadata, metamodel, document);
    }

}
