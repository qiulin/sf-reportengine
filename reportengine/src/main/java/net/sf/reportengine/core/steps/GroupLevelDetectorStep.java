/*
 * Created on 20.03.2005
 * Author: dragos balan
 *
 */
package net.sf.reportengine.core.steps;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
 * and all the columns {0,1,2} as grouping columns
 * 
 * then : <br/>
 * 	when passing the first row to the execute method the result level will be -1 <br>
 *  when passing the second row to the same method the result will be -1 (because none of the columns 0,1,2 have changed)<br>
 *  when passing the third  row to the same method the result will be 1 (because this is the index of the first changed column)<br>
 *  when passing the second row to the same method the result will be 0  <br>
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class GroupLevelDetectorStep extends AbstractReportStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(GroupLevelDetectorStep.class);
    
    /**
     * the aggregation level
     */
	private int aggregationLevel = -1; 
	
    /**
     * Recommended constructor
     * @param aggregationColumns
     */
    public GroupLevelDetectorStep(){}
    
    
	/**
	 * execute method
	 */
	public void execute(NewRowEvent newRowEvent) {
        
		//first time we cannot make any comparison so the return level is zero
		if(getPreviousRowOfGroupingValues() == null){
			//handle last row for grouping columns 
			aggregationLevel = -1;
		}else{
			List<GroupColumn> groupingCols = getGroupColumns();
			aggregationLevel = checkLevelChangedInGroupingColumns(groupingCols, getPreviousRowOfGroupingValues(), newRowEvent); 
			LOGGER.trace("For newRow={} the grouping level found is {}", newRowEvent, aggregationLevel);
		}
		
		//set the result in context
		getAlgoContext().set(ContextKeys.NEW_GROUPING_LEVEL, aggregationLevel);
	}
    
    
    private int checkLevelChangedInGroupingColumns(	List<GroupColumn> groupingColumns, 
    												Object[] lastRowOfGroupingValues, 
    												NewRowEvent newRowEvent){
		boolean aggregationLevelFound = false;
		int i = 0;
		
		//TODO: groupings assumed ordered by grouping order: make sure they are ordered
		//iterate through last row for comparison with the new row of data
		GroupColumn currentGroupColumn = null; 
		while (!aggregationLevelFound && i < groupingColumns.size()){
			currentGroupColumn = groupingColumns.get(i); 
			Object valueToBeCompared = currentGroupColumn.getValue(newRowEvent);
			LOGGER.trace("checking column {} "+currentGroupColumn.getClass()); 
			LOGGER.trace(" 		having grouping order {}", currentGroupColumn.getGroupingLevel()); 
			LOGGER.trace(" 		and value {} against {}", valueToBeCompared,lastRowOfGroupingValues[i]);
			if (!lastRowOfGroupingValues[i].equals(valueToBeCompared)) {
				//condition to exit from for loop
				aggregationLevelFound = true;
			}else{
				i++;
			}           
		}// end while
		return aggregationLevelFound ? i:-1; 
    }
}