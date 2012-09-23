/*
 * Created on 14.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.ICtColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.config.SubtotalsInfo;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.steps.AbstractAlgorithmStep;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.LevelDetectorStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.filter.DataOutputFilter;
import net.sf.reportengine.filter.ITotalsOutputFilter;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.util.DistinctValuesHolder;

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
    	return (Integer)getContext().get(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL);
    }
    
    /**
     * getter for aggregation level count ( how many levels of aggregation has the report)
     * @return
     * @deprecated : use getGroupColumns.length instead
     */
    public int getAggLevelCount(){
    	return (Integer)getContext().get(LevelDetectorStep.CONTEXT_KEY_AGG_LEVEL_COUNT);
    }
    
    /**
     * getter for aggregation columns
     * @return
     * @deprecated
     */
    public int[] getAggColumnsIndex(){
    	 return (int[])getContext().get(LevelDetectorStep.CONTEXT_KEY_AGG_COLUMNS_INDEX);
    }
    
    /**
     * 
     * @return
     * @deprecated
     */
    public boolean[] getShowTotalOnAggLevel(){
    	return (boolean[])getContext().get(LevelDetectorStep.CONTEXT_KEY_SHOW_TOTALS_FOR_AGG_COLUMNS);
    }
    

    
    /**
     * returns the calculator within context
     * @return
     */
    public ICalculator[][] getCalculatorMatrix(){
    	return (ICalculator[][])getContext().get(TotalsCalculatorStep.CONTEXT_KEY_CALCULATORS);
    }
    
    
    /**
     * getter for the distribution of calculators in data columns array 
     * @return
     */
    public int[] getCalculatorDistributionInColumnDataArray(){
    	return (int[])getContext().get(FlatReportExtractDataInitStep.CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS);
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
    	return getGroupingColumns().length - calcRowNumber - 1;
    }
    
    /**
     * getter for output dispatcher
     * @return
     */
    public IReportOutput getOutput(){
    	return (IReportOutput)getContext().getOutput();
    }
    
    public Object[] getComputedCellValues(){
    	return (Object[])getContext().get(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES);
    }
    
    public String[] getFormattedCellValues(){
    	return (String[])getContext().get(ComputeColumnValuesStep.CONTEXT_KEY_FORMATTED_CELL_VALUES);
    }
    
    /**
     * 
     * @return
     * @deprecated use getDataColumns/getGropingColumns instead
     */
    public IColumn[] getConfigColumns(){
    	return (IColumn[])getContext().get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
    }
    
    public ICtColumn[] getCtHeaderColumns(){
    	return (ICtColumn[])getContext().get(ComputeColumnValuesStep.CONTEXT_KEY_HEADER_COLUMNS);
    }
    
//    /**
//     * 
//     * @param columns
//     * @return
//     * @deprecated
//     */
//    protected int[] extractColumnIndexesHavingCalculators(IColumn[] columns){
//    	int[] tempIndexes = new int[columns.length];
//    	int columnWithCalculatorsCount = 0;
//    	for(int i=0; i<columns.length; i++){
//    		ICalculator calculator = columns[i].getCalculator();
//    		if(calculator != null){
//    			tempIndexes[columnWithCalculatorsCount] = i;
//    			columnWithCalculatorsCount++;
//    		}
//    	}
//    	
//    	int[] result = new int[columnWithCalculatorsCount];
//    	System.arraycopy(tempIndexes, 0, result, 0, columnWithCalculatorsCount);
//    	
//    	return result;
//    }
    
    
    
    public boolean getShowGrandTotal(){
    	return (Boolean)getContext().get(FlatReport.CONTEXT_KEY_SHOW_GRAND_TOTAL);
    }
    
    public boolean getShowTotals(){
    	return (Boolean)getContext().get(FlatReport.CONTEXT_KEY_SHOW_TOTALS);
    }
    
    public List<DataOutputFilter> getDataOutputFilterList(){
    	return (List<DataOutputFilter>)getContext().get(FlatReport.CONTEXT_KEY_DATA_OUT_FILTERS);
    }
    
    /**
     * 
     * @return
     * @deprecated
     */
    public List<ITotalsOutputFilter> getTotalsOutputFilterList(){
    	return (List<ITotalsOutputFilter>)getContext().get(FlatReport.CONTEXT_KEY_TOTAL_OUT_FILTERS);
    }
    
    public IGroupColumn[] getGroupingColumns(){
    	return (IGroupColumn[])getContext().get(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS);
    }
    
    public int getGroupingColumnsLength(){
    	return getGroupingColumns() != null ? getGroupingColumns().length : 0; 
    }
    
    public IDataColumn[] getDataColumns(){
    	return (IDataColumn[])getContext().get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
    }
    
    public Object[] getPreviousRowOfGroupingValues(){
    	return (Object[])getContext().get(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES);
    }
	 
    public String getTotalStringForGroupingLevel(int groupingLevel) {
    	String result = null;
		
    	if(SubtotalsInfo.GRAND_TOTAL_GROUPING_LEVEL == groupingLevel){
    		result = FlatReportTotalsOutputStep.GRAND_TOTAL_STRING;
    	}else{
    		Object[] prevDataRow = getPreviousRowOfGroupingValues(); 
        	if(prevDataRow != null){
        		String prevValueForGropingLevel = prevDataRow[groupingLevel].toString();
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
