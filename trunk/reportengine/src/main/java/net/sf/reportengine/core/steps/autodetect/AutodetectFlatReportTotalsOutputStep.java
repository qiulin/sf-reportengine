/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 * @since 0.8
 */
public class AutodetectFlatReportTotalsOutputStep extends FlatReportTotalsOutputStep {
	
	private boolean hasTotals = false; 
	
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
