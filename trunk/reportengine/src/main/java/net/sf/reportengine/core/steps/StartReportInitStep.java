/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.out.ReportProps;

/**
 * @author dragos balan
 * @deprecated the flat table no longer needs a start report
 */
public class StartReportInitStep extends AbstractReportInitStep<String> {
	
	/**
	 * contains a call to output.startReport()
	 */
	public StepResult<String> init(StepInput stepInput) {
		getReportOutput(stepInput).startReport(new ReportProps());
		return StepResult.NO_RESULT;
	}
}
