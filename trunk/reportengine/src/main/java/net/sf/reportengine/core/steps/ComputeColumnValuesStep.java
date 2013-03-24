package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.util.ContextKeys;

/**
 * This step is responsible for the following tasks :
 * 
 * 1. caches the cell values (by keeping an array of the values retrieved by DataColumn.getValue())
 * 2. caches the formatted cell values (also by using an array populated by DataColumn.formatValue())
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class ComputeColumnValuesStep extends AbstractReportStep{
	
	
	private int finalReportGroupCount = -1;
	private int finalReportColumnCount = -1; 
	private List<GroupColumn> groupCols = null;
	private List<DataColumn> dataColumns = null; 
			
	/**
	 * empty constructor
	 */
	public ComputeColumnValuesStep(){}
	
	/**
	 * this step's init method
	 */
	public void init(ReportContext context){
		super.init(context);
		
		groupCols = getGroupingColumns();
		dataColumns = getDataColumns();
		
		finalReportGroupCount = groupCols != null ? groupCols.size() : 0;
		finalReportColumnCount = finalReportGroupCount + dataColumns.size(); 
		
		context.set(ContextKeys.COMPUTED_CELL_VALUES, new Object[finalReportColumnCount]);
		context.set(ContextKeys.FORMATTED_CELL_VALUES, new String[finalReportColumnCount]);	
	}
	
	/**
	 * This particular implementation computes correct order for the values to be displayed 
	 * and calls the formatter for each column
	 * 
	 * @param newRowEvent the event associated to a new row
	 */
	public void execute(NewRowEvent newRowEvent){
		String[] formattedResults = new String[finalReportColumnCount];
		Object[] nonFormattedResults = new Object[finalReportColumnCount];
		Object[] previousRowGrpValues = getPreviousRowOfGroupingValues();
		
		Object valueForCurrentColumn = null; 
		
		//handle the grouping columns first
		GroupColumn currentGrpCol = null; 
		for(int i=0; i<finalReportGroupCount; i++){
			currentGrpCol = groupCols.get(i);
			valueForCurrentColumn = currentGrpCol.getValue(newRowEvent);
			nonFormattedResults[i] = valueForCurrentColumn;
			
			if(	currentGrpCol.showDuplicates() 
				|| previousRowGrpValues == null 
				|| !valueForCurrentColumn.equals(previousRowGrpValues[i])){
				formattedResults[i] = currentGrpCol.getFormattedValue(valueForCurrentColumn);
			}else{
				formattedResults[i] = IReportOutput.WHITESPACE;
			}
		}
		
		//then handle the data columns
		for(int i=0; i<dataColumns.size(); i++){
			valueForCurrentColumn = dataColumns.get(i).getValue(newRowEvent);
			nonFormattedResults[finalReportGroupCount+i] = valueForCurrentColumn;
			formattedResults[finalReportGroupCount+i] = dataColumns.get(i).getFormattedValue(valueForCurrentColumn);
		}
		
		getContext().set(ContextKeys.COMPUTED_CELL_VALUES, nonFormattedResults);
		getContext().set(ContextKeys.FORMATTED_CELL_VALUES, formattedResults);
	}
}
