/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.SecondProcessGroupColumn;
import net.sf.reportengine.core.steps.AbstractCrosstabInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan
 *
 */
public class ConstrGrpColsForSecondProcessInitStep extends AbstractCrosstabInitStep<List<GroupColumn>>{
	
	
	public StepResult<List<GroupColumn>> init(StepInput stepInput) {
		List<GroupColumn> newGroupCols = constructGroupColumnsForSecondProcess(getGroupColumns(stepInput)); 
		//getAlgoContext().set(ContextKeys.INTERNAL_GROUP_COLS, newGroupCols); 
		
		return new StepResult<List<GroupColumn>>(ContextKeys.INTERNAL_GROUP_COLS, newGroupCols); 
	}

	/**
	 * creates a list of group columns for the second report based on the original group columns
	 * 
	 * @param originalGroupCols
	 * @return	a list of group columns necessary to the second processing
	 */
	protected List<GroupColumn> constructGroupColumnsForSecondProcess(List<GroupColumn> originalGroupCols){
		List<GroupColumn> result = null; 
		if(originalGroupCols != null && originalGroupCols.size() > 0){
			result = new ArrayList<GroupColumn>(originalGroupCols.size());
			for (GroupColumn originalGroupColumn : originalGroupCols) {
				result.add(new SecondProcessGroupColumn(originalGroupColumn));
			}
		}
		return result; 
	}
}
