/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public abstract class OutputFormat {
	
	public abstract String getFormatTemplateClasspath(); 
	
	public abstract boolean needsPostProcessing(); 
	
}
