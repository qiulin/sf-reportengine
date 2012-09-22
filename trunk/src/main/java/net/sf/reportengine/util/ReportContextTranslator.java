/**
 * 
 */
package net.sf.reportengine.util;

import java.util.List;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.FlatReport;
import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.ICtColumn;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.LevelDetectorStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.filter.DataOutputFilter;
import net.sf.reportengine.out.IReportOutput;

/**
 * helper class for working with the report context
 * 
 * @author dragos
 * @since 0.3
 * @deprecated
 */
public final class ReportContextTranslator {
	
	
	/**
	 * private constructor which prevents instantiation
	 */
	private ReportContextTranslator(){
		
	}
	
	/**
     * returns the current grouping-subgrouping level 
     * 
     * @return  the grouping level
     */
    public static int getGroupingLevel(IAlgorithmContext context){
    	return (Integer)context.get(LevelDetectorStep.CONTEXT_KEY_GROUPING_LEVEL);
    }
    
    
    /**
     * getter for aggregation level count ( how many levels of aggregation has the report)
     * @return
     */
    public static int getGroupingsCount(IAlgorithmContext context){
    	return (Integer)context.get(LevelDetectorStep.CONTEXT_KEY_AGG_LEVEL_COUNT);
    }
    
    
    /**
     * getter for grouping columns index = the index of columns which take part 
     * in the groups - subgroups
     * 
     * @return an array of indexes
     */
    public static int[] getGroupingColumnsIndex(IAlgorithmContext context){
    	 return (int[])context.get(LevelDetectorStep.CONTEXT_KEY_AGG_COLUMNS_INDEX);
    }
    
    
    /**
     * 
     * @return
     */
    public static boolean[] getShowTotalOnGroupLevel(IAlgorithmContext context){
    	return (boolean[])context.get(LevelDetectorStep.CONTEXT_KEY_SHOW_TOTALS_FOR_AGG_COLUMNS);
    }
    
    /**
     * the number of data columns
     * 
     * @return
     */
//    public static int getInputColumnsCount(IAlgorithmContext context){
//    	return context.getInput().getColumnsCount();
//    }
    
    /**
     * returns the matrix of calculators existing in this context
     * 
     * @return
     */
    public static ICalculator[][] getCalculatorMatrix(IAlgorithmContext context){
    	return (ICalculator[][])context.get(TotalsCalculatorStep.CONTEXT_KEY_CALCULATORS);
    }
    
    /**
     * computes the row number ( of the calculators matrix ) where the totals are for the given level
     * @param level		the aggregation level
     * @return
     */
//    public static int getCalcRowNumberForGroupLevel(int level){
//    	return getGroupingsCount() - level -1;
//    }
    
    /**
     * computes the aggregation level for the given row of the calculators matrix
     * @param calcRowNumber
     * @return
     */
//    public int computeAggLevelForCalcRowNumber(int calcRowNumber){
//    	return getLevelCount() - calcRowNumber - 1;
//    }
    
    /**
     * 
     * @param context
     * @return
     */
    public static IReportOutput getOutput(IAlgorithmContext context){
    	return (IReportOutput)context.getOutput();
    }
    
    
    public static Object[] getComputedCellValues(IAlgorithmContext context){
    	return (Object[])context.get(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES);
    }
    
    public static String[] getFormattedCellValues(IAlgorithmContext context){
    	return (String[])context.get(ComputeColumnValuesStep.CONTEXT_KEY_FORMATTED_CELL_VALUES);
    }
    
    public static IColumn[] getConfigColumns(IAlgorithmContext context){
    	return (IColumn[])context.get(AbstractReport.CONTEXT_KEY_DATA_COLUMNS);
    }
    
    public static ICtColumn[] getCtHeaderColumns(IAlgorithmContext context){
    	return (ICtColumn[])context.get(ComputeColumnValuesStep.CONTEXT_KEY_HEADER_COLUMNS);
    }
    
    public static boolean getShowGrandTotal(IAlgorithmContext context){
    	return (Boolean)context.get(FlatReport.CONTEXT_KEY_SHOW_GRAND_TOTAL);
    }
    
    public static boolean getShowTotals(IAlgorithmContext context){
    	return (Boolean)context.get(FlatReport.CONTEXT_KEY_SHOW_TOTALS);
    }
    
    public static List<DataOutputFilter> getOutputFilterList(IAlgorithmContext context){
    	return (List<DataOutputFilter>)context.get(FlatReport.CONTEXT_KEY_DATA_OUT_FILTERS);
    }
}
