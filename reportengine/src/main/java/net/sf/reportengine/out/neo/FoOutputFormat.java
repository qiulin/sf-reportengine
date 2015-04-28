/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public class FoOutputFormat extends OutputFormat {
	
	/**
	 * the default FO templates class path
	 */
	public final static String DEFAULT_FO_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/fo";

	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.OutputFormat#getFormatTemplateClasspath()
	 */
	@Override
	public String getFormatTemplateClasspath() {
		// TODO improve here
		return DEFAULT_FO_TEMPLATES_CLASS_PATH;
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.OutputFormat#needsPostProcessing()
	 */
	@Override
	public boolean needsPostProcessing() {
		return true; 
	}

}
