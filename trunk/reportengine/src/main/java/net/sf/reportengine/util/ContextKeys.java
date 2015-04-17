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
	 * this key will not be taken into accound ( it's used for NO_RESULT steps)
	 */
	NO_KEY,
	
	/**
	 * this key is used in unit tests or in step results which are strictly destined 
	 * to the next algorithm ( so the IOKeys is the most important)
	 */
	SKIP_CONTEXT_KEY, 
	
	
	
	CROSSTAB_METADATA, 
	
	INTERNAL_GROUP_COLS, 
	INTERNAL_DATA_COLS, 
	
	NEW_GROUPING_LEVEL, 
	
	CALC_INTERM_RESULTS, 
	DISTRIBUTION_OF_CALCULATORS, 
	
	FORMATTED_CELL_VALUES, 
	LAST_GROUPING_VALUES, 
	
	INTERMEDIATE_DISTINCT_VALUES_HOLDER, 
	INTERMEDIATE_CROSSTAB_DATA_INFO, 
	INTERMEDIATE_ROW, 
	DATA_ROW_COUNT,
	
	SORTABLE_DATA_COLS_INDEXES, 
	IN_MEM_SORTED_RESULT,
	//SORTED_FILES, 
	
	LOCAL_REPORT_INPUT, 
	LOCAL_REPORT_OUTPUT,
	NEW_LOCAL_REPORT_OUTPUT, 
	
	INTERMEDIATE_CROSSTAB_OUTPUT
}
