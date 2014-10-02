/**
 * 
 */
package net.sf.reportengine.components;

import net.sf.reportengine.ReportComponent;
import net.sf.reportengine.ReportOutput;

/**
 * @author dragos balan
 *
 */
public final class ReportTitle implements ReportComponent {
	
	/**
	 * the title
	 */
	public final String title; 
	
	/**
	 * the constructor of a report title
	 * @param title
	 */
	public ReportTitle(String title){
		this.title = title; 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.ReportComponent#output(net.sf.reportengine.ReportOutput)
	 */
	public void output(ReportOutput out) {
		// TODO Auto-generated method stub

	}

}
