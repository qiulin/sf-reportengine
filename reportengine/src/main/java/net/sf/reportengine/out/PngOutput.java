/**
 * 
 */
package net.sf.reportengine.out;

import java.io.OutputStream;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.fop.apps.MimeConstants;

/**
 * <p>PNG report output. </p>
 * 
 * Usage : 
 * <pre>
 * 		//png output into a file
 * 		ReportOutput reportOutput = new PngOutput("c:/temp/test.png"); 
 * 		or
 * 		// png output into a stream 
 * 		ReportOutput reportOutput = new PngOutput(new FileOutputStream("c:\\temp\\test.png"));
 * 		or
 * 		//png output into memory (not recommended)
 * 		ReportOutput reportOutput = new PngOutput();  
 * </pre>
 * 
 * @author dragos balan
 */
public class PngOutput extends AbstractFopOutput {

	/**
	 * generates png into memory as a {@code ByteArrayOutputStream}
	 */
	public PngOutput() {
		super(); 
	}

	/**
	 * generates the png onto the specified file path. 
	 * The file will be created. 
	 * 
	 * @param filePath the path of the file where png should be generated
	 */
	public PngOutput(String filePath) {
		super(filePath);
	}
	
	/**
	 * generates the png onto the specified file path and during the creation of the png the specified
	 * fop configuration will be used.
	 * 
	 * @param filePath	the path of the file where png will be generated
	 * @param fopConfig	the custom fop configuration
	 */
	public PngOutput(String filePath, Configuration fopConfig){
		super(filePath, fopConfig); 
	}
	
	/**
	 * outputs the generated png into the output stream provided
	 * 
	 * @param out	the output stream
	 */
	public PngOutput(OutputStream out) {
		super(out);
	}

	/**
	 * generates png into the specified output stream using the custom fop configuration. 
	 * 
	 * @param out		the output stream 
	 * @param fopConfig	the fop custom configuration
	 */
	public PngOutput(OutputStream out, Configuration fopConfig) {
		super(out, fopConfig);
	}

	/**
	 * returns MIME_PNG
	 */
	@Override protected String getMimeType() {
		return MimeConstants.MIME_PNG; 
	}

}
