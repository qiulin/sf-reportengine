/**
 * 
 */
package net.sf.reportengine.out;

import java.io.OutputStream;

import org.apache.avalon.framework.configuration.Configuration;
import org.apache.fop.apps.MimeConstants;

/**
 * @author dragos balan
 *
 */
public class PdfOutput extends AbstractFopOutput{
	
	/**
	 * 
	 */
	public PdfOutput(){
		super(); 
	}
	
	/**
	 * 
	 * @param filePath
	 */
	public PdfOutput(String filePath){
		super(filePath);
	}
	
	/**
	 * 
	 * @param filePath
	 * @param fopConfig
	 */
	public PdfOutput(String filePath, Configuration fopConfig){
		super(filePath, fopConfig); 
	}
	
	/**
	 * 
	 * @param out
	 */
	public PdfOutput(OutputStream out){
		super(out); 
	}
	
	/**
	 * 
	 * @param out
	 * @param fopConfig
	 */
	public PdfOutput(OutputStream out, Configuration fopConfig){
		super(out, fopConfig); 
	}
	
	
	/**
	 * 
	 */
	@Override protected String getMimeType() {
		return MimeConstants.MIME_PDF;
	}
}
