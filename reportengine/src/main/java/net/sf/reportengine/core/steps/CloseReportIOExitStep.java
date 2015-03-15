/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.util.IOKeys;


/**
 * @author dragos balan
 *
 */
public class CloseReportIOExitStep extends AbstractReportExitStep<String> {
	
	/**
	 * 
	 */
	public StepResult<String> exit(StepInput stepInput){
		getReportOutput(stepInput).close(); 
		getReportInput(stepInput).close(); 
		return StepResult.NO_RESULT; 
	}
	
}
