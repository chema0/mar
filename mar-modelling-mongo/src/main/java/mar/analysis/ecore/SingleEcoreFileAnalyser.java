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
        List<String> uris = new ArrayList<>();

        // TODO: ¿tiene sentido almacenar esto como una lista de Strings, o más bien
        // como un conjunto para evitar duplicados?
        Map<String, List<String>> elements = new HashMap<>();

        TreeIterator<EObject> it = r.getAllContents();
        while (it.hasNext()) {
            EObject obj = it.next();

            EClass element = obj.eClass();
            String elementName = element.getName();

            EStructuralFeature name = element.getEStructuralFeature("name");

            try {
                if (name != null) {
                    Object value = obj.eGet(name);
                    System.out.println(elementName + " - " + value);

                    // TODO: ¿tiene sentido almacenar las stats de los elementos nulos?
                    if (value != null) {
                        elements.putIfAbsent(elementName, new ArrayList<String>());

                        List<String> values = elements.get(elementName);
                        values.add((String) value);

                        elements.put(elementName, values);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Map<String, Integer> stats = new HashMap<>();
        AtomicInteger numElements = new AtomicInteger();

        elements.forEach((k, v) -> {
            stats.put(k, v.size());
            numElements.addAndGet(v.size());
        });

        stats.put("total", numElements.get());

        // TODO: check this
        // int numValidationErrors = validate(r);
        // stats.put("errors", numValidationErrors);

        Map<String, List<String>> metadata = null;
        if (!uris.isEmpty()) {
            metadata = new HashMap<>();
            metadata.put("nsURI", uris);
        }

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

        return new AnalysisData(stats, metadata, elements, document);
    }

}
