/*
 * Created on 20.03.2005
 * Author: dragos balan
 *
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;

import org.apache.log4j.Logger;

/**
 * <p>
 * this computes the aggregation level for the current dataEvent. 
 * The protocol is : 
 * <ul>
 * 		<li>
 * 			when returning the level -1 then a simple data row has been found 
 * 			( none of the aggregation columns has been changed )
 * 		</li>
 * 		<li>
 * 			any level > 0 returned is the index of the first element changed in the 
 * 			aggregation columns.
 * 		</li>
 * </ul>
 * 
 * Example: <br>
 * Let's assume that we have a data matrix like : <br/>
 * <table>
 * 	<tr><td>1</td><td>2</td><td>3</td><td>4</td></tr>
 * 	<tr><td>1</td><td>2</td><td>3</td><td>5</td></tr>
 * 	<tr><td>1</td><td>1</td><td>6</td><td>7</td></tr>
 *  <tr><td>2</td><td>8</td><td>6</td><td>7</td></tr>
 * </table>
 * 
 * and all the columns {0,1,2} as aggregation columns
 * 
 * then : <br/>
 * 	when passing the first row to the execute method the result level will be -1 <br>
 *  when passing the second row to the same method the result will be -1 (because none of the columns 0,1,2 have changed)<br>
 *  when passing the third  row to the same method the result will be 1 (because this is the index of the first changed column)<br>
 *  when passing the second row to the same method the result will be 0  <br>
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.1
 * @deprecated when the migration to GroupColumns,HeaderRows and Data is complete please use GroupingLevelDetectorStep
 */
