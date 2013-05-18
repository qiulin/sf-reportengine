/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;

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
	
	@Override public void init(Map<InputKeys, Object> algoInput, ReportContext context){
		super.init(algoInput, context); 
		
		
		
		inMemoryResult = new PriorityQueue<NewRowEvent>(
								100, 
								new NewRowComparator(	getGroupingColumns(), 
														getDataColumns())); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override
	public void execute(NewRowEvent rowEvent) {
		LOGGER.info("processing new row {} ", rowEvent); 
		inMemoryResult.offer(rowEvent); 
	}
	
	public void exit(Map<InputKeys,Object> algoInput, ReportContext context){
		
		List<NewRowEvent> arrayResult = new ArrayList<NewRowEvent>(inMemoryResult.size());
		
		while(!inMemoryResult.isEmpty()){
			arrayResult.add(inMemoryResult.poll()); 
		}
		
		//the result is ready for writing
		context.set(ContextKeys.IN_MEM_SORTED_RESULT, arrayResult); 
		super.exit(algoInput, context); 
	}
}
