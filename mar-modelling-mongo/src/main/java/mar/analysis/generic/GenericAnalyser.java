package mar.analysis.generic;

import mar.analysis.smells.Smell;
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
import java.util.function.Predicate;
import java.util.function.ToIntFunction;

public class GenericAnalyser {

    private static GenericAnalyser instance;

    public static GenericAnalyser getInstance() {
        if (instance == null) {
            instance = new GenericAnalyser();
        }

        return instance;
    }

    public AnalysisData getAdditionalAnalysis(Resource r, ToIntFunction<Resource> validate, Consumer<Map<String, List<Smell>>> detectSmells) {

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

        /* Metadata */

        Map<String, List<String>> metadata = new HashMap<>();

        /* Document */

        AnalysisMetadataDocument document = new AnalysisMetadataDocument();
        document.setNumElements(numElements.get());

        Map<String, List<Smell>> smells = new HashMap<>();

        detectSmells.accept(smells);

        if (!smells.isEmpty()) {
            smells.forEach(document::addSmell);
        }

        return new AnalysisData(stats, metadata, elements, document);
    }
}
