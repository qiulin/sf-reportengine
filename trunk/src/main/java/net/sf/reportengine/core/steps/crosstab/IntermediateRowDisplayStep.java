/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;

/**
 * @author Administrator
 *
 */
public class IntermediateRowDisplayStep extends AbstractCrosstabStep {
	
	
	
	public IntermediateRowDisplayStep(){
		
	}
	
	
	public void init(IAlgorithmContext context){
		super.init(context);
		
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override
	public void execute(NewRowEvent newRowEvent) {
		int groupingLevel = getGroupingLevel(); 
		
		if(groupingLevel >= 0){
			//if grouping level changed
			if(groupingLevel < getGroupingColumns().length){
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
		IReportOutput output = getOutput(); 
		
		output.startRow();
		output.output(new CellProps("Intermediate row:", 1, ReportContent.CONTENT_DATA));
		for (IntermediateDataInfo element : intermediateRow.getIntermComputedDataList().getDataList()) {
			output.output(new CellProps(element.toString(), 1, ReportContent.CONTENT_DATA));
		}		
		output.endRow(); 
	}
}
