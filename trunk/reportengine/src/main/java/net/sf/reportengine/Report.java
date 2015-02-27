/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.components.ReportComponent;

/**
 * Report interface 
 * 
 * @author dragos balan
 * @since 0.13.0
 */
public interface Report {
	
	/**
	 * adds a new component to the report
	 * @param reportComponent	the component
	 */
	public void add(ReportComponent reportComponent); 
	
	/**
	 * executes the report
	 */
	public void execute(); 
}
