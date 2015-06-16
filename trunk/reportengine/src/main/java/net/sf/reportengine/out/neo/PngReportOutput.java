package net.sf.reportengine.out.neo;

import java.io.OutputStream;

import net.sf.reportengine.util.ReportIoUtils;

import org.apache.xmlgraphics.util.MimeConstants;


public class PngReportOutput extends PostProcessedFoReportOutput {
	
	public PngReportOutput(OutputStream outStream){
		this(outStream, new PngOutputFormat()); 
	}
	
	public PngReportOutput(OutputStream outStream, PngOutputFormat outputFormat){
		super(outStream, outputFormat, new FopTransformerPostProcessor(MimeConstants.MIME_PNG)); 
	}
	
	
	public PngReportOutput(String filePath){
		this(filePath, new PngOutputFormat()); 
	}
	
	
	public PngReportOutput(String filePath, PngOutputFormat outputFormat){
		super(	ReportIoUtils.createOutputStreamFromPath(filePath), 
				outputFormat, 
				new FopTransformerPostProcessor(
						MimeConstants.MIME_PNG, 
						new FopUserAgentProperties("net.sf.reportengine", filePath))); 
	}
}
