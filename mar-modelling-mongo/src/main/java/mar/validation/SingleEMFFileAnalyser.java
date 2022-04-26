package mar.validation;

import mar.analysis.generic.AnalysisData;
import mar.models.model.Status;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import javax.annotation.Nonnull;
import java.io.IOException;


public class SingleEMFFileAnalyser implements ISingleFileAnalyser {

    @Override
    public AnalysisResult analyse(@Nonnull IFileInfo f) {
        String modelId = f.getModelId();
        try {
            if (!isProperFormat(f)) {
                return new AnalysisResult(modelId, Status.NOT_HANDLED);
            }

            Resource r = loadModel(f);

            Status status;
            if (!checkResource(modelId, r)) {
                status = Status.NO_VALIDATE;
            } else {
                status = Status.VALID;
            }

            AnalysisData d = getAdditionalAnalysis(r);
            return new AnalysisResult(modelId, status).
                    withStats(d.getStats()).
                    withMetadata(d.getMetadata()).
                    withMetadataJSON(d.getDocument()).
                    withElements(d.getElements());
        } catch (Exception e) {
            // LOG.error("Crashed " + relativeName, e);
            // db.updateStatus(relativeName, Status.CRASHED);
            return new AnalysisResult(modelId, Status.CRASHED);
        }

    }

    @Nonnull
    protected AnalysisData getAdditionalAnalysis(@Nonnull Resource r) {
        return AnalysisData.EMPTY;
    }

    /**
     * Analysis data provided by subclasses.
     * Moved to its own class
     */
    /* protected static class AnalysisData {
        public static AnalysisData EMPTY = new AnalysisData(null, null, null, null);

        public final Map<String, Integer> stats;
        public final Map<String, List<String>> metadata;
        public final Map<String, List<String>> elements;
        private AnalysisMetadataDocument document;

        public AnalysisData(@CheckForNull Map<String, Integer> stats, @CheckForNull Map<String, List<String>> metadata,
                            @CheckForNull Map<String, List<String>> elements, @CheckForNull AnalysisMetadataDocument document) {
            this.stats = stats;
            this.metadata = metadata;
            this.elements = elements;
            this.document = document;
        }

    } */

    protected boolean isProperFormat(IFileInfo f) {
        return true;
    }

    /**
     * By default, the resource is ok. Override to enable specific diagnostics.
     */
    protected boolean checkResource(@Nonnull String modelId, @Nonnull Resource r) {
        return true;
    }

    @Nonnull
    protected Resource loadModel(@Nonnull IFileInfo f) throws IOException {
        ResourceSet rs = new ResourceSetImpl();
        Resource r = rs.getResource(URI.createFileURI(f.getAbsolutePath()), true);
        return r;
    }
}
