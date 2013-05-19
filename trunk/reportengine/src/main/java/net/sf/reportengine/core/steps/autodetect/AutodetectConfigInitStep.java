/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * constructs column configuration based on column metadata and user preferences
 * 
 * @author dragos balan
 * @since 0.8
 */
public class AutodetectConfigInitStep implements AlgorithmInitStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AutodetectConfigInitStep.class);

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.AlgorithmContext)
	 */
	public void init(Map<IOKeys,Object> algoInput, AlgorithmContext reportContext) {
		ReportInput input = (ReportInput)algoInput.get(IOKeys.REPORT_INPUT); 
		Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)algoInput.get(IOKeys.USER_COLUMN_PREFERENCES);
		
		LOGGER.info("Autodetecting the columns based on user preferences and input metadata"); 
		
		List<ColumnMetadata> colMetadata = input.getColumnMetadata(); 
		
		//construct data columns
		List<DataColumn> dataColumns = ReportUtils.dataColsFromMetadataAndUserPrefs(colMetadata, colPrefs); 
		LOGGER.debug("data columns detected : {}",dataColumns); 
		
		List<GroupColumn> groupColumns = null; 
		if(dataColumns !=  null && dataColumns.size() < colMetadata.size()){
			//there are group columns. let's detect them
			groupColumns = ReportUtils.groupColsFromMetadataAndUserPrefs(colMetadata, colPrefs);
		}//else{
			//no group column. All configured columns are data columns
		//}
		LOGGER.debug("group columns detected : {}",groupColumns); 
		
		//set the result in context
		//reportContext.set(ContextKeys.DATA_COLUMNS, dataColumns);
		//reportContext.set(ContextKeys.GROUP_COLUMNS, groupColumns); 
		
		algoInput.put(IOKeys.DATA_COLS, dataColumns) ;
		algoInput.put(IOKeys.GROUP_COLS, groupColumns); 
		
		boolean reportHasCalculators = ReportUtils.atLeastOneDataColumHasCalculators(dataColumns);
		//reportContext.set(ContextKeys.DETECTED_SHOW_TOTALS, reportHasCalculators); 
		//reportContext.set(ContextKeys.DETECTED_SHOW_GRAND_TOTAL, reportHasCalculators); 
		
		algoInput.put(IOKeys.SHOW_TOTALS, reportHasCalculators); 
		algoInput.put(IOKeys.SHOW_GRAND_TOTAL, reportHasCalculators);
	}
}
