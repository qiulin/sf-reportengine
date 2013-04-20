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
public class PngOutput extends AbstractFopOutput {

	/**
	 * 
	 */
	public PngOutput() {
		super(); 
	}

	/**
	 * @param filePath
	 */
	public PngOutput(String filePath) {
		super(filePath);
	}
	
	/**
	 * 
	 * @param filePath
	 * @param fopConfig
	 */
	public PngOutput(String filePath, Configuration fopConfig){
		super(filePath, fopConfig); 
	}
	
	/**
	 * @param out
	 */
	public PngOutput(OutputStream out) {
		super(out);
	}

	/**
	 * @param out
	 * @param fopConfig
	 */
	public PngOutput(OutputStream out, Configuration fopConfig) {
		super(out, fopConfig);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.AbstractFopOutput#getMimeType()
	 */
	@Override protected String getMimeType() {
		return MimeConstants.MIME_PNG; 
	}

}
