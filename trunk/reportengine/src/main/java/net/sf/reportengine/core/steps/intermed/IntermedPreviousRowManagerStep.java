/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedPreviousRowManagerStep extends PreviousRowManagerStep {
	
	@Override public List<GroupColumn> getGroupColumns(){
		return (List<GroupColumn>)getAlgoContext().get(ContextKeys.INTERNAL_GROUP_COLS); 
	}
	
	/**
	 * executes the super only if the report has group columns
	 */
	@Override public void execute(NewRowEvent newRow){
		if(getGroupColumnsLength() > 0 ){
			super.execute(newRow); 
		}
	}
}
