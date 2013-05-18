/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos balan
 * @since 0.8
 */
public class AutodetectFlatReportTotalsOutputStep extends FlatReportTotalsOutputStep {
	
	private boolean hasTotals = false; 
	
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
