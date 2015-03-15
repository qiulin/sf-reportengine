/**
 * 
 */
package net.sf.reportengine.core.steps;


/**
 * @author dragos balan
 *
 */
public class CloseReportOutputExitStep extends AbstractReportExitStep<String> {

	public StepResult<String> exit(StepInput stepInput) {
		getReportOutput(stepInput).close(); 
		return StepResult.NO_RESULT; 
	}

}
