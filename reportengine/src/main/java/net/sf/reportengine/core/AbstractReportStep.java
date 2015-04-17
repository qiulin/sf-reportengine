/*
 * Created on 14.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.steps.AbstractAlgoMainStep;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
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
public abstract class AbstractReportStep<T, U, V> extends AbstractAlgoMainStep<T, U, V>{
	
	/**
	 * constant for grand total grouping level
	 */
	public static final int GRAND_TOTAL_GROUPING_LEVEL = -1;
	
    
    /**
     * @return  the grouping level
     */
    public int getGroupingLevel(StepInput stepInput){
    	return (Integer)stepInput.getContextParam(ContextKeys.NEW_GROUPING_LEVEL);
    }
    
    
    /**
     * returns the calculator within context
     * @return
     */
    public CalcIntermResult[][] getCalcIntermResultsMatrix(StepInput stepInput){
    	return (CalcIntermResult[][])stepInput.getContextParam(ContextKeys.CALC_INTERM_RESULTS);
    }
    
    
    /**
     * getter for the distribution of calculators in data columns array 
     * @return
     */
    public ArrayList<Integer> getCalculatorDistributionInColumnDataArray(StepInput stepInput){
    	return (ArrayList<Integer>)stepInput.getContextParam(ContextKeys.DISTRIBUTION_OF_CALCULATORS);
    }
    
    
    
    /**
     * getter for output dispatcher
     * @return
     */
    public ReportOutput getReportOutput(StepInput stepInput){
    	return (ReportOutput)stepInput.getContextParam(ContextKeys.LOCAL_REPORT_OUTPUT); 
    }
    
   
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumnsLength()}
     * 
     * @return
     */
    public List<DataColumn> getDataColumns(StepInput stepInput){
    	return (List<DataColumn>)stepInput.getAlgoInput(IOKeys.DATA_COLS); 
    }
    
    /**
     * 
     * @return
     */
    public int getDataColumnsLength(StepInput stepInput){
    	List<DataColumn> dataColumns = getDataColumns(stepInput); 
    	return dataColumns != null ? dataColumns.size() : 0; 
    }
    
    /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumnsCount()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @return
     */
    public List<GroupColumn> getGroupColumns(StepInput stepInput){
    	return (List<GroupColumn>)stepInput.getAlgoInput(IOKeys.GROUP_COLS); 
    }
    
    public int getGroupColumnsCount(StepInput stepInput){
    	List<GroupColumn> groupCols = getGroupColumns(stepInput); 
    	return groupCols != null ? groupCols.size() : 0; 
    }
    
	public String[] getFormattedCellValues(StepInput stepInput){
    	return (String[])stepInput.getContextParam(ContextKeys.FORMATTED_CELL_VALUES);
    }
    
    
    public boolean getShowGrandTotal(StepInput stepInput){
    	return (Boolean)stepInput.getAlgoInput(IOKeys.SHOW_GRAND_TOTAL);
    }
    
    public boolean getShowTotals(StepInput stepInput){
    	return (Boolean)stepInput.getAlgoInput(IOKeys.SHOW_TOTALS);
    }
    
    /**
     * computes the row number (from the calculators matrix) where the totals are for the given level
     * @param level		the aggregation level
     * @return
     */
    public int computeCalcRowNumberForAggLevel(StepInput stepInput, int level){
    	return getGroupColumnsCount(stepInput) - level -1;
    }
    
    /**
     * computes the aggregation level for the given row of the calculators matrix
     * @param calcRowNumber
     * @return
     */
    public int computeAggLevelForCalcRowNumber(StepInput stepInput, int calcRowNumber){
    	return getGroupColumnsCount(stepInput) - calcRowNumber - 1;
    }
    
    
    /**
     * 
     * @return
     */
    public Object[] getPreviousRowOfGroupValues(StepInput stepInput){
    	return (Object[])stepInput.getContextParam(ContextKeys.LAST_GROUPING_VALUES);
    }
	
    /**
     * 
     * @param groupingLevel
     * @return
     */
    public String getTotalStringForGroupingLevel(StepInput stepInput, String calculatorLabels, int groupingLevel) {
    	String result = null;
		
    	if(GRAND_TOTAL_GROUPING_LEVEL == groupingLevel){
    		result = FlatReportTotalsOutputStep.GRAND_TOTAL_STRING + calculatorLabels;
    	}else{
    		Object[] prevGroupValuesRow = getPreviousRowOfGroupValues(stepInput); 
        	if(prevGroupValuesRow != null){
        		String prevValueForGropingLevel = prevGroupValuesRow[groupingLevel].toString();
        		result = calculatorLabels + prevValueForGropingLevel;
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
    public Object[] getTotalStringForGroupingLevelAndPredecessors(StepInput stepInput, int groupingLevel) {
    	Object[] result= new Object[groupingLevel+1];
		
		Object[] prevDataRow = getPreviousRowOfGroupValues(stepInput); 
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
    public Object[] getTotalStringForGroupingLevelAndPredecessors(StepInput stepInput, int from, int groupingLevel) {
    	Object[] result = null; 
    	if(groupingLevel >= from){
    		result = new Object[groupingLevel+1-from];
		
    		Object[] prevDataRow = getPreviousRowOfGroupValues(stepInput); 
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
    public Integer getDataRowCount(StepInput stepInput){
    	return (Integer)stepInput.getContextParam(ContextKeys.DATA_ROW_COUNT); 
    }
    
    /**
     * @param stepInput TODO
     * 
     */
    protected void incrementDataRowNbr(StepInput stepInput){
    	Integer oldValue = getDataRowCount(stepInput); 
    	//getAlgoContext().set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(oldValue.intValue()+1));
    }
    
    /**
     * 
     * @return
     */
    public String getReportTitle(StepInput stepInput){
    	return (String)stepInput.getAlgoInput(IOKeys.REPORT_TITLE); 
    }
}
