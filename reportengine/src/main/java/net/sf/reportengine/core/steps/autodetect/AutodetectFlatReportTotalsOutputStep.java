/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;

/**
 * @author dragos balan
 * @since 0.8
 */
public class AutodetectFlatReportTotalsOutputStep extends FlatReportTotalsOutputStep {
	
	private boolean hasTotals = false; 
	
	@Override 
	public StepResult<String> init(StepInput stepInput){
		super.init(stepInput); 
		hasTotals = getShowTotals(stepInput) || getShowGrandTotal(stepInput); 
		return StepResult.NO_RESULT; 
	}
	
	@Override 
	public StepResult<Integer> execute(NewRowEvent newRowEvent, StepInput stepInput){
		StepResult<Integer> stepResult = null;
		if(hasTotals){
			stepResult = super.execute(newRowEvent, stepInput); 
		}
		return stepResult; 
	}
}
