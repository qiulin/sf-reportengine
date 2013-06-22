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
	
	CROSSTAB_METADATA, 
	
	INTERNAL_GROUP_COLS, 
	INTERNAL_DATA_COLS, 
	
	NEW_GROUPING_LEVEL, 
	
	CALCULATORS, 
	DISTRIBUTION_OF_CALCULATORS, 
	
	FORMATTED_CELL_VALUES, 
	LAST_GROUPING_VALUES, 
	
	INTERMEDIATE_DISTINCT_VALUES_HOLDER, 
	INTERMEDIATE_CROSSTAB_DATA_INFO, 
	INTERMEDIATE_ROW, 
	DATA_ROW_COUNT,
	
	SORTABLE_DATA_COLS_INDEXES, 
	IN_MEM_SORTED_RESULT,
	SORTED_FILES, 
	
	LOCAL_REPORT_INPUT, 
	LOCAL_REPORT_OUTPUT
	
	
}
