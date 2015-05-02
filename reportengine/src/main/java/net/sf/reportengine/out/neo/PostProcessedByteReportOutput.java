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
public class PostProcessedByteReportOutput extends AbstractReportOutput {
	
	private final OutputStream outStream; 
	
	private final DefaultReportOutput characterOutput; 
	
	private final File tempFile; 
	
	public PostProcessedByteReportOutput(OutputStream outStream, OutputFormat outFormat){
		super(outFormat);
		this.outStream = outStream; 
		this.tempFile = ReportIoUtils.createTempFile("report-fo"); 
		this.characterOutput = new DefaultReportOutput(ReportIoUtils.createWriterFromFile(tempFile), outFormat);
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
		PostProcessor postProcessor = getFormat().postProcessor(); 
		if(postProcessor != null){
			postProcessor.process(tempFile, outStream);
		}
		
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#output(java.lang.String, java.util.Map)
	 */
	public void output(String templateName, Map rootModel) {
		characterOutput.output(templateName, rootModel);
	}
}
