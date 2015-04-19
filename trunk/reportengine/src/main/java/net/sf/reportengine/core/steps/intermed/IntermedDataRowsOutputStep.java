/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.neo.NewDataRowsOutputStep;
import net.sf.reportengine.util.ContextKeys;

/**
 * 
 * this is an extension of the DataRowsOutputStep ( relies on the execute method of this class) 
 * but based on the internal computed data columns and group columns
 * 
 * @author dragos balan
 *
 */
public class IntermedDataRowsOutputStep extends NewDataRowsOutputStep {
	
	/**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumns()}
     * {@link #getDataColumnsLength()}
     * 
     * @param algoInput
     * @param algoContext
     * @return
     */
    @Override public List<DataColumn> getDataColumns(StepInput stepInput){
    	return (List<DataColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_DATA_COLS); 
	}
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumnsCount()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @param algoInput
     * @param algoContext
     * @return
     */
    @Override public List<GroupColumn> getGroupColumns(StepInput stepInput){
    	return (List<GroupColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_GROUP_COLS); 
	}
}
