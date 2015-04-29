/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public interface OutputFormat {
	
	public abstract String getFormatTemplateClasspath(); 
	
	public abstract boolean needsPostProcessing(); 
	
}
