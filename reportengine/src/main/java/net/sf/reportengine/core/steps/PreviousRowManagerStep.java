/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.ContextKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * this is the manager of the previous row. Normally this should be the last 
 * step in your algorithm
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class PreviousRowManagerStep extends AbstractReportStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(PreviousRowManagerStep.class);
	/**
	 * reference to the last row (this is very helpful since this class makes 
	 * comparisons between the current row and the last row
	 */
	private Object[] previousRowOfGroupingColumnValues;
    
    /**
     * 
     */
	public void init(ReportContext reportContext){
		super.init(reportContext);
		
		
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override public void execute(NewRowEvent rowEvent) {
		
		//first time we initialize the last column values
		if(previousRowOfGroupingColumnValues == null){
			previousRowOfGroupingColumnValues = new Object[getGroupingColumnsLength()];
			copyGroupingValuesToLastRowOfGroupingColumnValues(getGroupingColumns(), rowEvent);
			
			getContext().set(ContextKeys.LAST_GROUPING_VALUES, previousRowOfGroupingColumnValues);
		}else{
			if(getGroupingLevel() > -1){
				copyGroupingValuesToLastRowOfGroupingColumnValues(getGroupingColumns(), rowEvent);
			}
		}
		
    	LOGGER.trace("previousRowOfGroupingValues {}", Arrays.toString(previousRowOfGroupingColumnValues));
	}
	
	/**
	 * copies the current row valus into the previousValues array
	 * @param groupingCols
	 * @param newRowEvent
	 */
	private void copyGroupingValuesToLastRowOfGroupingColumnValues(List<GroupColumn> groupingCols, NewRowEvent newRowEvent){
    	for (int i = 0; i < groupingCols.size(); i++) {
    		previousRowOfGroupingColumnValues[i] = groupingCols.get(i).getValue(newRowEvent);
    	}    	
    }
}
