package net.sf.reportengine.core.steps.autodetect;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.util.IOKeys;

/**
 * this is just a PreviousRowManagerStep which verifies at runtime if the report has group columns 
 * in order to execute itself
 * 
 * @author dragos balan
 * @since 0.8
 *
 */
public class AutodetectPreviousRowManagerStep extends PreviousRowManagerStep{
	
	/**
	 * flag
	 */
	private boolean reportHasGroups; 
	
	/**
	 * 
	 */
	public void init(Map<IOKeys, Object> algoInput, AlgoContext context){
		super.init(algoInput, context); 
		
		reportHasGroups = getGroupColumns() != null && getGroupColumns().size() > 0;
	}
	
	@Override public void execute(NewRowEvent rowEvent) {
		if(reportHasGroups){
			super.execute(rowEvent);
		}
	}
}
