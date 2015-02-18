/**
 * 
 */
package net.sf.reportengine.components;

import net.sf.reportengine.AbstractReportComponent;
import net.sf.reportengine.out.TitleProps;
import net.sf.reportengine.out.neo.NewReportOutput;

/**
 * @author dragos balan
 *
 */
public final class ReportTitle extends AbstractReportComponent<TitleProps> {
	
	/**
	 * the title
	 */
	public final String title; 
	
	/**
	 * the constructor of a report title
	 * @param title
	 */
	public ReportTitle(String title){
		super("titleProps", "title.ftl"); 
		this.title = title; 
	}
	
	public void output(NewReportOutput out){
		output(out, new TitleProps(title, 2)); 
	}

}
