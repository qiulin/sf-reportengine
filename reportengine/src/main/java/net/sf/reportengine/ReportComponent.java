/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.out.neo.NewReportOutput;


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
	public void output(NewReportOutput out); 
}
