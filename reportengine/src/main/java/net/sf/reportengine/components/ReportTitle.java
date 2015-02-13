/**
 * 
 */
package net.sf.reportengine.components;

import net.sf.reportengine.AbstractReportComponent;
import net.sf.reportengine.out.neo.NewReportOutput;

/**
 * @author dragos balan
 *
 */
public final class ReportTitle extends AbstractReportComponent<String> {
	
	/**
	 * the title
	 */
	public final String title; 
	
	/**
	 * the constructor of a report title
	 * @param title
	 */
	public ReportTitle(String title, NewReportOutput reportOutput){
		super("title", "title.ftl", reportOutput); 
		this.title = title; 
	}
	
	public void output(NewReportOutput out){
		output(out, title); 
	}

}
