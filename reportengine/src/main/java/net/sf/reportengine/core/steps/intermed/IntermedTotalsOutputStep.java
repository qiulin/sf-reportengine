/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.neo.FlatTableTotalsOutputStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * displays the intermediate totals
 * 
 * @author dragos balan
 *
 */
public class IntermedTotalsOutputStep extends FlatTableTotalsOutputStep {
	
	
	/**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumns()}
     * {@link #getDataColumnsLength()}
     * 
     * @return
     */
    @Override public List<DataColumn> getDataColumns(StepInput stepInput){
    	return (List<DataColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_DATA_COLS); 
	}
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumns()}
     * {@link #getGroupColumnsCount()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @return
     */
    @Override 
    public List<GroupColumn> getGroupColumns(StepInput stepInput){
    	return (List<GroupColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_GROUP_COLS); 
	}
    
    
    public List<DataColumn> getInitialDataColumns(StepInput stepInput){
    	return (List<DataColumn>)stepInput.getAlgoInput(IOKeys.DATA_COLS); 
    }
    
    @Override
    protected String getLabelsForAllCalculators(StepInput stepInput){
    	StringBuilder result = new StringBuilder(); 
    	for (DataColumn dataColumn : getInitialDataColumns(stepInput)) {
			if(dataColumn.getCalculator() != null){
				result.append(dataColumn.getCalculator().getLabel()).append(" ") ;
			}
		}
    	return result.toString(); 
    }
}
