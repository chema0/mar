package mar.validation.server;

import mar.analysis.thrift.Result;
import mar.analysis.thrift.ValidateService;
import mar.analysis.thrift.ValidationJob;
import mar.beans.Status;
import mar.ingestion.IngestedMetadata;
import mar.sandbox.SandboxClient;
import mar.validation.AnalysisMetadataDocument;
import mar.validation.AnalysisResult;
import mar.validation.IFileInfo;
import mar.validation.ResourceAnalyser.OptionMap;

import javax.annotation.CheckForNull;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.function.Supplier;

public class AnalysisClient extends SandboxClient {
	
	public static final int DEFAULT_INITIAL_PORT = 9081;
	
	@Nonnull
	private final OptionMap options;
	
	public AnalysisClient(int initialPort, @CheckForNull OptionMap options) {
		super(initialPort);
		this.options = options;
	}

	public AnalysisClient() {
		this(DEFAULT_INITIAL_PORT, null);
	}
	
	@Nonnull
	public AnalysisResult analyse(@Nonnull IFileInfo f, @Nonnull String type) {
		Invoker<AnalysisResult> invoker = (protocol) -> {
			ValidateService.Client client = new ValidateService.Client(protocol);

			ValidationJob job = new ValidationJob(f.getModelId(), f.getRelativePath(), f.getAbsolutePath(), type, options);

			Result jobResult = client.validate(job);

			AnalysisResult r = new AnalysisResult(f.getModelId(), Status.valueOf(jobResult.getStatus()));
			if (jobResult.isSetStats())
				jobResult.stats.forEach((k, v) -> r.withStats(k, v));
			if (jobResult.isSetMetadata())
				r.withMetadata(jobResult.metadata);
			if (jobResult.isSetMetadata_json()) {
				AnalysisMetadataDocument document = AnalysisMetadataDocument.loadFromJSON(jobResult.metadata_json);
				addIngestedMetadata(f, document);
				try {
					r.withMetadataJSON(document);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (jobResult.isSetMetamodel()) {
				r.withMetamodel(jobResult.metamodel);
			}
			return r;
		};
		
		Supplier<AnalysisResult> onTimeOut = () -> {
			return new AnalysisResult(f.getModelId(), Status.TIMEOUT);					
		};

		return invokeService(invoker, onTimeOut);
	}
	
	private void addIngestedMetadata(IFileInfo origin, @CheckForNull AnalysisMetadataDocument document) {
		if (document == null) 
			return;
		if (! (origin instanceof IngestedMetadata))
			return;
				
		IngestedMetadata metadata = (IngestedMetadata) origin;
		
		document.setURL(metadata.getURL());
		document.setTopics(metadata.getTopics());
		document.setExplicitName(metadata.getExplicitName());
	}
	
	
}
