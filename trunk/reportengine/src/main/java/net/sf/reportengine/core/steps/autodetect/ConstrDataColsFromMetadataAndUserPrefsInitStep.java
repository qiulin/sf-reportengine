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
package net.sf.reportengine.core.steps.autodetect;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;

/**
 * @author dragos balan
 *
 */
public class ConstrDataColsFromMetadataAndUserPrefsInitStep extends AbstractReportInitStep<List<DataColumn>> {
	
	public StepResult<List<DataColumn>> init(StepInput stepInput) {
		TableInput input = getReportInput(stepInput); //(ReportInput)algoInput.get(IOKeys.REPORT_INPUT); 
		Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)stepInput.getAlgoInput(IOKeys.USER_COLUMN_PREFERENCES);
		
		List<ColumnMetadata> colMetadata = input.getColumnMetadata(); 
		
		//construct data columns
		List<DataColumn> dataColumns = ReportUtils.dataColsFromMetadataAndUserPrefs(colMetadata, colPrefs); 
		
		return new StepResult<List<DataColumn>>(ContextKeys.INTERNAL_DATA_COLS, dataColumns, IOKeys.DATA_COLS); 
	}
}
