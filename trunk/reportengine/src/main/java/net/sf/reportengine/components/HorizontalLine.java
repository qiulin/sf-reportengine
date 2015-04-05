/**
 * 
 */
package net.sf.reportengine.components;

import net.sf.reportengine.out.neo.NewReportOutput;

/**
 * @author dragos balan
 *
 */
public class HorizontalLine extends AbstractReportComponent {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.components.ReportComponent#output(net.sf.reportengine.out.neo.NewReportOutput)
	 */
	public void output(NewReportOutput out) {
		out.output("horizontalLine.ftl");
	}

}
