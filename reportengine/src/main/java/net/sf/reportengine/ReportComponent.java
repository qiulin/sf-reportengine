/**
 * 
 */
package net.sf.reportengine;

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
	public void output(ReportOutput out); 
}
