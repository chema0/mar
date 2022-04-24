package mar.analysis.uml;

import mar.indexer.common.configuration.ModelLoader;
import mar.modelling.loader.ILoader;
import mar.validation.IFileInfo;
import mar.validation.ResourceAnalyser;
import mar.validation.ResourceAnalyser.OptionMap;
import mar.validation.SingleEMFFileAnalyser;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.util.Diagnostician;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.Model;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;

import javax.annotation.CheckForNull;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class UMLAnalyser extends SingleEMFFileAnalyser {

    public static final String ID = "uml";

    public static class Factory implements ResourceAnalyser.Factory {

        @Override
        public void configureEnvironment() {
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("uml", new UMLResourceFactoryImpl());
            Resource.Factory.Registry.INSTANCE.getExtensionToFactoryMap().put("xmi", new XMIResourceFactoryImpl());
            EPackage.Registry.INSTANCE.put(UMLPackage.eINSTANCE.getNsURI(), UMLPackage.eINSTANCE);
        }

        @Override
        public UMLAnalyser newAnalyser(@CheckForNull OptionMap options) {
            return new UMLAnalyser();
        }

        @Override
        public String getId() {
            return ID;
        }

        @Override
        public ILoader newLoader() {
            return new UMLLoader();
        }

    }

    @Override
    protected boolean isProperFormat(IFileInfo f) {
        try (BufferedReader reader = new BufferedReader(new FileReader(f.getFullFile()))) {
            boolean isUML = reader.lines().anyMatch(s -> s.contains("http://www.eclipse.org/uml2"));
            if (!isUML) {
                System.out.println(" -- Not a UML model");
                return false;
            }
            return true;
        } catch (FileNotFoundException e1) {
            return false;
        } catch (IOException e1) {
            return false;
        }
    }

    @Override
    protected Resource loadModel(IFileInfo f) throws IOException {
        return ModelLoader.UML.load(f.getFullFile());
    }

    @Override
    protected boolean checkResource(String modelId, Resource r) {
        boolean hasModel = false;
        for (EObject obj : r.getContents()) {
            Diagnostic d = Diagnostician.INSTANCE.validate(obj);
            if (d.getSeverity() == Diagnostic.ERROR) {
                return false;
            }

            if (obj instanceof Model) {
                hasModel = true;
            }
        }

        return hasModel;
    }

    @Override
    protected AnalysisData getAdditionalAnalysis(Resource r) {
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

        elements.forEach((k, v) ->

        {
            stats.put(k, v.size());
            numElements.addAndGet(v.size());
        });

        stats.put("total", numElements.get());

        return new AnalysisData(stats, null, elements, null);
    }
}
