/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.File;
import java.io.OutputStream;
import java.util.Map;

import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos balan
 *
 */
public class PostProcessedFoReportOutput extends AbstractReportOutput {
	
	private final OutputStream outStream; 
	
	private final FreemarkerReportOutput characterOutput; 
	
	private final File tempFile; 
	
	private final PostProcessor postProcessor; 
	
	
	/**
	 * 
	 * @param outStream
	 * @param outFormat
	 */
	public PostProcessedFoReportOutput(	OutputStream outStream, 
										FoOutputFormat outFormat, 
										PostProcessor postProcessor){
		super(outFormat);
		this.outStream = outStream; 
		this.tempFile = ReportIoUtils.createTempFile("report-fo"); 
		this.characterOutput = new FoReportOutput(ReportIoUtils.createWriterFromFile(tempFile), outFormat);
		this.postProcessor = postProcessor; 
	}
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#open()
	 */
	public void open() {
		characterOutput.open();
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#close()
	 */
	public void close() {
		characterOutput.close();
		postProcessor.process(tempFile, outStream);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#output(java.lang.String, java.util.Map)
	 */
	public void output(String templateName, Map rootModel) {
		characterOutput.output(templateName, rootModel);
	}
}
