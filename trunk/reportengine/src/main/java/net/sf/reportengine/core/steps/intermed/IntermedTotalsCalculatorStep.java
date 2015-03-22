/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedTotalsCalculatorStep extends TotalsCalculatorStep {
	
	@Override 
	public List<GroupColumn> getGroupColumns(StepInput stepInput){
		return (List<GroupColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_GROUP_COLS); 
	}
	
	@Override 
	public List<DataColumn> getDataColumns(StepInput stepInput){
		return (List<DataColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_DATA_COLS); 
	}
}
