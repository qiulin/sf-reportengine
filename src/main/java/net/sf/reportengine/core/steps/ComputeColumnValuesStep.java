package net.sf.reportengine.core.steps;

import java.util.Arrays;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * This step is responsible for managing 3 important components of the report
 * 
 * 1. the computed cell values
 * 2. the formatted cell values
 * 3. the header columns ( for crosstab reports)
 * 
 * 
 * @author dragos balan
 * @since 0.3
 */
public class ComputeColumnValuesStep extends AbstractReportStep{
	
	
	/**
	 * the context key for computed cell values
	 */
	public final static String CONTEXT_KEY_COMPUTED_CELL_VALUES =  "net.sf.reportengine.compCellsValues";
	
	/**
	 * the context key for computed cell values
	 */
	public final static String CONTEXT_KEY_FORMATTED_CELL_VALUES =  "net.sf.reportengine.formattedCellValues";
	
	/**
	 * the context key for the header columns array
	 */
	public final static String CONTEXT_KEY_HEADER_COLUMNS = "net.sf.reportentinge.headerColumns";
	
	
	private int finalReportGroupCount = -1;
	private int finalReportColumnCount = -1; 
	private IGroupingColumn[] groupCols = null;
	private IDataColumn[] dataColumns = null; 
			
	/**
	 * empty constructor
	 */
	public ComputeColumnValuesStep(){}
	
	/**
	 * this step's init method
	 */
	public void init(IAlgorithmContext context){
		super.init(context);
		
		groupCols = getGroupingColumns();
		dataColumns = getDataColumns();
		
		finalReportGroupCount = groupCols != null ? groupCols.length : 0;
		finalReportColumnCount = finalReportGroupCount + dataColumns.length; 
		
		context.set(CONTEXT_KEY_COMPUTED_CELL_VALUES, new Object[finalReportColumnCount]);
		context.set(CONTEXT_KEY_FORMATTED_CELL_VALUES, new String[finalReportColumnCount]);	
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
		
		getContext().set(CONTEXT_KEY_COMPUTED_CELL_VALUES, nonFormattedResults);
		getContext().set(CONTEXT_KEY_FORMATTED_CELL_VALUES, formattedResults);
	}
}
