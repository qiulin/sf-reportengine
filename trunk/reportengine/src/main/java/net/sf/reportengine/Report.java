/**
 * 
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.out.neo.AbstractReportOutput;

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
	
	
	private Report(Builder builder){
		this.reportOutput = builder.reportOutput;
		this.components = builder.components; 
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
	
	public static class Builder {
		
		private List<ReportComponent> components = new ArrayList<ReportComponent>(); 
		
		private final AbstractReportOutput reportOutput;
		
		/**
		 * 
		 * @param output
		 */
		public Builder(AbstractReportOutput output){
			this.reportOutput = output;
		}
		
		
		/**
		 * adds a new component to the report
		 * @param newComponent	the component
		 */
		public Builder add(ReportComponent newComponent){
			components.add(newComponent); 
			return this; 
		}
		
		public Report build(){
			return new Report(this); 
		}
		
	}
}
