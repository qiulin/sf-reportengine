/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * 
 * this is an extension of the DataRowsOutputStep ( relies on the execute method of this class) 
 * but based on the internal computed data columns and group columns
 * 
 * @author dragos balan
 *
 */
public class IntermedDataRowsOutputStep extends DataRowsOutputStep {
	
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
    @Override public List<DataColumn> getDataColumns(){
    	return (List<DataColumn>)getAlgoContext().get(ContextKeys.INTERNAL_DATA_COLS); 
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
    @Override public List<GroupColumn> getGroupColumns(){
    	return (List<GroupColumn>)getAlgoContext().get(ContextKeys.INTERNAL_GROUP_COLS); 
	}
}
