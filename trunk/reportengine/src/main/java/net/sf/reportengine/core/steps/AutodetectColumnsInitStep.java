/**
 * 
 */
package net.sf.reportengine.core.steps;

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
public class AutodetectColumnsInitStep implements AlgorithmInitStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = Logger.getLogger(AutodetectColumnsInitStep.class);
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void init(ReportContext reportContext) {
		ReportInput input = reportContext.getInput(); 
		if(input.supportsMetadata()){
			if(LOGGER.isInfoEnabled())LOGGER.info("Autodetecting the columns based on user preferences and input metadata"); 
			Map<String, ColumnPreferences> colPrefs = (Map<String, ColumnPreferences>)reportContext.get(ContextKeys.USER_COLUMN_PREFERENCES);
			ColumnMetadata[] colMetadata = input.getColumnMetadata(); 
			
			//construct data columns
			List<DataColumn> dataColumns = ReportUtils.dataColsFromMetadataAndUserPrefs(colMetadata, colPrefs); 
			List<GroupColumn> groupColumns = null; 
			if(dataColumns !=  null && dataColumns.size() < colMetadata.length){
				//there are group columns. let's detect them
				groupColumns = ReportUtils.groupColsFromMetadataAndUserPrefs(colMetadata, colPrefs);
			}//else{
				//no group column. All configured columns are data columns
			//}
			
			//set the result in context
			reportContext.set(ContextKeys.DATA_COLUMNS, dataColumns);
			reportContext.set(ContextKeys.GROUP_COLUMNS, groupColumns); 
    	}else{
    		LOGGER.error("non supporting metadata input used inside config of "+this.getClass());
    	}
	}
}
