/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.util.ContextKeys;

import org.apache.log4j.Logger;

/**
 * constructs column configuration based on column metadata and user preferences
 * 
 * @author dragos balan
 * @since 0.8
 */
public class AutodetectColumnsInitStep implements IAlgorithmInitStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = Logger.getLogger(AutodetectColumnsInitStep.class);
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep#init(net.sf.reportengine.core.algorithm.IReportContext)
	 */
	public void init(IReportContext reportContext) {
		if(reportContext.getInput().suppportsColumnMetadata()){
			if(LOGGER.isInfoEnabled())LOGGER.info("Autodetecting the columns ..."); 
			reportContext.getInput().open(); 
			ColumnMetadata[] colMetadata = reportContext.getInput().getColumnMetadata(); 
			Map<String, ColumnPreferences> userPreferences = (Map<String, ColumnPreferences>)reportContext.get(ContextKeys.USER_COLUMN_PREFERENCES); 
			
			//prepare the data/group column results
			List<IDataColumn> resultDataColumn = new ArrayList<IDataColumn>(); 
			List<IGroupColumn> resultGroupColumn = new ArrayList<IGroupColumn>(); 
			
			//walk through column metadata and check if there's any user preferences 
			for (int colIndex = 0; colIndex < colMetadata.length; colIndex++) {
				ColumnMetadata columnMetadata = colMetadata[colIndex];
				if(userPreferences.containsKey(columnMetadata.getColumnId())){
					ColumnPreferences prefs = userPreferences.get(columnMetadata.getColumnId());
					if(prefs.isGroup()){
						resultGroupColumn.add(createGroupColumn(colIndex, 
																resultGroupColumn.size(), 
																prefs, 
																columnMetadata)); 
					}else{
						resultDataColumn.add(createDataColumn(	colIndex, 
																prefs, 
																columnMetadata)); 
					}
				}else{
					resultDataColumn.add(createDataColumn(colIndex, columnMetadata)); 
				}
			}//end for columnMetadata
			
			
			//set the result in context
			reportContext.set(ContextKeys.DATA_COLUMNS, resultDataColumn);
			reportContext.set(ContextKeys.GROUPING_COLUMNS, resultGroupColumn); 
		}else{
			if(LOGGER.isInfoEnabled())LOGGER.info("The input doesn't support metadata. No autodetection of columns could be done."); 
		}
	}
	
	private IDataColumn createDataColumn(int columnIndex, ColumnPreferences prefs, ColumnMetadata metadata){
		DefaultDataColumn result = new DefaultDataColumn(); 
		result.setHeader(prefs.getHeader() != null ? prefs.getHeader() : metadata.getColumnLabel()); 
		result.setHorizAlign(prefs.getHorizAlign() != null ? prefs.getHorizAlign() : metadata.getHorizontalAlign()); 
		result.setInputColumnIndex(columnIndex);
		return result; 
	}
	
	private IDataColumn createDataColumn(int columnIndex, ColumnMetadata metadata){
		DefaultDataColumn result = new DefaultDataColumn(); 
		result.setHeader(metadata.getColumnLabel()); 
		result.setHorizAlign(metadata.getHorizontalAlign()); 
		result.setInputColumnIndex(columnIndex); 
		return result; 
	}
	
	private IGroupColumn createGroupColumn(	int columnIndex, 
											int groupingLevel, 
											ColumnPreferences prefs, 
											ColumnMetadata metadata){
		DefaultGroupColumn result = new DefaultGroupColumn(); 
		result.setHeader(prefs.getHeader() != null ? prefs.getHeader() : metadata.getColumnLabel());
		result.setHorizAlign(prefs.getHorizAlign() != null ? prefs.getHorizAlign() : metadata.getHorizontalAlign()); 
		result.setInputColumnIndex(columnIndex);
		result.setGroupingLevel(groupingLevel); 
		
		return result; 
	}	
}
