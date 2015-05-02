/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.File;
import java.io.OutputStream;

/**
 * @author dragos balan
 *
 */
public interface PostProcessor {
	
	public void process(File tempFile, OutputStream outStream); 
}
