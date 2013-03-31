/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;

/**
 * @author dragos balan
 *
 */
public class AutodetectTotalsCalculatorStep extends TotalsCalculatorStep {
		
	private boolean hasTotals; 
	
	@Override public void init(ReportContext reportContext){
		 super.init(reportContext); 
		 
		 hasTotals = getShowTotals() || getShowGrandTotal(); 
	}
	
	@Override public void execute(NewRowEvent newRowEvent){
		if(hasTotals){
			super.execute(newRowEvent);
		}
	}
}
