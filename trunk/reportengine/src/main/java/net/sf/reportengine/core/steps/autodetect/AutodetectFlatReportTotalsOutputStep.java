/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 * @since 0.8
 */
public class AutodetectFlatReportTotalsOutputStep extends FlatReportTotalsOutputStep {
	
	private boolean hasTotals = false; 
	
	@Override 
	protected void executeInit(){
		super.executeInit(); 
		hasTotals = getShowTotals() || getShowGrandTotal(); 
	}
	
	@Override 
	public void execute(NewRowEvent newRowEvent){
		if(hasTotals){
			super.execute(newRowEvent); 
		}
	}
}
