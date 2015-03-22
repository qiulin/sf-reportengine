/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedGroupLevelDetectorStep extends GroupLevelDetectorStep {
	
	
	 /**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getGroupColumnsCount()}
     * {@link #computeAggLevelForCalcRowNumber(int)}
     * {@link #computeCalcRowNumberForAggLevel(int)}
     * 
     * @return
     */
	@Override public List<GroupColumn> getGroupColumns(StepInput stepInput){
		return (List<GroupColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_GROUP_COLS);
	}
}
