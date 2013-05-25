/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.IOKeys;

/**
 * This is used only for debug 
 * 
 * @author dragos balan 
 * 
 */
public class IntermediateRowDisplayStep extends AbstractCrosstabStep {
	
	
	
	public IntermediateRowDisplayStep(){
		
	}
	
	
	public void init(Map<IOKeys, Object> algoInput, AlgoContext context){
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
			if(groupingLevel < getGroupColumns().size()){
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
		ReportOutput output = getReportOutput(); 
		
		output.startDataRow(new RowProps());
		output.outputDataCell(new CellProps.Builder("Intermediate row:").build());
		for (IntermediateDataInfo element : intermediateRow.getIntermComputedDataList().getDataList()) {
			output.outputDataCell(new CellProps.Builder(element.toString()).build());
		}		
		output.endDataRow(); 
	}
}
