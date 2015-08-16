/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
public enum StepIOKeys {
	
	/**
	 * this key will not be taken into account (it is used for NO_RESULT steps)
	 */
	NO_KEY,
	
	/**
	 * this key is used in unit tests or in step results which are strictly destined 
	 * to the next algorithm (so the IOKeys is the most important)
	 */
	SKIP_CONTEXT_KEY, 
	
	CROSSTAB_METADATA, 
	
	INTERNAL_GROUP_COLS, 
	
	INTERNAL_DATA_COLS, 
	
	NEW_GROUPING_LEVEL, 
	
	CALC_INTERM_RESULTS, 
	
	DISTRIBUTION_OF_CALCULATORS, 
	
	LAST_GROUPING_VALUES, 
	
	INTERMEDIATE_DISTINCT_VALUES_HOLDER, 
	
	INTERMEDIATE_CROSSTAB_DATA_INFO, 
	
	INTERMEDIATE_ROW, 
	
	DATA_ROW_COUNT,
	
	SORTABLE_DATA_COLS_INDEXES, 
	
	IN_MEM_SORTED_RESULT,
	
	LOCAL_REPORT_INPUT, 

	INTERMEDIATE_CROSSTAB_OUTPUT
}