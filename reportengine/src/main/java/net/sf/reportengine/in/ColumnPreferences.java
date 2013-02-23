package net.sf.reportengine.in;

import net.sf.reportengine.config.HorizontalAlign;

public class ColumnPreferences {
	
	private String header; 
	private boolean isGroup; 
	private HorizontalAlign horizAlign;
	
	
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public void setHeader(String header) {
		this.header = header;
	}
	/**
	 * @return the isGroup
	 */
	public boolean isGroup() {
		return isGroup;
	}
	/**
	 * @param isGroup the isGroup to set
	 */
	public void setGroup(boolean isGroup) {
		this.isGroup = isGroup;
	}
	/**
	 * @return the horizAlign
	 */
	public HorizontalAlign getHorizAlign() {
		return horizAlign;
	}
	/**
	 * @param horizAlign the horizAlign to set
	 */
	public void setHorizAlign(HorizontalAlign horizAlign) {
		this.horizAlign = horizAlign;
	} 
	
	
}
