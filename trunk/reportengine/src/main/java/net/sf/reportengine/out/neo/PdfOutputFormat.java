/**
 * 
 */
package net.sf.reportengine.out.neo;

import org.apache.xmlgraphics.util.MimeConstants;

/**
 * @author dragos balan
 *
 */
public class PdfOutputFormat extends FoOutputFormat {
	
	private final PostProcessor fopPostProcessor = new FopTransformerPostProcessor(MimeConstants.MIME_PDF); 
	
	public PostProcessor postProcessor() {
		return fopPostProcessor; 
	}
	
}
