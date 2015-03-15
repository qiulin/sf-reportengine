/**
 * 
 */
package net.sf.reportengine.core.steps;



/**
 * @author dragos balan
 *
 */
public class CloseReportInputExitStep extends AbstractReportExitStep<String> {

	public StepResult<String> exit(StepInput stepInput) {
		getReportInput(stepInput).close(); 
		return StepResult.NO_RESULT; 
	}
}
