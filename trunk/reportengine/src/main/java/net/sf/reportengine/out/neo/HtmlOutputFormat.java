/**
 * 
 */
package net.sf.reportengine.out.neo;


/**
 * @author dragos balan
 *
 */
public class HtmlOutputFormat extends OutputFormat {
	
	/**
	 * the default class path  for freemarker templates
	 */
	public final static String DEFAULT_HTML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/html"; 

	/**
	 * 
	 */
	private final String templatesClassPath; 
	
	/**
	 * 
	 */
	public HtmlOutputFormat(){
		this(DEFAULT_HTML_TEMPLATES_CLASS_PATH); 
	}
	
	/**
	 * 
	 * @param templatesClassPath
	 */
	public HtmlOutputFormat(String templatesClassPath){
		this.templatesClassPath = templatesClassPath; 
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.OutputFormat#getFormatTemplateClasspath()
	 */
	@Override
	public String getFormatTemplateClasspath() {
		return templatesClassPath;
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.OutputFormat#needsPostProcess()
	 */
	@Override
	public boolean needsPostProcessing() {
		return false;
	}

}
