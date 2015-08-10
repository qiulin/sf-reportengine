/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine;

import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.out.ReportProps;

/**
 * @author dragos balan
 *
 */
class DefaultReport implements Report{
	
	public final static String START_REPORT_TEMPLATE = "startReport.ftl";
	public final static String END_REPORT_TEMPLATE = "endReport.ftl";
	
	/**
	 * the list of the components of this report
	 */
	private final List<ReportComponent> components; 
	
	/**
	 * the report output
	 */
	private final AbstractReportOutput reportOutput;
	
	/**
	 * 
	 * @param builder
	 */
	DefaultReport(Builder builder){
		this.reportOutput = builder.getOutput();
		this.components = builder.getComponents(); 
	}
	
	
	/**
	 * executes the report
	 */
	public void execute(){
		reportOutput.open();
		reportOutput.output(START_REPORT_TEMPLATE, new ReportProps(reportOutput.getFormat())); 
		for (ReportComponent reportComponent : components) {
			reportComponent.output(reportOutput);
		}
		reportOutput.output(END_REPORT_TEMPLATE);
		reportOutput.close();
	}
}
