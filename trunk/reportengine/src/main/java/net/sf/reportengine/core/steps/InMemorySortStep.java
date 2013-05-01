/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.ListSelectionEvent;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.ContextKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class InMemorySortStep extends AbstractReportStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(InMemorySortStep.class);
	
	private List<Object[]> inMemoryResult = new ArrayList<Object[]>(); ; 
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override
	public void execute(NewRowEvent rowEvent) {
		Comparable[] newRow = (Comparable[])getContext().get(ContextKeys.ARRANGED_FOR_SORT_ROW); 
		
		LOGGER.info("processing new row {} ", Arrays.toString(newRow)); 
		
		//iteration through existing results
		int position=0;
		boolean betterValueFound = false; 
		while(position < inMemoryResult.size() && !betterValueFound) {
			Object[] row = inMemoryResult.get(position); 
			
			for(int i=0; i<newRow.length; i++){
				LOGGER.info("comparing new value {} with old {} ", newRow[i], row[i]);
				//check the new value against the memory values
				if(newRow[i].compareTo(row[i]) < 0){
					LOGGER.info("found new value {} less then old value {}", newRow[i], row[i]);
					betterValueFound = true;
					break; //exit for
				}
			}
			
			position++;
		}
		
		if(inMemoryResult.size() ==0  || position > inMemoryResult.size()){
			LOGGER.info("adding new row {} at end of list (position = {})", Arrays.toString(newRow), position);
			inMemoryResult.add(newRow); 
		}else{
			LOGGER.info("adding new row {} at position {} ", Arrays.toString(newRow), position);
			//add the new row at the specified position 
			//and shifts all other values to the right
			inMemoryResult.add(position-1, newRow); 
		}
	}
	
	public void exit(ReportContext context){
		
		Object[][] arrayResult = inMemoryResult.toArray(new Object[][]{}); 
		
		//the result is ready for writing
		context.set(ContextKeys.IN_MEM_SORTED_RESULT, arrayResult); 
		super.exit(context); 
	}
}
