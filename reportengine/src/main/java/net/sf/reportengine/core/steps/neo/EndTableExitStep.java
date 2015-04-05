/**
 * 
 */
package net.sf.reportengine.core.steps.neo;

import net.sf.reportengine.core.steps.AbstractReportExitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.neo.NewReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author balan
 *
 */
public class EndTableExitStep extends AbstractReportExitStep<String> {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(net.sf.reportengine.core.steps.StepInput)
	 */
	public StepResult<String> exit(StepInput stepInput) {
		((NewReportOutput)stepInput.getAlgoInput(IOKeys.NEW_REPORT_OUTPUT)).output("endTable.ftl");
		return StepResult.NO_RESULT; 
	}

}
