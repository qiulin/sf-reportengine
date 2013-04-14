/**
 * 
 */
package net.sf.reportengine.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.in.ColumnMetadata;
import net.sf.reportengine.in.ColumnPreferences;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public final class ReportUtils {
	
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
    	List<DataColumn> resultDataColumns = new ArrayList<DataColumn>(); 
    	
    	//walk through column metadata and check if there's any user preferences 
		for (int colIndex = 0; colIndex < colMetadataList.size(); colIndex++) {
			ColumnMetadata columnMetadata = colMetadataList.get(colIndex);
			
			if(userPrefs.containsKey(columnMetadata.getColumnId())){
				ColumnPreferences prefs = userPrefs.get(columnMetadata.getColumnId());
				if(!prefs.isGroup()){
					resultDataColumns.add(createDataColumn(	colIndex, 
															prefs, 
															columnMetadata)); 
				}
			}else{
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
		result.setInputColumnIndex(columnIndex);
		result.setCalculator(prefs.getCalculator()); 
		result.setFormatter(prefs.getFormatter()); 
		
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
		result.setInputColumnIndex(columnIndex);
		result.setGroupingLevel(groupingLevel); 
		
		return result; 
	}
}
