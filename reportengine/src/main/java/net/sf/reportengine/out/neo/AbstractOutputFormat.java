/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractOutputFormat implements OutputFormat {
	
	private String templateClassPath; 
	
	public AbstractOutputFormat(String templateClassPath){
		this.templateClassPath = templateClassPath; 
	}
	
	public String getFormatTemplateClasspath(){
		return templateClassPath; 
	}
	
	public PostProcessor postProcessor() {
		return null;
	}
	
}
