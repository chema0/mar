package mar.validation;

import javax.annotation.Nonnull;

public interface ISingleFileAnalyser {

	@Nonnull
	AnalysisResult analyse(@Nonnull IFileInfo f);

	public interface Remote extends ISingleFileAnalyser, AutoCloseable {
	}

}
