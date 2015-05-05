/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.OutputStream;

import org.apache.xmlgraphics.util.MimeConstants;

/**
 * @author dragos balan
 *
 */
public class PdfReportOutput extends PostProcessedFoReportOutput {
	
	
	public PdfReportOutput(OutputStream outStream){
		this(outStream, new PdfOutputFormat()); 
	}
	
	public PdfReportOutput(OutputStream outStream, PdfOutputFormat outputFormat){
		super(outStream, outputFormat, new FopTransformerPostProcessor(MimeConstants.MIME_PDF)); 
	}
}
