/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

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
	
	private PriorityQueue<NewRowEvent> inMemoryResult ; 
	
	
	public InMemorySortStep(){
		
	}
	
	@Override 
	protected void executeInit(){
		inMemoryResult = new PriorityQueue<NewRowEvent>(
								100, 
								new NewRowComparator(	getGroupColumns(), 
														getDataColumns())); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public void execute(NewRowEvent rowEvent) {
		inMemoryResult.offer(rowEvent); 
	}
	
	public void exit(){
		List<NewRowEvent> arrayResult = new ArrayList<NewRowEvent>(inMemoryResult.size());
		
		while(!inMemoryResult.isEmpty()){
			arrayResult.add(inMemoryResult.poll()); 
		}
		
		//the result is ready for writing
		getAlgoContext().set(ContextKeys.IN_MEM_SORTED_RESULT, arrayResult); 
	}
}
