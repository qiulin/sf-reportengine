/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos balan
 *
 */
public class AutodetectTotalsCalculatorStep extends TotalsCalculatorStep {
		
	private boolean hasTotals; 
	
	@Override public void init(Map<InputKeys, Object> algoInput, ReportContext reportContext){
		 super.init(algoInput, reportContext); 
		 
		 hasTotals = getShowTotals() || getShowGrandTotal(); 
	}
	
	@Override public void execute(NewRowEvent newRowEvent){
		if(hasTotals){
			super.execute(newRowEvent);
		}
	}
}
