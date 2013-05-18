/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.InputKeys;

/**
 * @author Administrator
 *
 */
public abstract class AbstractCrosstabStep extends AbstractReportStep {
	
	
	/**
	 * returns the original set of grouping columns. This is just to differentiate between getGroupingColumns
	 * and getOriginalGroupingColumns. The getGroupingColumns returns the current group columns which may contain 
	 * runtime added groups (like in intermediate reports where I build groups out of row headers). 
	 * 
	 * getOriginalGroupingColumns returns what the user has set as group columns in the crosstab report
	 * 
	 * @return
	 */
//	public GroupColumn[] getOriginalCrosstabGroupingColumns(){
//		return (GroupColumn[])getContext().get(CrossTabReport.CONTEXT_KEY_CROSSTAB_GROUP_COLS);
//	}
	
	public Integer getOriginalCrosstabGroupingColsLength(){
		return (Integer)getInput().get(InputKeys.ORIGINAL_CT_GROUP_COLS_COUNT); 
	}
	
	public int getOriginalCrosstabDataColsLength(){
		return (Integer)getInput().get(InputKeys.ORIGINAL_CT_DATA_COLS_COUNT); 
	}
		
	public List<CrosstabHeaderRow> getCrosstabHeaderRows(){
		return (List<CrosstabHeaderRow>)getInput().get(InputKeys.CROSSTAB_HEADER_ROWS); 
	}
	 
	 public int getCrosstabHeaderRowsLength(){
		 return getCrosstabHeaderRows().size();
	 }
		
	 public CrosstabData getCrosstabData(){
		 //return (CrosstabData)getContext().get(ContextKeys.CROSSTAB_DATA); 
		 return (CrosstabData)getInput().get(InputKeys.CROSSTAB_DATA); 
	 }
	 
//	 public int[] getDataRelativePositionToHeader(){
//		 return (int[])getContext().get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_CROSSTAB_RELATIVE_POSITION);
//	 }
	 
	 /**
	  * getter for CROSSTAB_METADATA 
	  * 
	  * @return the crosstab metadata of the report
	  */
	 public DistinctValuesHolder getDistinctValuesHolder(){
		 return (DistinctValuesHolder)getContext().get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER); 
	 }
	 
	 public IntermediateDataInfo getIntermediateCrosstabDataInfo(){
		 return (IntermediateDataInfo)getContext().get(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
	 }
	 
	 public IntermediateReportRow getIntermediateRow(){
		 return (IntermediateReportRow)getContext().get(ContextKeys.INTERMEDIATE_ROW); 
	 }
	 
	 public boolean getShowTotalsInHeader(){
		 return true; //TODO: come back here
	 }
	
	 
	 	/**
	     * 
	     * @param from
	     * @param to
	     * @return
	     */
//	    public Object[] getPreviousGroupingValues(int groupingLevel) {
//	    	Object[] result = null;
//	    	int originalGroupingColsLength = getOriginalCrosstabGroupingColsLength();
//	    	if(groupingLevel > originalGroupingColsLength){
//	    		int resultLength = groupingLevel - originalGroupingColsLength; 
//	    		result = new Object[resultLength];
//	    		Object[] prevDataRow = getPreviousRowOfGroupingValues(); 
//	    		System.arraycopy(prevDataRow, originalGroupingColsLength, result, 0, resultLength); 
//	    	}else{
//	    		//throw new IllegalArgumentException("Request for previous grouping values from "+from+" to "+to+" cannot be fulfilled");
//	    	}
//	    	return result; 
//	    }
	    
	    
	    public int[] getPositionOfTotal(int from, int groupingLevel) {
	    	int[] result = null; 
	    	if(groupingLevel >= from){
	    		result = new int[groupingLevel-from+1];
	    		DistinctValuesHolder ctMetadata = getDistinctValuesHolder(); 
	    		Object[] prevDataRow = getPreviousRowOfGroupingValues(); 
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
