/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public class FoOutputFormat extends AbstractPrintOutputFormat {
	
	/**
	 * the default FO templates class path
	 */
	public final static String DEFAULT_FO_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/fo";

	public FoOutputFormat(){
		this("A4"); 
	}
	
	public FoOutputFormat(String pageSize){
		this(DEFAULT_FO_TEMPLATES_CLASS_PATH, pageSize); 
	}
	
	public FoOutputFormat(String templateClassPath, String pageSize){
		super(templateClassPath, pageSize); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.OutputFormat#getFormatTemplateClasspath()
	 */
	@Override
	public String getFormatTemplateClasspath() {
		// TODO improve here
		return DEFAULT_FO_TEMPLATES_CLASS_PATH;
	}

	
	public PostProcessor postProcessor() {
		return null; 
	}
}
