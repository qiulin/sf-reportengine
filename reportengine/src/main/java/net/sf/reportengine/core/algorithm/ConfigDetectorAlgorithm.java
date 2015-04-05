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
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.ReportInput;
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
		ReportInput input = (ReportInput)inputParams.get(IOKeys.REPORT_INPUT); 
		input.open(); //TODO: THIS IS NOT OK, we open the input in this algorithm ( => open a connection to the DB) 
		//and then we open it again in the next algorithm ( to loop through the rows)
		
		Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)inputParams.get(IOKeys.USER_COLUMN_PREFERENCES);
		
		
		List<ColumnMetadata> colMetadata = input.getColumnMetadata(); 
		
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
