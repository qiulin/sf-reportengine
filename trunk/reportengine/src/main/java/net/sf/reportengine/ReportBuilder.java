/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.components.ReportComponent;
import net.sf.reportengine.out.AbstractReportOutput;

/**
 * 
 * @author dragos balan
 * @since 0.13
 */
public class ReportBuilder {

	/**
	 * 
	 */
	private List<ReportComponent> components;

	/**
	 * 
	 */
	private final AbstractReportOutput reportOutput;

	/**
	 * 
	 * @param output
	 */
	public ReportBuilder(AbstractReportOutput output) {
		this.reportOutput = output;
		this.components = new ArrayList<ReportComponent>();
	}

	/**
	 * adds a new component to the report
	 * 
	 * @param newComponent
	 *            the component
	 */
	public ReportBuilder add(ReportComponent newComponent) {
		components.add(newComponent);
		return this;
	}

	public AbstractReportOutput getOutput() {
		return reportOutput;
	}

	public List<ReportComponent> getComponents() {
		return components;
	}

	public Report build() {
		return new DefaultReport(reportOutput, components);
	}
}
