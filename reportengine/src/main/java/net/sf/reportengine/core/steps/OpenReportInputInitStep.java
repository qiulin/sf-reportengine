/**
 * 
 */
package net.sf.reportengine.core.steps;



/**
 * @author dragos balan
 *
 */
public class OpenReportInputInitStep extends AbstractReportInitStep<String> {

	
	public StepResult<String> init(StepInput stepInput) {
		getReportInput(stepInput).open();
		return StepResult.NO_RESULT; 
	}
}
