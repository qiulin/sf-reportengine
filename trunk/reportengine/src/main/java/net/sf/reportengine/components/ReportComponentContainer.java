/**
 * 
 */
package net.sf.reportengine.components;

import java.util.List;

import net.sf.reportengine.out.neo.AbstractReportOutput;

/**
 * This is a container of report components
 * 
 * @author dragos balan
 *
 */
public class ReportComponentContainer implements ReportComponent {
	
	/**
	 * the components inside this container
	 */
	private List<ReportComponent> components;

	/* (non-Javadoc)
	 * @see net.sf.reportengine.components.ReportComponent#output(net.sf.reportengine.out.neo.NewReportOutput)
	 */
	public void output(AbstractReportOutput out) {
		for (ReportComponent reportComponent : components) {
			reportComponent.output(out);
		}
	}
}
