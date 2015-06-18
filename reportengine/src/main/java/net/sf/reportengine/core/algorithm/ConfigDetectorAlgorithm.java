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
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnMetadataHolder;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;

/**
 * @author dragos balan
 *
 */
public class ConfigDetectorAlgorithm implements Algorithm {
	
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.Algorithm#execute(java.util.Map)
	 */
	public Map<IOKeys, Object> execute(Map<IOKeys, Object> inputParams) {
		TableInput input = (TableInput)inputParams.get(IOKeys.REPORT_INPUT); 
		input.open(); //TODO: THIS IS NOT OK, we open the input in this algorithm ( => open a connection to the DB) 
		//and then we open it again in the next algorithm ( to loop through the rows)
		
		Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)inputParams.get(IOKeys.USER_COLUMN_PREFERENCES);
		
		
		List<ColumnMetadata> colMetadata = ((ColumnMetadataHolder)input).getColumnMetadata(); 
		
		//construct data columns
		List<DataColumn> dataColumns = ReportUtils.dataColsFromMetadataAndUserPrefs(colMetadata, colPrefs); 
		
		List<GroupColumn> groupColumns = null; 
		if(dataColumns !=  null && dataColumns.size() < colMetadata.size()){
			//there are group columns. let's detect them
			groupColumns = ReportUtils.groupColsFromMetadataAndUserPrefs(colMetadata, colPrefs);
		}//else{
			//no group column. All configured columns are data columns
		//}
		
		
		Map<IOKeys, Object> result = new EnumMap<IOKeys, Object>(IOKeys.class); 
		result.put(IOKeys.DATA_COLS, dataColumns); 
		result.put(IOKeys.GROUP_COLS, groupColumns);
		
		boolean reportHasCalculators = ReportUtils.atLeastOneDataColumHasCalculators(dataColumns);
		
		result.put(IOKeys.SHOW_TOTALS, reportHasCalculators); 
		result.put(IOKeys.SHOW_GRAND_TOTAL, reportHasCalculators);
		
		
		input.close();
		return result; 
	}
}
