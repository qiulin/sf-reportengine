/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedGroupLevelDetectorStep extends GroupLevelDetectorStep {
	
	
	private IOKeys groupColIOKey = null; 
	private ContextKeys groupColContextKey = null; 
	
	public IntermedGroupLevelDetectorStep(ContextKeys groupColumnKey){
		this.groupColContextKey = groupColumnKey; 
	}
	
	public IntermedGroupLevelDetectorStep(IOKeys groupColumnKey){
		this.groupColIOKey = groupColumnKey; 
	}
	
	
	@Override 
	public List<GroupColumn> getGroupColumns(){
		if(groupColIOKey != null){
			return (List<GroupColumn>)getAlgoInput().get(groupColIOKey); 
		}else{
			//return (List<GroupColumn>)algoContext.get(ContextKeys.INTERNAL_GROUP_COLS);
			return (List<GroupColumn>)getAlgoContext().get(groupColContextKey); 
		}
	}
}
