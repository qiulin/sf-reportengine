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
package net.sf.reportengine.core.steps.autodetect;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnMetadataHolder;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;

public class ConstrGroupColsFromMetadataAndUserPrefsInitStep extends AbstractReportInitStep<List<GroupColumn>> {
	
	public StepResult<List<GroupColumn>> init(StepInput stepInput) {
		
		TableInput input = getReportInput(stepInput); 
		Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)stepInput.getAlgoInput(IOKeys.USER_COLUMN_PREFERENCES);
		
		List<ColumnMetadata> colMetadata = ((ColumnMetadataHolder)input).getColumnMetadata(); 
		
		//construct data columns
		List<DataColumn> dataColumns = (List<DataColumn> )stepInput.getContextParam(ContextKeys.INTERNAL_DATA_COLS); 
				
		List<GroupColumn> groupColumns = null; 
		if(dataColumns !=  null && dataColumns.size() < colMetadata.size()){
			//there are group columns. let's detect them
			groupColumns = ReportUtils.groupColsFromMetadataAndUserPrefs(colMetadata, colPrefs);
		}//else{
			//no group column. All configured columns are data columns
		//}
		
		/*************************************************
		 * this has been disabled for compilation purposes
		 ***************************************************/
		//inputParams.put(IOKeys.GROUP_COLS, groupColumns); 
		
		return new StepResult<List<GroupColumn>>(ContextKeys.INTERNAL_GROUP_COLS, groupColumns, IOKeys.GROUP_COLS); 
	}
}
