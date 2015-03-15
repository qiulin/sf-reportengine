package net.sf.reportengine.core.steps.autodetect;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;

public class ConstrGroupColsFromMetadataAndUserPrefsInitStep extends AbstractReportInitStep<List<GroupColumn>> {
	
	public StepResult<List<GroupColumn>> init(StepInput stepInput) {
		//Map<IOKeys, Object> algoInput = getAlgoInput(); 
		ReportInput input = getReportInput(stepInput); //(ReportInput)algoInput.get(IOKeys.REPORT_INPUT); 
		Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)stepInput.getAlgoInput(IOKeys.USER_COLUMN_PREFERENCES);
		
		List<ColumnMetadata> colMetadata = input.getColumnMetadata(); 
		
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
