/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.Arrays;

import net.sf.reportengine.config.IColumn;
import net.sf.reportengine.config.ICrosstabData;
import net.sf.reportengine.config.ICrosstabHeaderRow;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;

/**
 * @author dragos balan
 *
 */
public class CrosstabComputeValuesStep extends AbstractCrosstabStep {
	
	public static final String CONTEXT_KEY_CROSSTAB_COMPUTED_VALUES = "net.sf.reportengine.crosstab.computedValues";
	public static final String CONTEXT_KEY_CROSSTAB_FORMATTED_VALUES = "net.sf.reportengine.crosstab.formattedValues"; 
	public static final String CONTEXT_KEY_CROSSTAB_INTERMEDIATE_COLUMNS_COUNT = "net.sf.reportengine.crosstab.intermediateColumnCount";
	
	public static final int DATA_COLUMNS_COUNT = 1; 
	
	/**
	 * empty constructor
	 */
	public CrosstabComputeValuesStep(){}
	
	/**
	 * this step's init method
	 */
	public void init(IAlgorithmContext context){
		super.init(context);
		int groupingColumnsCount = getGroupingColumns().length;
		int headerRowsCount = getCrosstabHeaderRows().length; 
		
		int computedValuesLength = 	groupingColumnsCount + 
									headerRowsCount + 
									DATA_COLUMNS_COUNT; 
		
		context.set(CONTEXT_KEY_CROSSTAB_INTERMEDIATE_COLUMNS_COUNT, computedValuesLength); 
		context.set(CONTEXT_KEY_CROSSTAB_COMPUTED_VALUES, new Object[computedValuesLength]);
		context.set(CONTEXT_KEY_CROSSTAB_FORMATTED_VALUES, new String[computedValuesLength]);		
	}
	
	/**
	 * computes correct order for the values to be displayed 
	 * and calls the formatter for each column
	 * 
	 * @param newRowEvent the event associated to a new row
	 */
	public void execute(NewRowEvent newRowEvent){
		IColumn[] configColumns = getConfigColumns();
		ICrosstabHeaderRow[] headerRows = getCrosstabHeaderRows(); 
		ICrosstabData dataColumn = getCrosstabData();
		
		//Object[] results = new Object[configColumns.length];
		String[] formattedResults = new String[configColumns.length+headerRows.length+1];
		
		//first format the values in the grouping columns
		for(int i=0; i<configColumns.length; i++){
			formattedResults[i] = configColumns[i].getFormattedValue(newRowEvent);
		}
		
		//then format the values in the header rows
		for (int i = 0; i < headerRows.length; i++) {
			ICrosstabHeaderRow headerRow = headerRows[i]; 
			Object unformattedValue = headerRow.getValue(newRowEvent);
			formattedResults[configColumns.length + i] = unformattedValue.toString(); //TODO: come back here
		}
		
		//lastly format the values in the data column
		formattedResults[configColumns.length+headerRows.length] = dataColumn.getFormattedValue(newRowEvent); 
		
		getContext().set(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES, formattedResults); //TODO: come back here
		getContext().set(ComputeColumnValuesStep.CONTEXT_KEY_FORMATTED_CELL_VALUES, formattedResults);
	}

}
