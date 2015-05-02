/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public interface OutputFormat {
	
	public String getFormatTemplateClasspath(); 
	
	public PostProcessor postProcessor(); 
	
}
