/**
 * 
 */
package net.sf.reportengine.out;

import java.io.OutputStream;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.fop.apps.MimeConstants;

/**
 * <p>PDF report output. </p>
 * 
 * Usage : 
 * <pre>
 * 		//pdf output into a file
 * 		ReportOutput reportOutput = new PdfOutput("c:/temp/test.pdf"); 
 * 		or
 * 		// pdf output into a stream 
 * 		ReportOutput reportOutput = new PdfOutput(new FileOutputStream("c:\\temp\\test.pdf"));
 * 		or
 * 		//pdf output into memory (not recommended)
 * 		ReportOutput reportOutput = new PdfOutput();  
 * </pre>
 * 
 * @author dragos balan
 */
public class PdfOutput extends AbstractFopOutput{
	
	/**
	 * generates pdf into memory as a {@code ByteArrayOutputStream}
	 */
	public PdfOutput(){
		super(); 
	}
	
	/**
	 * generates the pdf onto the specified file path. 
	 * The file will be created. 
	 * 
	 * @param filePath the path of the file where pdf should be generated
	 */
	public PdfOutput(String filePath){
		super(filePath);
	}
	
	/**
	 * generates the pdf onto the specified file path with the possibility to append to an existing file.
	 * 
	 * @param filePath	the path of the file where pdf will be generated
	 * @param append 	true if you want to append the content to an existing file
	 */
	public PdfOutput(String filePath, boolean append){
		super(filePath, append); 
	}
	
	
	/**
	 * generates the pdf onto the specified file path and during the creation of the pdf the specified
	 * fop configuration will be used.
	 * 
	 * @param filePath	the path of the file where pdf will be generated
	 * @param append 	true if you want to append the content to an existing file
	 * @param fopConfig	the custom fop configuration
	 */
	public PdfOutput(String filePath, boolean append, Configuration fopConfig){
		super(filePath, append, fopConfig); 
	}
	
	/**
	 * outputs the generated pdf into the output stream provided
	 * 
	 * @param out	the output stream
	 */
	public PdfOutput(OutputStream out){
		super(out); 
	}
	
	/**
	 * generates pdf into the specified output stream using the custom fop configuration. 
	 * 
	 * @param out		the output stream 
	 * @param fopConfig	the fop custom configuration
	 */
	public PdfOutput(OutputStream out, Configuration fopConfig){
		super(out, fopConfig); 
	}
	
	
	/**
	 * returns MIME_PDF
	 */
	@Override protected String getMimeType() {
		return MimeConstants.MIME_PDF;
	}
}
