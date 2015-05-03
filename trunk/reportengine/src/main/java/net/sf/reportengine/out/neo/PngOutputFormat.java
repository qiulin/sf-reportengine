/**
 * 
 */
package net.sf.reportengine.out.neo;

import org.apache.xmlgraphics.util.MimeConstants;

/**
 * @author dragos balan
 *
 */
public class PngOutputFormat extends FoOutputFormat {
	
	private final PostProcessor fopPostProcessor = new FopTransformerPostProcessor(MimeConstants.MIME_PNG); 
	
	public PostProcessor postProcessor() {
		return fopPostProcessor; 
	}
}
