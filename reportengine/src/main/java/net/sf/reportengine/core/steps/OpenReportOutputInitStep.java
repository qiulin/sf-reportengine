/**
 * 
 */
package net.sf.reportengine.core.steps;


/**
 * @author dragos balan
 *
 */
public class OpenReportOutputInitStep extends AbstractReportInitStep<String> {

	public StepResult<String> init(StepInput stepInput) {
		getReportOutput(stepInput).open();
		return StepResult.NO_RESULT;
	}

}
