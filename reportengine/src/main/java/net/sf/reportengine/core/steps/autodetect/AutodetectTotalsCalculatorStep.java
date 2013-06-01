/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;

/**
 * @author dragos balan
 *
 */
public class AutodetectTotalsCalculatorStep extends TotalsCalculatorStep {
		
	private boolean hasTotals; 
	
	@Override protected void executeInit(){
		super.executeInit(); 
		hasTotals = getShowTotals() || getShowGrandTotal(); 
	}
	
	@Override public void execute(NewRowEvent newRowEvent){
		if(hasTotals){
			super.execute(newRowEvent);
		}
	}
}
