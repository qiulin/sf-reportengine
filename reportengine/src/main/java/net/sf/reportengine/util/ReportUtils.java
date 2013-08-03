/**
 * 
 */
package net.sf.reportengine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.SortType;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public final class ReportUtils {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ReportUtils.class);
	
	
	/**
	 * the private constructor
	 */
	private ReportUtils(){}
	
	/**
     * check if all provided data columns have calculators assigned
     * @return
     */
    public static boolean atLeastOneDataColumHasCalculators(List<DataColumn> dataColumns){
    	boolean atLeastOneCalculator = false; 
    	for(DataColumn dataColumn: dataColumns){
    		if(dataColumn.getCalculator() != null){
    			atLeastOneCalculator = true;
    			break; 
    		}
    	}
    	return atLeastOneCalculator; 
    }
    
    
    /**
     * 
     * @param colMetadata
     * @param userPrefs
     * @return
     */
    public static List<DataColumn> dataColsFromMetadataAndUserPrefs(	List<ColumnMetadata> colMetadataList,
    																	Map<String, ColumnPreferences> userPrefs){
    	
    	LOGGER.debug("processing metadata {} and preferences {}", colMetadataList, userPrefs); 
    	List<DataColumn> resultDataColumns = new ArrayList<DataColumn>(); 
    	
    	//walk through column metadata and check if there's any user preferences 
		for (int colIndex = 0; colIndex < colMetadataList.size(); colIndex++) {
			ColumnMetadata columnMetadata = colMetadataList.get(colIndex);
			LOGGER.debug("searching user preferences for column {} with metadata {}", colIndex, columnMetadata); 
			if(userPrefs.containsKey(columnMetadata.getColumnId())){
				ColumnPreferences prefs = userPrefs.get(columnMetadata.getColumnId());
				LOGGER.debug("user preferences found {}", prefs); 
				if(!prefs.isGroup()){
					resultDataColumns.add(createDataColumn(	colIndex, 
															prefs, 
															columnMetadata)); 
				}
			}else{
				LOGGER.debug("no preferences found. Constructing data column only from metadata {}", columnMetadata); 
				resultDataColumns.add(createDataColumn(colIndex, columnMetadata)); 
			}
		}//end for COLUMN_METADATA
		
		return resultDataColumns; 
    }
    
    
    /**
     * 
     * @param colMetadata
     * @param userPrefs
     * @return
     */
    public static List<GroupColumn> groupColsFromMetadataAndUserPrefs(List<ColumnMetadata> colMetadataList, 
    																Map<String, ColumnPreferences> userPrefs){
    	List<GroupColumn> result = new ArrayList<GroupColumn>(); 
    	
    	//walk through column metadata and check if there's any user preferences 
		for (int colIndex = 0; colIndex < colMetadataList.size(); colIndex++) {
			ColumnMetadata columnMetadata = colMetadataList.get(colIndex);
			
			if(userPrefs.containsKey(columnMetadata.getColumnId())){
				ColumnPreferences prefs = userPrefs.get(columnMetadata.getColumnId());
				if(prefs.isGroup()){
					int groupLevel = result.size(); 
					result.add(createGroupColumn(	colIndex, 
												 	groupLevel, 
												 	prefs, 
												 	columnMetadata)); 
				}
			}
		}//end for COLUMN_METADATA
		
		return result; 
    }
    
    /**
     * 
     * @param columnIndex
     * @param prefs
     * @param metadata
     * @return
     */
    public static DataColumn createDataColumn(	int columnIndex, 
    											ColumnPreferences prefs, 
    											ColumnMetadata metadata){
		DefaultDataColumn result = new DefaultDataColumn(); 
		result.setHeader(prefs.getHeader() != null ? prefs.getHeader() : metadata.getColumnLabel() != null ? metadata.getColumnLabel() : "Column "+columnIndex); 
		result.setHorizAlign(prefs.getHorizAlign() != null ? prefs.getHorizAlign() : metadata.getHorizontalAlign() != null ? metadata.getHorizontalAlign() : HorizAlign.CENTER);
		result.setVertAlign(prefs.getVertAlign() != null ? prefs.getVertAlign() : VertAlign.MIDDLE);
		result.setInputColumnIndex(columnIndex);
		result.setCalculator(prefs.getCalculator()); 
		result.setFormatter(prefs.getFormatter());
		
		if(prefs.getSortLevel() > DataColumn.NO_SORTING){
			LOGGER.debug("computing sorting for column={} from prefs={} and colIndex={}", result.getHeader(), prefs.getSortLevel(), columnIndex); 
			result.setSortLevel(prefs.getSortLevel() == ColumnPreferences.DEFAULT_SORT_LEVEL ? columnIndex : prefs.getSortLevel()); 
			result.setSortType(prefs.getSortType()); 
		}else{
			LOGGER.debug("no sorting found for column {} ", result.getHeader()); 
		}
		
		return result; 
	}
	
    /**
     * 
     * @param columnIndex
     * @param metadata
     * @return
     */
	public static DataColumn createDataColumn(int columnIndex, ColumnMetadata metadata){
		DefaultDataColumn result = new DefaultDataColumn(); 
		result.setHeader(metadata.getColumnLabel() != null ? metadata.getColumnLabel() : "Column "+columnIndex); 
		result.setHorizAlign(metadata.getHorizontalAlign() != null ? metadata.getHorizontalAlign() : HorizAlign.CENTER); 
		result.setInputColumnIndex(columnIndex); 
		return result; 
	}
	
	/**
	 * 
	 * @param columnIndex
	 * @param groupingLevel
	 * @param prefs
	 * @param metadata
	 * @return
	 */
	public static GroupColumn createGroupColumn(	int columnIndex, 
													int groupingLevel, 
													ColumnPreferences prefs, 
													ColumnMetadata metadata){
		DefaultGroupColumn result = new DefaultGroupColumn(); 
		result.setHeader(prefs.getHeader() != null ? prefs.getHeader() : metadata.getColumnLabel() != null ? metadata.getColumnLabel() : "Column "+columnIndex);
		result.setHorizAlign(prefs.getHorizAlign() != null ? prefs.getHorizAlign() : metadata.getHorizontalAlign() != null ? metadata.getHorizontalAlign() : HorizAlign.CENTER); 
		result.setVertAlign(prefs.getVertAlign() != null ? prefs.getVertAlign() : VertAlign.MIDDLE); 
		result.setInputColumnIndex(columnIndex);
		result.setGroupingLevel(groupingLevel);
		
		if(prefs.getSortType() != null){
			result.setSortType(prefs.getSortType()); 
		}
		
		return result; 
	}
	
	/**
	 * checks if any of the preferences contain any sorting 
	 * 
	 * @param userColumnPrefs
	 * @return
	 */
	public static boolean isSortingInPreferences(Map<String, ColumnPreferences> userColumnPrefs){
		boolean result = false; 
		for (ColumnPreferences prefElem : userColumnPrefs.values()) {
			if(prefElem.getSortLevel() > DataColumn.NO_SORTING){
				result = true; 
				break; 
			}
		}
		return result;
	}
	
	/**
	 * 
	 * @param groupCols
	 * @param dataCols
	 * @return
	 */
	public static boolean isSortingInColumns(List<GroupColumn> groupCols, List<DataColumn> dataCols){
		boolean result = false;
		
		if(groupCols != null && !groupCols.isEmpty()){
			for (GroupColumn groupCol : groupCols) {
				if(groupCol.getSortType() != null && groupCol.getSortType() != SortType.NONE){
					result = true; 
					break; 
				}
			}
		}
		
		if(!result && dataCols != null && !dataCols.isEmpty()){
			for (DataColumn dataCol : dataCols) {
				if(dataCol.getSortLevel() > DataColumn.NO_SORTING){
					result = true; 
					break; 
				}
			}
		}
		
		LOGGER.debug("checking if {} and {} need sorting ? {}", groupCols, dataCols, result); 
		
		return result; 
	}
}
