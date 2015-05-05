package net.sf.reportengine.out.neo;

import java.io.OutputStream;

import org.apache.xmlgraphics.util.MimeConstants;


public class PngReportOutput extends PostProcessedFoReportOutput {
	
	public PngReportOutput(OutputStream outStream){
		this(outStream, new PngOutputFormat()); 
	}
	
	public PngReportOutput(OutputStream outStream, PngOutputFormat outputFormat){
		super(outStream, outputFormat, new FopTransformerPostProcessor(MimeConstants.MIME_PNG)); 
	}
	
}
