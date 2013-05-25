/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.out.ReportProps;

/**
 * @author dragos balan
 *
 */
public class StartReportInitStep extends AbstractReportInitStep {
	
	/**
	 * contains a call to output.startReport()
	 */
	@Override protected void executeInit() {
		getReportOutput().startReport(new ReportProps());
	}
}
