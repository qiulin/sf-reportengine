/*
 * Created on 14.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.steps.AbstractAlgoMainStep;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * <p>
 *  Default implementation for a step in the algorithm 
 * </p>
 * 
 * @author dragos balan
 * @since 0.2
 */
public abstract class AbstractReportStep extends AbstractAlgoMainStep{
	
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
     * returns the aggregation level (especially used in the derived classes)
     * @return  the aggregation level
     */
    public int getGroupingLevel(){
    	return (Integer)getAlgoContext().get(ContextKeys.NEW_GROUPING_LEVEL);
    }
    
    /**
     * returns the calculator within context
     * @return
     */
    public Calculator[][] getCalculatorMatrix(){
    	return (Calculator[][])getAlgoContext().get(ContextKeys.CALCULATORS);
    }
    
    
    /**
     * getter for the distribution of calculators in data columns array 
     * @return
     */
    public int[] getCalculatorDistributionInColumnDataArray(){
    	return (int[])getAlgoContext().get(ContextKeys.DISTRIBUTION_OF_CALCULATORS);
    }
    
    
    
    /**
     * getter for output dispatcher
     * @return
     */
    public ReportOutput getReportOutput(){
    	return (ReportOutput)getAlgoInput().get(IOKeys.REPORT_OUTPUT); 
    }
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumnsLength()}
     * 
     * @return
     */
    public List<DataColumn> getDataColumns(){
    	return (List<DataColumn>)getAlgoInput().get(IOKeys.DATA_COLS); 
    }
    
    public int getDataColumnsLength(){
    	return getDataColumns() != null ? getDataColumns().size() : 0; 
    }
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumnsLength()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @return
     */
    public List<GroupColumn> getGroupColumns(){
    	return (List<GroupColumn>)getAlgoInput().get(IOKeys.GROUP_COLS); 
    }
    
    public int getGroupColumnsLength(){
    	return getGroupColumns() != null ? getGroupColumns().size() : 0; 
    }
    
	public String[] getFormattedCellValues(){
    	return (String[])getAlgoContext().get(ContextKeys.FORMATTED_CELL_VALUES);
    }
    
    
    public boolean getShowGrandTotal(){
    	return (Boolean)getAlgoInput().get(IOKeys.SHOW_GRAND_TOTAL);
    }
    
    public boolean getShowTotals(){
    	return (Boolean)getAlgoInput().get(IOKeys.SHOW_TOTALS);
    }
    
    /**
     * computes the row number (from the calculators matrix) where the totals are for the given level
     * @param level		the aggregation level
     * @return
     */
    public int computeCalcRowNumberForAggLevel(int level){
    	return getGroupColumnsLength() - level -1;
    }
    
    /**
     * computes the aggregation level for the given row of the calculators matrix
     * @param calcRowNumber
     * @return
     */
    public int computeAggLevelForCalcRowNumber(int calcRowNumber){
    	return getGroupColumnsLength() - calcRowNumber - 1;
    }
    
    
    
    public Object[] getPreviousRowOfGroupingValues(){
    	return (Object[])getAlgoContext().get(ContextKeys.LAST_GROUPING_VALUES);
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
    
    /**
     * returns the data row count from the report context
     * @return
     */
    public Integer getDataRowCount(){
    	return (Integer)getAlgoContext().get(ContextKeys.DATA_ROW_COUNT); 
    }
    
    /**
     * 
     */
    protected void incrementDataRowNbr(){
    	Integer oldValue = getDataRowCount(); 
    	getAlgoContext().set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(oldValue.intValue()+1));
    }
}
