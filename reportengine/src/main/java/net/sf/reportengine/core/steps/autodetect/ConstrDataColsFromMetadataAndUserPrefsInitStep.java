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
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;

/**
 * @author dragos balan
 *
 */
public class ConstrDataColsFromMetadataAndUserPrefsInitStep extends AbstractReportInitStep<List<DataColumn>> {
	
	public StepResult<List<DataColumn>> init(StepInput stepInput) {
		ReportInput input = getReportInput(stepInput); //(ReportInput)algoInput.get(IOKeys.REPORT_INPUT); 
		Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)stepInput.getAlgoInput(IOKeys.USER_COLUMN_PREFERENCES);
		
		List<ColumnMetadata> colMetadata = input.getColumnMetadata(); 
		
		//construct data columns
		List<DataColumn> dataColumns = ReportUtils.dataColsFromMetadataAndUserPrefs(colMetadata, colPrefs); 
		
		return new StepResult<List<DataColumn>>(ContextKeys.INTERNAL_DATA_COLS, dataColumns, IOKeys.DATA_COLS); 
	}
}
