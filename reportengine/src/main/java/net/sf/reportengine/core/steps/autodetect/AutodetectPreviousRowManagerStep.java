package net.sf.reportengine.core.steps.autodetect;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;

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
	public void init(ReportContext context){
		super.init(context); 
		
		reportHasGroups = getGroupingColumns() != null && getGroupingColumns().size() > 0;
	}
	
	@Override public void execute(NewRowEvent rowEvent) {
		if(reportHasGroups){
			super.execute(rowEvent);
		}
	}
}
