/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.io.File;

import net.sf.reportengine.core.steps.AbstractReportExitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedSetResultsExitStep extends AbstractReportExitStep<File> {

	public StepResult<File> exit(StepInput stepInput) {
		StepResult<File> result = null; 
		ReportOutput output = getReportOutput(stepInput); 
		if(output instanceof IntermediateCrosstabOutput){
			result = new StepResult<File>(ContextKeys.SKIP_CONTEXT_KEY, 
						((IntermediateCrosstabOutput)output).getSerializedOutputFile(), 
						IOKeys.INTERMEDIATE_OUTPUT_FILE); 
		}else{
			throw new IllegalStateException("the output found under IOKeys.INTERMEDIATE_OUTPUT_FILE is not of type IntermediateCrosstabOutput.class"); 
		}
		
		return result; 
	}

}
