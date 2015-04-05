/**
 * 
 */
package net.sf.reportengine.core.steps.neo;

import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;

/**
 * @author dragos balan
 *
 */
public class StartTableInitStep extends AbstractOutputInitStep<String> {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.steps.StepInput)
	 */
	public StepResult<String> init(StepInput input) {
		outputNoValue(input, "startTable.ftl");
		return StepResult.NO_RESULT; 
	}

}
