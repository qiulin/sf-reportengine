/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Arrays;

import org.apache.log4j.Logger;

import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * 
 * this is the manager of the previous row. Normally this should be the last 
 * step in your algorithm
 * 
 * @author Administrator
 *
 */
public class PreviousRowManagerStep extends AbstractReportStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(PreviousRowManagerStep.class);
	
	/**
     * context key for last grouping values
     */
    public static final String CONTEXT_KEY_LAST_GROUPING_VALUES = "net.sf.reportengine.prevGroupingValues";
	
    /**
	 * reference to the last row (this is very helpful since this class makes 
	 * comparisons between the current row and the last row
	 */
	private Object[] previousRowOfGroupingColumnValues;
    
    
	public void init(IAlgorithmContext reportContext){
		super.init(reportContext);
		
		
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override
	public void execute(NewRowEvent rowEvent) {
		
		//first time we initialize the last column values
		if(previousRowOfGroupingColumnValues == null){
			previousRowOfGroupingColumnValues = new Object[getGroupingColumnsLength()];
			copyGroupingValuesToLastRowOfGroupingColumnValues(getGroupingColumns(), rowEvent);
			
			getContext().set(CONTEXT_KEY_LAST_GROUPING_VALUES, previousRowOfGroupingColumnValues);
		}else{
			if(getGroupingLevel() > -1){
				copyGroupingValuesToLastRowOfGroupingColumnValues(getGroupingColumns(), rowEvent);
			}
		}
		
		if(logger.isTraceEnabled()){
    		logger.trace("previousRowOfGroupingValues "+Arrays.toString(previousRowOfGroupingColumnValues));
    	}
	}
	
	
	private void copyGroupingValuesToLastRowOfGroupingColumnValues(IGroupingColumn[] groupingCols, NewRowEvent newRowEvent){
    	for (int i = 0; i < groupingCols.length; i++) {
    		previousRowOfGroupingColumnValues[i] = groupingCols[i].getValue(newRowEvent);
    	}    	
    }
	
}
