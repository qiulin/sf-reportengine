/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;

/**
 * @author dragos balan
 *
 */
public class AutodetectTotalsCalculatorStep extends TotalsCalculatorStep {
		
	private boolean hasTotals; 
	
	@Override 
	public StepResult<CalcIntermResult[][]> init(StepInput stepInput){
		StepResult<CalcIntermResult[][]> stepResult = super.init(stepInput); 
		hasTotals = getShowTotals(stepInput) || getShowGrandTotal(stepInput);
		return stepResult; 
	}
	
	@Override 
	public StepResult<String> execute(NewRowEvent newRowEvent, StepInput stepInput){
		StepResult<String> stepResult = null; 
		if(hasTotals){
			stepResult = super.execute(newRowEvent, stepInput);
		}
		return stepResult; 
	}
}
