/**
 * 
 */
package net.sf.reportengine.components;

import net.sf.reportengine.out.neo.AbstractReportOutput;

/**
 * @author dragos balan
 *
 */
public class EmptyLine extends AbstractReportComponent {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.components.ReportComponent#output(net.sf.reportengine.out.neo.NewReportOutput)
	 */
	public void output(AbstractReportOutput out) {
		out.output("emptyLine.ftl");
	}

}
