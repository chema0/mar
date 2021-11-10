package mar.analysis.rds;

import java.io.IOException;

import javax.annotation.CheckForNull;

import org.eclipse.emf.ecore.resource.Resource;

import mar.validation.IFileInfo;
import mar.validation.ISingleFileAnalyser;
import mar.validation.ResourceAnalyser;
import mar.validation.ResourceAnalyser.OptionMap;
import mar.validation.SingleEMFFileAnalyser;
import mar.validation.server.AnalysisClient;

public class RdsAnalyser extends SingleEMFFileAnalyser {

	public static final String ID = "rds";

	public static class Factory implements ResourceAnalyser.Factory {

		@Override
		public void configureEnvironment() {
	
		}
		
		@Override
		public RdsAnalyser newAnalyser(@CheckForNull OptionMap options) {
			return new RdsAnalyser();
		}				
		
		@Override
		public String getId() {
			return ID;
		}
	}
	
	@Override
	protected Resource loadModel(IFileInfo f) throws IOException {
		RdsLoader loader = new RdsLoader();
		return loader.load(f.getFullFile());
	}

}
