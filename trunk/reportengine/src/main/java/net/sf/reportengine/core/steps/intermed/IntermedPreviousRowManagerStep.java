/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedPreviousRowManagerStep extends PreviousRowManagerStep {
	
	/**
	 * 
	 */
	public List<GroupColumn> getGroupColumns(StepInput stepInput){
		return (List<GroupColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_GROUP_COLS); 
	}
	
	/**
	 * executes the super only if the report has group columns
	 */
	@Override
	public StepResult<Object[]> execute(NewRowEvent newRow, StepInput stepInput){
		StepResult<Object[]> stepResult = null; 
		if(getGroupColumnsCount(stepInput) > 0 ){
			stepResult = super.execute(newRow, stepInput); 
		}
		
		return stepResult; 
	}
}
