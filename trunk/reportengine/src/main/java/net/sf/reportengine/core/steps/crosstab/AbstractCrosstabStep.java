/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.DefaultDistinctValuesHolder;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractCrosstabStep<T,U,V> extends AbstractReportStep<T,U,V> {
	
	public List<CrosstabHeaderRow> getCrosstabHeaderRows(StepInput stepInput){
		return (List<CrosstabHeaderRow>)stepInput.getAlgoInput(IOKeys.CROSSTAB_HEADER_ROWS); 
	}
	 
	 public int getCrosstabHeaderRowsLength(StepInput stepInput){
		 return getCrosstabHeaderRows(stepInput).size();
	 }
		
	 public CrosstabData getCrosstabData(StepInput stepInput){
		 return (CrosstabData)stepInput.getAlgoInput(IOKeys.CROSSTAB_DATA); 
	 }
	 
	 public DefaultDistinctValuesHolder getDistinctValuesHolder(StepInput stepInput){
		 return (DefaultDistinctValuesHolder)stepInput.getContextParam(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER); 
	 }
	 
	 public IntermediateDataInfo getIntermediateCrosstabDataInfo(StepInput stepInput){
		 return (IntermediateDataInfo)stepInput.getContextParam(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
	 }
	 
	 public IntermediateReportRow getIntermediateRow(StepInput stepInput){
		 return (IntermediateReportRow)stepInput.getContextParam(ContextKeys.INTERMEDIATE_ROW); 
	 }
	 
	 public boolean getShowTotalsInHeader(){
		 return true; //TODO: come back here
	 }
	
	 
	 public int[] getPositionOfTotal(StepInput stepInput, int from, int groupingLevel) {
    	int[] result = null; 
    	if(groupingLevel >= from){
    		result = new int[groupingLevel-from+1];
    		DefaultDistinctValuesHolder ctMetadata = getDistinctValuesHolder(stepInput); 
    		Object[] prevDataRow = getPreviousRowOfGroupValues(stepInput); 
    		if(prevDataRow != null){
    			for(int i=from; i < groupingLevel+1; i++){
    				result[i-from] = ctMetadata.getIndexFor(i-from, prevDataRow[i]);
    			}
    		}else{
    			throw new IllegalArgumentException("Cannot determine the previous grouping values"); 
    		}
    	}
		return result;
	 }
}
