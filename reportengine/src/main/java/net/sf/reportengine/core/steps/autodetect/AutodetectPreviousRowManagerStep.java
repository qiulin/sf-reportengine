package net.sf.reportengine.core.steps.autodetect;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.IOKeys;

/**
 * this is just a PreviousRowManagerStep which verifies at runtime if the report has group columns 
 * in order to execute itself
 * 
 * @author dragos balan
 * @since 0.8
 *
 */
public class AutodetectPreviousRowManagerStep extends PreviousRowManagerStep{
	
	/**
	 * flag
	 */
	private boolean reportHasGroups; 
	
	/**
	 * 
	 */
	@Override
	public StepResult<String> init(StepInput stepInput){
		reportHasGroups = getGroupColumns(stepInput) != null && getGroupColumns(stepInput).size() > 0;
		return StepResult.NO_RESULT; 
	}
	
	@Override 
	public StepResult<Object[]> execute(NewRowEvent rowEvent, StepInput stepInput) {
		StepResult<Object[]> stepResult = null; 
		if(reportHasGroups){
			stepResult = super.execute(rowEvent, stepInput);
		}
		return stepResult; 
	}
}
