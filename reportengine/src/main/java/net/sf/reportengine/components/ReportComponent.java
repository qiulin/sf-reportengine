/**
 * 
 */
package net.sf.reportengine.components;

import net.sf.reportengine.out.neo.AbstractReportOutput;


/**
 * @author dragos balan
 *
 */
public interface ReportComponent {
	
	/**
	 * renders this component 
	 * 
	 * @param out the output of the report
	 */
	public void output(AbstractReportOutput out); 
}
