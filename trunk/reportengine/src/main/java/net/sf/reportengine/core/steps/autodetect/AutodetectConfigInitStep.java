/**
 * 
 */
package net.sf.reportengine.core.steps.autodetect;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.ReportUtils;

import org.apache.log4j.Logger;

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
	private static final Logger LOGGER = Logger.getLogger(AutodetectConfigInitStep.class);
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void init(ReportContext reportContext) {
		ReportInput input = reportContext.getInput(); 
		if(input.supportsMetadata()){//TODO: remove this when all input classes will suport metadata
			if(LOGGER.isInfoEnabled())LOGGER.info("Autodetecting the columns based on user preferences and input metadata"); 
			Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)reportContext.get(ContextKeys.USER_COLUMN_PREFERENCES);
			List<ColumnMetadata> colMetadata = input.getColumnMetadata(); 
			
			//construct data columns
			List<DataColumn> dataColumns = ReportUtils.dataColsFromMetadataAndUserPrefs(colMetadata, colPrefs); 
			if(LOGGER.isDebugEnabled())LOGGER.debug("data columns detected : "+dataColumns); 
			
			List<GroupColumn> groupColumns = null; 
			if(dataColumns !=  null && dataColumns.size() < colMetadata.size()){
				//there are group columns. let's detect them
				groupColumns = ReportUtils.groupColsFromMetadataAndUserPrefs(colMetadata, colPrefs);
			}//else{
				//no group column. All configured columns are data columns
			//}
			if(LOGGER.isDebugEnabled())LOGGER.debug("group columns detected : "+groupColumns); 
			
			//set the result in context
			reportContext.set(ContextKeys.DATA_COLUMNS, dataColumns);
			reportContext.set(ContextKeys.GROUP_COLUMNS, groupColumns); 
			
			boolean reportHasCalculators = ReportUtils.atLeastOneDataColumHasCalculators(dataColumns);
			reportContext.set(ContextKeys.SHOW_TOTALS, reportHasCalculators); 
			reportContext.set(ContextKeys.SHOW_GRAND_TOTAL, reportHasCalculators); 
    	}else{
    		LOGGER.error("non supporting metadata input used inside config of "+this.getClass());
    	}
	}
}
