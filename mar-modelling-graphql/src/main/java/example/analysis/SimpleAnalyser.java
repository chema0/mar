package example.analysis;

import java.io.File;
import java.io.IOException;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;

import mar.modelling.loader.ILoader;
import mar.validation.AnalyserRegistry;
import mar.validation.ResourceAnalyser.Factory;

public class SimpleAnalyser {
	public static void main(String[] args) {
		Factory factory = AnalyserRegistry.INSTANCE.getFactory("ecore");
		if (factory == null) {
			showAvailableAnalysers();
			return;
		}
		factory.configureEnvironment();

		ILoader loader = factory.newLoader();
		
	    try {
			Resource r = loader.toEMF(new File("mar-modelling-graphql/src/main/resources/models/DC.ecore"));
			
			EList<EObject> l = r.getContents();
	        EPackage pkg = (EPackage) l.get(0);
	        System.out.println("Package name: " + pkg.getName());
	        
	        for (EClassifier classifier : pkg.getEClassifiers()) {
	            if (classifier instanceof EClass) {
	            	System.out.println("classifier: " + classifier.getName());
	            }
	        }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void showAvailableAnalysers() {
		System.out.println("Available analysers: ");
		AnalyserRegistry.INSTANCE.forEach((str, factory_) -> {
			System.out.println("  " + str);
		});
	}
}