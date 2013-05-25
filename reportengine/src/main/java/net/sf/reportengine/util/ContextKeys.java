/**
 * 
 */
package net.sf.reportengine.util;

/**
 * Enumeration for context keys
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public enum ContextKeys {
	
	
	/**
	 * the key used to identify the configuration columns inside the report context
	 */
//	DATA_COLUMNS, //= "net.sf.reportengine.dataColumns";
	
	
	/**
	 * the identifier of grouping columns inside the report context
	 */
//	GROUP_COLUMNS, //= "net.sf.reportengine.groupingColumns";
	
	
	/**
	 * the identifier of the column preferences
	 */
//	USER_COLUMN_PREFERENCES, 
	
	/**
	 * 
	 */
//	SHOW_TOTALS, // = "net.sf.reportengine.showTotals";
//	SHOW_GRAND_TOTAL, // = "net.sf.reportengine.showGrandTotal";
	
	/**
	 * 
	 */
//	CROSSTAB_HEADER_ROWS, // = "net.sf.reportengine.crosstab.headerRows"; 
//	CROSSTAB_DATA, // = "net.sf.reportengine.crosstab.data"; 
	CROSSTAB_METADATA, // = "net.sf.reportengine.crosstab.metadata";
	
	
	/**
	 * 
	 */
	//ORIGINAL_CT_GROUP_COLS_COUNT, // = "net.sf.reportengine.intermediate.origCtGroupColsCnt";
	//ORIGINAL_CT_DATA_COLS_COUNT, // = "net.sf.reportengine.intermediate.origCtDataColsCnt";
	INTERNAL_GROUP_COLS, 
	INTERNAL_DATA_COLS, 
	INTERMEDIATE_OUTPUT,

	/**
	 * context identifier (key) for aggregation level
	 */
	NEW_GROUPING_LEVEL, // = "net.sf.reportengine.newGroupingLevel";

	/**
	 * the context key for the calculator matrix
	 */
	CALCULATORS, // = "net.sf.reportengine.calculators";
	
	/**
	 * context key for distribution of calculators in the data columns
	 */
	DISTRIBUTION_OF_CALCULATORS, // = "net.sf.reportengine.calcDistribution";

	/**
	 * the context key for computed cell values
	 */
	COMPUTED_CELL_VALUES, // =  "net.sf.reportengine.compCellsValues";

	/**
	 * the context key for computed cell values
	 */
	FORMATTED_CELL_VALUES, // =  "net.sf.reportengine.formattedCellValues";


	/**
	 * context key for last grouping values
	 */
	LAST_GROUPING_VALUES, // = "net.sf.reportengine.prevGroupingValues";

	/**
	 * context key for the distinct values holder
	 */
	INTERMEDIATE_DISTINCT_VALUES_HOLDER, // = "net.sf.reportengine.intermediate.distinctValuesHolder";

	/**
	 * context key for intermediate crosstab info
	 */
	INTERMEDIATE_CROSSTAB_DATA_INFO, // = "net.sf.reportengine.intermediate.ctDataInfo";

	/**
	 * context key for intermediate row
	 */
	INTERMEDIATE_ROW, // = "net.sf.reportengine.int.intermediateRow";
	
	/**
	 * context key for data rows number
	 */
	DATA_ROW_COUNT,
	
	/**
	 * 
	 */
	SORTABLE_DATA_COLS_INDEXES, 
	
	/**
	 * 
	 */
	IN_MEM_SORTED_RESULT,
	
	SORTED_FILES
}
