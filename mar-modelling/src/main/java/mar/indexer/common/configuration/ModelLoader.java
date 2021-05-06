package mar.indexer.common.configuration;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;

import javax.annotation.Nonnull;

import org.apache.commons.io.IOUtils;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

import mar.analysis.rds.RdsLoader;
import mar.models.archimate.ArchimateLoader;
import mar.models.bpmn.BPMNLoader;
import mar.models.elysium.LilypondLoader;
import mar.models.pnml.PnmlLoader;
import mar.models.pnml.SculptorLoader;
import mar.models.simulink.SimulinkLoader;

public enum ModelLoader {

	DEFAULT {
		@Override
		public Resource load(File file) {
			ResourceSet rs = new ResourceSetImpl();
			return rs.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		}
	},
	
	UML {
		@Override
		public Resource load(File file) {
			ResourceSet rs = new ResourceSetImpl();
			return rs.getResource(URI.createFileURI(file.getAbsolutePath()), true);
		}
	},
	
	BPMNLoader {
		@Override
		public Resource load(File file) throws IOException {
			BPMNLoader loader = new BPMNLoader();
			return loader.load(file);
		}
	},
	
	PNML {		
		@Override
		public Resource load(File file) throws IOException {
			PnmlLoader loader = new PnmlLoader();			
			return loader.load(file);
		}
	},
	
	SCULPTOR {		
		@Override
		public Resource load(File file) throws IOException {
			SculptorLoader loader = new SculptorLoader();			
			return loader.load(file);
		}
	},
	
	ARCHIMATE {		
		@Override
		public Resource load(File file) throws IOException {
			ArchimateLoader loader = new ArchimateLoader();			
			return loader.load(file);
		}
	},

	LILYPOND {		
		@Override
		public Resource load(File file) throws IOException {
			LilypondLoader loader = new LilypondLoader();			
			return loader.load(file);
		}
	},

	SIMULINK {		
		@Override
		public Resource load(File file) throws IOException {
			SimulinkLoader loader = new SimulinkLoader();			
			return loader.load(file);
		}
	},
	
	RDS {		
		@Override
		public Resource load(File file) throws IOException {
			RdsLoader loader = new RdsLoader();			
			return loader.load(file);
		}
	};

	public abstract Resource load(File file) throws IOException;

	public Resource load(@Nonnull InputStream stream) throws IOException {
		File tmp = File.createTempFile("tmp", "model");
		FileOutputStream out = new FileOutputStream(tmp);
		IOUtils.copy(stream, out);
		Resource r = load(tmp);
		tmp.delete();
		return r;

	}
}