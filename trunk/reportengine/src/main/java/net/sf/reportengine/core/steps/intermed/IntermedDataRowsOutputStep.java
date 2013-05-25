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
 * @author dragos balan
 *
 */
public class IntermedDataRowsOutputStep extends DataRowsOutputStep {
	
	private IOKeys dataColIOKey; 
	private ContextKeys dataColContextKey; 
	
	private IOKeys groupColIOKey; 
	private ContextKeys groupColContextKey; 
	
	
	public IntermedDataRowsOutputStep(IOKeys dataColsKey, IOKeys groupColsKey){
		this.dataColIOKey = dataColsKey; 
		this.groupColIOKey = groupColsKey; 
	}
	
	
	public IntermedDataRowsOutputStep(ContextKeys dataColsKey, ContextKeys groupColsKey){
		this.dataColContextKey = dataColsKey; 
		this.groupColContextKey = groupColsKey; 
	}
	
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
    	if(dataColIOKey != null){
    		return (List<DataColumn>)getAlgoInput().get(dataColIOKey); 
    	}else{
    		return (List<DataColumn>)getAlgoContext().get(dataColContextKey); 
    	}
	}
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumnsLength()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @param algoInput
     * @param algoContext
     * @return
     */
    @Override public List<GroupColumn> getGroupColumns(){
    	if(groupColIOKey != null){
    		return (List<GroupColumn>)getAlgoInput().get(groupColIOKey); 
    	}else{
    		return (List<GroupColumn>)getAlgoContext().get(groupColContextKey); 
    	}
	}
}
