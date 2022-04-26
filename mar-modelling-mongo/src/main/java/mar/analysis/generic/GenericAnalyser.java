package mar.analysis.generic;

import mar.validation.AnalysisMetadataDocument;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;

public class GenericAnalyser {

    private static GenericAnalyser instance;

    public static GenericAnalyser getInstance() {
        if (instance == null) {
            instance = new GenericAnalyser();
        }

        return instance;
    }

    public AnalysisData getAdditionalAnalysis(Resource r, Consumer<Resource> validate) {

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

        /* TODO: Metadata */

        Map<String, List<String>> metadata = new HashMap<>();

        /* TODO: Document */

        AnalysisMetadataDocument document = new AnalysisMetadataDocument();

        return new AnalysisData(stats, metadata, elements, document);
    }
}
