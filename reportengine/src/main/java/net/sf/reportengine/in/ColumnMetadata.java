/**
 * 
 */
package net.sf.reportengine.in;

import net.sf.reportengine.config.HorizAlign;

/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public final class ColumnMetadata {
	
	/**
	 * the label of the column ( also known as column header)
	 */
	private String columnLabel; 
	
	/**
	 * the column identifier
	 */
	private String columnId; 
	
	/**
	 * the horizontal alignment
	 */
	private HorizAlign horizontalAlign; 
	
	
	/**
	 * the constructor
	 */
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
	public HorizAlign getHorizontalAlign() {
		return horizontalAlign;
	}


	/**
	 * @param horizontalAlign the horizontalAlign to set
	 */
	public void setHorizontalAlign(HorizAlign horizontalAlign) {
		this.horizontalAlign = horizontalAlign;
	}
	
}
