package net.sf.reportengine.core.steps;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.ContextKeys;

/**
 * This step is responsible for the following tasks :
 * 
 * 1. caches the cell values (by keeping an array of the values retrieved by IDataColumn.getValue())
 * 2. caches the formatted cell values (also by using an array populated by IDataColumn.formatValue())
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class ComputeColumnValuesStep extends AbstractReportStep{
	
	
	private int finalReportGroupCount = -1;
	private int finalReportColumnCount = -1; 
	private IGroupColumn[] groupCols = null;
	private IDataColumn[] dataColumns = null; 
			
	/**
	 * empty constructor
	 */
	public ComputeColumnValuesStep(){}
	
	/**
	 * this step's init method
	 */
	public void init(IReportContext context){
		super.init(context);
		
		groupCols = getGroupingColumns();
		dataColumns = getDataColumns();
		
		finalReportGroupCount = groupCols != null ? groupCols.length : 0;
		finalReportColumnCount = finalReportGroupCount + dataColumns.length; 
		
		context.set(ContextKeys.CONTEXT_KEY_COMPUTED_CELL_VALUES, new Object[finalReportColumnCount]);
		context.set(ContextKeys.CONTEXT_KEY_FORMATTED_CELL_VALUES, new String[finalReportColumnCount]);	
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
		
		Object valueForCurrentColumn = null; 
		for(int i=0; i<finalReportGroupCount; i++){
			valueForCurrentColumn = groupCols[i].getValue(newRowEvent);
			nonFormattedResults[i] = valueForCurrentColumn; 
			formattedResults[i] = groupCols[i].getFormattedValue(valueForCurrentColumn);
		}
		for(int i=0; i<dataColumns.length; i++){
			valueForCurrentColumn = dataColumns[i].getValue(newRowEvent);
			nonFormattedResults[finalReportGroupCount+i] = valueForCurrentColumn;
			formattedResults[finalReportGroupCount+i] = dataColumns[i].getFormattedValue(valueForCurrentColumn);
		}
		
		getContext().set(ContextKeys.CONTEXT_KEY_COMPUTED_CELL_VALUES, nonFormattedResults);
		getContext().set(ContextKeys.CONTEXT_KEY_FORMATTED_CELL_VALUES, formattedResults);
	}
}
