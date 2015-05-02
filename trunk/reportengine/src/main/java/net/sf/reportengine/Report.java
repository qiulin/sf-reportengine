/**
 * 
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.out.neo.AbstractReportOutput;
import net.sf.reportengine.out.neo.NewReportOutput;

/**
 * Experimental. Don't use it !
 * Report interface 
 * 
 * @author dragos balan
 * @since 0.13.0
 */
public class Report {
	
	public final static String START_REPORT_TEMPLATE = "startReport.ftl";
	public final static String REPORT_MODEL_NAME = "reportProps"; 
	public final static String END_REPORT_TEMPLATE = "endReport.ftl";
	
	
	private List<ReportComponent> components = new ArrayList<ReportComponent>(); 
	
	private final AbstractReportOutput reportOutput; 
	
	
	public Report(AbstractReportOutput reportOutput){
		this.reportOutput = reportOutput; 
	}
	
	
	/**
	 * adds a new component to the report
	 * @param reportComponent	the component
	 */
	public void add(ReportComponent reportComponent){
		components.add(reportComponent); 
	}
	
	/**
	 * executes the report
	 */
	public void execute(){
		reportOutput.open();
		reportOutput.output(START_REPORT_TEMPLATE, REPORT_MODEL_NAME, new ReportProps(reportOutput.getFormat())); 
		for (ReportComponent reportComponent : components) {
			reportComponent.output(reportOutput);
		}
		reportOutput.output(END_REPORT_TEMPLATE);
		reportOutput.close();
	}
}
