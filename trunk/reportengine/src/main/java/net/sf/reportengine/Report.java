/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.out.neo.AbstractReportOutput;

/**
 * This is the main report class. 
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
