/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.InputKeys;

/**
 * This is used only for debug 
 * 
 * @author dragos balan 
 * 
 */
public class IntermediateRowDisplayStep extends AbstractCrosstabStep {
	
	
	
	public IntermediateRowDisplayStep(){
		
	}
	
	
	public void init(Map<InputKeys, Object> algoInput, ReportContext context){
		super.init(algoInput, context);
		
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override
	public void execute(NewRowEvent newRowEvent) {
		int groupingLevel = getGroupingLevel(); 
		
		if(groupingLevel >= 0){
			//if grouping level changed
			if(groupingLevel < getGroupingColumns().size()){
				//if grouping level changed for the GROUPING COLUMNS
				displayIntermediateDebugInfo(getIntermediateRow());
			}else{
				//if grouping level changed for the crosstabHeaderRows 
				
			}
		}else{
			//grouping level not changed
			
		}
	}
	
	
	public void exit(){
		displayIntermediateDebugInfo(getIntermediateRow());
	}
	
	private void displayIntermediateDebugInfo(IntermediateReportRow intermediateRow){
		ReportOutput output = getOutput(); 
		
		output.startDataRow(new RowProps());
		output.outputDataCell(new CellProps.Builder("Intermediate row:").build());
		for (IntermediateDataInfo element : intermediateRow.getIntermComputedDataList().getDataList()) {
			output.outputDataCell(new CellProps.Builder(element.toString()).build());
		}		
		output.endDataRow(); 
	}
}
