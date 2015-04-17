/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.io.File;

import net.sf.reportengine.core.steps.AbstractReportExitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedSetResultsExitStep extends AbstractReportExitStep<File> {

	public StepResult<File> exit(StepInput stepInput) {
		IntermediateCrosstabOutput output = (IntermediateCrosstabOutput)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_OUTPUT);  
		StepResult<File> result = new StepResult<File>(ContextKeys.SKIP_CONTEXT_KEY, 
						((IntermediateCrosstabOutput)output).getSerializedOutputFile(), 
						IOKeys.INTERMEDIATE_OUTPUT_FILE); 
		return result; 
	}
}
