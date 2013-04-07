/*
 * Created on 14.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.steps.AbstractAlgorithmStep;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.util.ContextKeys;

/**
 * <p>
 *  Default implementation for a step in the algorithm 
 * </p>
 * 
 * @author dragos balan
 * @since 0.2
 */
public abstract class AbstractReportStep extends AbstractAlgorithmStep{
	
	/**
	 * constant for grand total grouping level
	 */
	public static final int GRAND_TOTAL_GROUPING_LEVEL = -1;
	
    /**
     * empty constructor
     *
     */
    public AbstractReportStep(){
        
    }
    
    /**
     * empty implementation
     */
    public abstract void execute(NewRowEvent rowEvent);
    
    /**
     * empty implementation 
     */
    public void exit(){}
    
    /**
     * returns the aggregation level (especially used in the derived classes)
     * @return  the aggregation level
     */
    public int getGroupingLevel(){
    	return (Integer)getContext().get(ContextKeys.NEW_GROUPING_LEVEL);
    }
    
    /**
     * returns the calculator within context
     * @return
     */
    public Calculator[][] getCalculatorMatrix(){
    	return (Calculator[][])getContext().get(ContextKeys.CALCULATORS);
    }
    
    
    /**
     * getter for the distribution of calculators in data columns array 
     * @return
     */
    public int[] getCalculatorDistributionInColumnDataArray(){
    	return (int[])getContext().get(ContextKeys.DISTRIBUTION_OF_CALCULATORS);
    }
    
    /**
     * computes the row number (from the calculators matrix) where the totals are for the given level
     * @param level		the aggregation level
     * @return
     */
    public int computeCalcRowNumberForAggLevel(int level){
    	return getGroupingColumnsLength() - level -1;
    }
    
    /**
     * computes the aggregation level for the given row of the calculators matrix
     * @param calcRowNumber
     * @return
     */
    public int computeAggLevelForCalcRowNumber(int calcRowNumber){
    	return getGroupingColumns().size() - calcRowNumber - 1;
    }
    
    /**
     * getter for output dispatcher
     * @return
     */
    public IReportOutput getOutput(){
    	return (IReportOutput)getContext().getOutput();
    }
    
    public Object[] getComputedCellValues(){
    	return (Object[])getContext().get(ContextKeys.COMPUTED_CELL_VALUES);
    }
    
	public String[] getFormattedCellValues(){
    	return (String[])getContext().get(ContextKeys.FORMATTED_CELL_VALUES);
    }
    
    
    public boolean getShowGrandTotal(){
    	return (Boolean)getContext().get(ContextKeys.SHOW_GRAND_TOTAL);
    }
    
    public boolean getShowTotals(){
    	return (Boolean)getContext().get(ContextKeys.SHOW_TOTALS);
    }
    
    public List<GroupColumn> getGroupingColumns(){
    	return (List<GroupColumn>)getContext().get(ContextKeys.GROUP_COLUMNS);
    }
    
    public int getGroupingColumnsLength(){
    	return getGroupingColumns() != null ? getGroupingColumns().size() : 0; 
    }
    
    public List<DataColumn> getDataColumns(){
    	return (List<DataColumn>)getContext().get(ContextKeys.DATA_COLUMNS);
    }
    
    public Object[] getPreviousRowOfGroupingValues(){
    	return (Object[])getContext().get(ContextKeys.LAST_GROUPING_VALUES);
    }
	 
    public String getTotalStringForGroupingLevel(int groupingLevel) {
    	String result = null;
		
    	if(GRAND_TOTAL_GROUPING_LEVEL == groupingLevel){
    		result = FlatReportTotalsOutputStep.GRAND_TOTAL_STRING;
    	}else{
    		Object[] prevGroupValuesRow = getPreviousRowOfGroupingValues(); 
        	if(prevGroupValuesRow != null){
        		String prevValueForGropingLevel = prevGroupValuesRow[groupingLevel].toString();
        		result = FlatReportTotalsOutputStep.TOTAL_STRING + " " + prevValueForGropingLevel;
        	}else{
        		throw new IllegalArgumentException("Cannot determine the previous grouping values. Previous group values array is null"); 
        	}
    	}
		return result;
    }
    
    
    /**
     * 
     * @param groupingLevel
     * @return
     */
    public Object[] getTotalStringForGroupingLevelAndPredecessors(int groupingLevel) {
    	Object[] result= new Object[groupingLevel+1];
		
		Object[] prevDataRow = getPreviousRowOfGroupingValues(); 
		if(prevDataRow != null){
			for(int i=0; i < groupingLevel+1; i++){
				result[i] = prevDataRow[i];
			}
		}else{
			throw new IllegalArgumentException("Cannot determine the previous grouping values"); 
		}
		return result;
    }
    
    
    /**
     * 
     * @param from
     * @param groupingLevel
     * @return
     */
    public Object[] getTotalStringForGroupingLevelAndPredecessors(int from, int groupingLevel) {
    	Object[] result = null; 
    	if(groupingLevel >= from){
    		result = new Object[groupingLevel+1-from];
		
    		Object[] prevDataRow = getPreviousRowOfGroupingValues(); 
    		if(prevDataRow != null){
    			for(int i=from; i < groupingLevel+1; i++){
    				result[i-from] = prevDataRow[i];
    			}
    		}else{
    			throw new IllegalArgumentException("Cannot determine the previous grouping values: previousDataRow is null."); 
    		}
    	}
		return result;
    }
}