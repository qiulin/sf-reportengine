/**
 * 
 */
package net.sf.reportengine.util;

/**
 * @author dragos
 *
 */
public enum IOKeys {
	
	REPORT_INPUT, 
	
	REPORT_OUTPUT,
	
	DATA_COLS, 
	
	GROUP_COLS,
	
	SHOW_TOTALS, 
	
	SHOW_GRAND_TOTAL,
	
	USER_COLUMN_PREFERENCES,
	
	CROSSTAB_HEADER_ROWS, 
	
	CROSSTAB_DATA, 
	
	ORIGINAL_CT_DATA_COLS_COUNT, 
	
	ORIGINAL_CT_GROUP_COLS_COUNT, 
	
	CROSSTAB_METADATA, 
	
	DISTINCT_VALUES_HOLDER, //TODO: this one or the crosstab metadata should dissapear
	
	SORTED_FILES, 
	
	HAS_GROUP_VALUES_ORDERED
}