public class LevelDetectorStep extends AbstractReportStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(LevelDetectorStep.class);
	
    /**
     * context identifier (key) for aggregation level
     */
    public static final String CONTEXT_KEY_GROUPING_LEVEL = "net.sf.reportengine.groupingLevel";
    
    /**
     * context identifier (key) for aggregation columns
     */
    public static final String CONTEXT_KEY_AGG_COLUMNS_INDEX = "net.sf.reportengine.aggColumnsIndex";
	
    /**
     * context identifier for aggregation level count
     */
    public static final String CONTEXT_KEY_AGG_LEVEL_COUNT = "net.sf.reportengine.aggLevelCount";
    
    /**
     * context identifier for showTotalsForAggCols array
     */
    public static final String CONTEXT_KEY_SHOW_TOTALS_FOR_AGG_COLUMNS = "net.sf.reportengine.showTotalsForAggCols";
    
    /**
	 * reference to the last row (this is very helpful since this class makes 
	 * comparisons between the current row and the last row
	 */
	private Object[] lastRow;
    
	/**
     * these are the indexes of the columns that have to
     * be taken into account when calculating the aggregationLevel
     */
    //private int[] aggregationColumns;
	private AggInfo aggInfo;
    
    /**
     * the aggregation level
     */
	private int aggregationLevel = -1; 
	
    /**
     * Recommended constructor
     * @param aggregationColumns
     */
    public LevelDetectorStep(){}
    
	/**
     * registers some new values into the context
     * @see net.sf.reportengine.core.algorithm.IAlgorithmInitStep#init(IReportContext)()
     */
    public void init(IAlgorithmContext reportContext){
        super.init(reportContext);
        
        aggInfo = extractAggregationInfo(getConfigColumns());
        
        reportContext.set(CONTEXT_KEY_AGG_COLUMNS_INDEX, aggInfo.getAggColsIndexArray());
        reportContext.set(CONTEXT_KEY_AGG_LEVEL_COUNT, aggInfo.getAggColsCount());
        reportContext.set(CONTEXT_KEY_SHOW_TOTALS_FOR_AGG_COLUMNS, aggInfo.getShowTotalsOnAggLevelArray());
    }
    
	/**
	 * execute method
	 */
	public void execute(NewRowEvent rowEvent) {
        Object[] row = getComputedCellValues();
        
		//first time we cannot make any comparison so the return level is zero
		if(lastRow == null){
		    lastRow = new Object[aggInfo.getAggColsCount()];
			copyOnlyAggColumnsToLastRow(row);
			aggregationLevel = -1;
			logger.trace("evaluating first data row. ");			
		}else{
			//preparing the level detection
			boolean aggregationLevelFound = false;
			int i = 0;
		
			//iterate through last row for comparison with the new row of data
			while (!aggregationLevelFound && i < aggInfo.getAggColsCount()){
				
				//logger.trace("comparing "+lastRow[i]+" with "+row[aggregationColumns[i]]);
				
				//first time a different value is found on the new row
				if (!lastRow[i].equals(row[aggInfo.getAggColsIndexArray()[i]])) {
					copyOnlyAggColumnsToLastRow(row);
					//condition to exit from for loop
					aggregationLevelFound = true;
					
					//logger.trace(" they are different -> new agg level found  "+i);
				}else{
					i++;
				}           
			}
			//now set the aggregationLevel in the context
			aggregationLevel = aggregationLevelFound ? i : -1;        
		}
		
		logger.trace("setting aggregation level to "+aggregationLevel);
		getContext().set(CONTEXT_KEY_GROUPING_LEVEL, aggregationLevel);
	}
    
	/**
     * copies only the specified columns to lastRow 
     * @param row
     */
    private void copyOnlyAggColumnsToLastRow(Object[] source){
        for (int i = 0; i < aggInfo.getAggColsCount(); i++) {
            lastRow[i] = source[aggInfo.getAggColsIndexArray()[i]];
        }
    }
    
    /**
     * extracts the indexes of columns that should be taken 
     * into account for aggregation (level detection)
     * 
     * @param columns
     * @return
     */
    protected AggInfo extractAggregationInfo(IColumn[] columns){
    	int[] tempAggColsIndex = new int[columns.length];
    	boolean[] tempShowTotalsOnAggLevel = new boolean[columns.length];
    	int aggColumnsCount = 0;
    	for(int i=0; i< columns.length; i++){
    		int aggOrder = columns[i].getGroupingOrder();
    		if(aggOrder >= 0){
    			logger.trace(" column "+i+" has grouping order "+aggOrder);
    			tempAggColsIndex[aggOrder] = i;
    			tempShowTotalsOnAggLevel[aggOrder] = columns[i].isShowTotalsOnChange();
    			aggColumnsCount++;
    		}    		
    	}
    	
    	int[] resultAggOrder = new int[aggColumnsCount];
    	System.arraycopy(tempAggColsIndex, 0, resultAggOrder, 0, aggColumnsCount);
    	
    	boolean[] resultShowTotals = new boolean[aggColumnsCount];
    	System.arraycopy(tempShowTotalsOnAggLevel, 0, resultShowTotals, 0, aggColumnsCount);
    	
    	return new AggInfo(aggColumnsCount, resultAggOrder, resultShowTotals);
    }
    
    
    /**
     * POJO holding aggregation information
     * 
     * @author dragos balan
     *
     */
    public static class AggInfo{
    	
    	private int aggColsCount;
    	private int[] aggColsIndex; 
    	private boolean[] showTotalsOnAggLevel;
    	
    	public AggInfo(int aggColsCount, int[] aggColsIndex, boolean[] showTotalsOnAggLevel){
    		this.aggColsCount = aggColsCount;
    		this.aggColsIndex = aggColsIndex;
    		this.showTotalsOnAggLevel = showTotalsOnAggLevel;
    	}
    	
    	public int getAggColsCount(){
    		return aggColsCount;
    	}
    	
    	public int[] getAggColsIndexArray(){
    		return aggColsIndex;
    	}
    	
    	public boolean[] getShowTotalsOnAggLevelArray(){
    		return showTotalsOnAggLevel;
    	}
    }
   
}