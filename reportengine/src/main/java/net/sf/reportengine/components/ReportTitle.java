/**
 * 
 */
package net.sf.reportengine.components;

import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.out.TitleProps;
import net.sf.reportengine.out.neo.NewReportOutput;

/**
 * @author dragos balan
 *
 */
public final class ReportTitle extends AbstractReportComponent {
	
	
	public static final String FM_TEMPLATE_NAME = "title.ftl";
	
	public static final String FM_ROOT_MODEL_NAME = "titleProps";
	
	
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
	
	/**
	 * 
	 */
	public void output(NewReportOutput out){
		Map<String, TitleProps> rootModel = new HashMap<String, TitleProps>(1);
		rootModel.put(FM_ROOT_MODEL_NAME, new TitleProps(title, 2)); 
		out.output(FM_TEMPLATE_NAME, rootModel); 
	}
}
