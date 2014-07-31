/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedTotalsOutputStep extends FlatReportTotalsOutputStep {
	
	
	/**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumns()}
     * {@link #getDataColumnsLength()}
     * 
     * @return
     */
    @Override public List<DataColumn> getDataColumns(){
    	return (List<DataColumn>)getAlgoContext().get(ContextKeys.INTERNAL_DATA_COLS); 
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
    @Override public List<GroupColumn> getGroupColumns(){
    	return (List<GroupColumn>)getAlgoContext().get(ContextKeys.INTERNAL_GROUP_COLS); 
	}
    
    
    public List<DataColumn> getInitialDataColumns(){
    	return (List<DataColumn>)getAlgoInput().get(IOKeys.DATA_COLS); 
    }
    
    @Override
    protected String getLabelsForAllCalculators(){
    	StringBuilder result = new StringBuilder(); 
    	for (DataColumn dataColumn : getInitialDataColumns()) {
			if(dataColumn.getCalculator() != null){
				result.append(dataColumn.getCalculator().getLabel()).append(" ") ;
			}
		}
    	return result.toString(); 
    }
}
