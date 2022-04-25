package mar.analysis.generic;

import mar.validation.AnalysisMetadataDocument;

import javax.annotation.CheckForNull;
import java.util.List;
import java.util.Map;

public class AnalysisData {

    public static AnalysisData EMPTY = new AnalysisData(null, null, null, null);

    private final Map<String, Integer> stats;
    private final Map<String, List<String>> metadata;
    private final Map<String, List<String>> elements;
    private AnalysisMetadataDocument document;

    public AnalysisData(@CheckForNull Map<String, Integer> stats, @CheckForNull Map<String, List<String>> metadata,
                        @CheckForNull Map<String, List<String>> elements, @CheckForNull AnalysisMetadataDocument document) {
        this.stats = stats;
        this.metadata = metadata;
        this.elements = elements;
        this.document = document;
    }

    public Map<String, Integer> getStats() {
        return stats;
    }

    public Map<String, List<String>> getMetadata() {
        return metadata;
    }

    public Map<String, List<String>> getElements() {
        return elements;
    }

    public AnalysisMetadataDocument getDocument() {
        return document;
    }
}
