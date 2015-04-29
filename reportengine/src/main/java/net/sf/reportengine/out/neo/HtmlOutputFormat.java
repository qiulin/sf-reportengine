/**
 * 
 */
package net.sf.reportengine.out.neo;


/**
 * @author dragos balan
 *
 */
public class HtmlOutputFormat extends AbstractOutputFormat {
	
	/**
	 * the default class path  for freemarker templates
	 */
	public final static String DEFAULT_HTML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/html"; 

	
	/**
	 * 
	 */
	public HtmlOutputFormat(){
		super(DEFAULT_HTML_TEMPLATES_CLASS_PATH); 
	}
}
