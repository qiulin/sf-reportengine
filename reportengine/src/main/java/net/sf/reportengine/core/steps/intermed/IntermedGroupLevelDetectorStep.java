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
	
	
	 /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumnsLength()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @return
     */
	@Override public List<GroupColumn> getGroupColumns(){
		return (List<GroupColumn>)getAlgoContext().get(ContextKeys.INTERNAL_GROUP_COLS);
	}
}
