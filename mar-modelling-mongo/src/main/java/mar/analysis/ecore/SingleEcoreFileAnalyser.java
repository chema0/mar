package mar.analysis.ecore;

import mar.analysis.generic.AnalysisData;
import mar.analysis.generic.GenericAnalyser;
import mar.analysis.smells.Smell;
import mar.analysis.smells.ecore.EcoreSmellCatalog;
import mar.modelling.loader.ILoader;
import mar.validation.AnalysisMetadataDocument;
import mar.validation.ResourceAnalyser;
import mar.validation.ResourceAnalyser.OptionMap;
import mar.validation.SingleEMFFileAnalyser;
import org.eclipse.emf.common.util.Diagnostic;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.EcorePackage;
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

    public static final GenericAnalyser genericAnalyser = GenericAnalyser.getInstance();

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
        return genericAnalyser.getAdditionalAnalysis(r,
                (resource) -> validate(r),
                (smells) -> smells = EcoreSmellCatalog.INSTANCE.detectSmells((r)));
    }

}
