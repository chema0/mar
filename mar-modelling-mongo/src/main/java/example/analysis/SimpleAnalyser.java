package example.analysis;

import com.mongodb.client.MongoClients;
import mar.analysis.ecore.SingleEcoreFileAnalyser;
import mar.modelling.loader.ILoader;
import mar.validation.AnalyserRegistry;
import mar.validation.ResourceAnalyser.Factory;
import mar.validation.SingleEMFFileAnalyser;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.resource.Resource;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SimpleAnalyser {
//     public static void main(String[] args) {
//         Factory factory = AnalyserRegistry.INSTANCE.getFactory("ecore");
//         if (factory == null) {
//             showAvailableAnalysers();
//             return;
//         }
//         factory.configureEnvironment();

//         ILoader loader = factory.newLoader();

//         try {
//             Resource r = loader.toEMF(new File("mar-modelling-graphql/src/main/resources/models/DC.ecore"));

//             SingleEcoreFileAnalyser singleEcoreFileAnalyser = new SingleEcoreFileAnalyser();
// //            SingleEMFFileAnalyser.AnalysisData analysisData = singleEcoreFileAnalyser.getAdditionalAnalysis(r);

//             EList<EObject> l = r.getContents();
//             List<String> packages = new ArrayList<String>();

//             EPackage pkg = (EPackage) l.get(0);
//             packages.add(pkg.getName());

// //            analysisToMongo(analysisData, packages);
// //            System.out.println("Package name: " + pkg.getName());
// //
// //            for (EClassifier classifier : pkg.getEClassifiers()) {
// //                if (classifier instanceof EClass) {
// //                    System.out.println("classifier: " + classifier.getName());
// //                }
// //            }
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     private static void showAvailableAnalysers() {
//         System.out.println("Available analysers: ");
//         AnalyserRegistry.INSTANCE.forEach((str, factory_) -> {
//             System.out.println("  " + str);
//         });
//     }

//     private static void analysisToMongo(SingleEMFFileAnalyser.AnalysisData analysisData, List<String> packages) {
//         MongoOperations mongoOps = new MongoTemplate(new SimpleMongoClientDatabaseFactory(MongoClients.create(), "mar"));

// //        Model model = new Model();
// //        model.setType(Type.ECORE);
// //        model.setStatus(Status.VALID);
// //
// //        ModelInfo info = new ModelInfo();
// //        info.setName("dc");
// //        info.setPackages(packages);
// //
// //        List<ModelStat> stats = new ArrayList<ModelStat>();
// //        for (Map.Entry<String, Integer> entry : analysisData.stats.entrySet()) {
// //            ModelStat stat = new ModelStat();
// //            stat.setType(entry.getKey());
// //            stat.setCount(entry.getValue());
// //            stats.add(stat);
// //        }

// //        model.setInfo(info);
// //        model.setStats(stats);
// //
// //        mongoOps.insert(model, "models");
//     }
}