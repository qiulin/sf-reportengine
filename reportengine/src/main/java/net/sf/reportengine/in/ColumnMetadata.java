/**
 * 
 */
package net.sf.reportengine.in;

import net.sf.reportengine.config.HorizontalAlign;

/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public final class ColumnMetadata {
	
	
	private String columnLabel; 
	
	private int columnType;
	
	private String columnId; 
	
	private HorizontalAlign horizontalAlign; 
	
	public ColumnMetadata(){
		
	}


	/**
	 * @return the columnLabel
	 */
	public String getColumnLabel() {
		return columnLabel;
	}


	/**
	 * @param columnLabel the columnLabel to set
	 */
	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}


	/**
	 * @return the columnType
	 */
	public int getColumnType() {
		return columnType;
	}


	/**
	 * @param columnType the columnType to set
	 */
	public void setColumnType(int columnType) {
		this.columnType = columnType;
	}


	/**
	 * @return the columnId
	 */
	public String getColumnId() {
		return columnId;
	}


	/**
	 * @param columnId the columnId to set
	 */
	public void setColumnId(String columnId) {
		this.columnId = columnId;
	}


	/**
	 * @return the horizontalAlign
	 */
	public HorizontalAlign getHorizontalAlign() {
		return horizontalAlign;
	}


	/**
	 * @param horizontalAlign the horizontalAlign to set
	 */
	public void setHorizontalAlign(HorizontalAlign horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}
	
}
