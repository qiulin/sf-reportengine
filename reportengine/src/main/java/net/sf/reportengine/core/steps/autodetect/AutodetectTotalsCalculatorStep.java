/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class AutodetectTotalsCalculatorStep extends TotalsCalculatorStep {
		
	private boolean hasTotals; 
	
	@Override public void init(Map<IOKeys, Object> algoInput, AlgorithmContext reportContext){
		 super.init(algoInput, reportContext); 
		 
		 hasTotals = getShowTotals() || getShowGrandTotal(); 
	}
	
	@Override public void execute(NewRowEvent newRowEvent){
		if(hasTotals){
			super.execute(newRowEvent);
		}
	}
}
