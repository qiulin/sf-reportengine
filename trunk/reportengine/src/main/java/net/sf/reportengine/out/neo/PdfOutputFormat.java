/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public class PdfOutputFormat extends FoOutputFormat {
	
	private final PostProcessor fopPostProcessor = new FopTransformerPostProcessor(); 
	
	public PostProcessor postProcessor() {
		return fopPostProcessor; 
	}
	
}
