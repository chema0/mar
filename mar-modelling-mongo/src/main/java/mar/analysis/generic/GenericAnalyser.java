package mar.analysis.generic;

import mar.analysis.uml.UMLAnalyser;
import mar.analysis.uml.UMLLoader;
import mar.modelling.loader.ILoader;
import mar.validation.ResourceAnalyser;
import mar.validation.SingleEMFFileAnalyser;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceFactoryImpl;
import org.eclipse.uml2.uml.UMLPackage;
import org.eclipse.uml2.uml.internal.resource.UMLResourceFactoryImpl;

import javax.annotation.CheckForNull;

public class GenericAnalyser extends SingleEMFFileAnalyser {

    // FIXME: change for proper type initz
    public static final String ID = "generic";

    public static class Factory implements ResourceAnalyser.Factory {

        @Override
        public void configureEnvironment() {
            // TODO: complete me
        }

        @Override
        public GenericAnalyser newAnalyser(@CheckForNull ResourceAnalyser.OptionMap options) {
            return new GenericAnalyser();
        }

        @Override
        public String getId() {
            return ID;
        }

        @Override
        public ILoader newLoader() {
            // TODO: complete me
            // return new UMLLoader();
        }

    }
}
