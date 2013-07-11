/**
 * 
 */
package net.sf.reportengine.config;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;

/**
 * @author dragos balan
 *
 */
public enum VertAlign {
	TOP("top", "before", HSSFCellStyle.VERTICAL_TOP), 
	MIDDLE("middle", "center", HSSFCellStyle.VERTICAL_CENTER), 
	BOTTOM("bottom", "after", HSSFCellStyle.VERTICAL_BOTTOM);
	
	/**
	 * the html code for each alignment 
	 */
	private String htmlCode; 
	
	/**
	 * the formatting objects code for each alignment
	 */
	private String foCode; 
	
	/**
	 * the excel code for this vertical alignment
	 */
	private short hssfCode; 
	
	/**
	 * 
	 * @param htmlCode
	 * @param foCode
	 */
	private VertAlign(String htmlCode, String foCode, short hssfCode){
		this.htmlCode = htmlCode; 
		this.foCode = foCode; 
		this.hssfCode = hssfCode; 
	}
	
	public String getHtmlCode(){
		return htmlCode; 
	}
	
	public String getFoCode(){
		return foCode; 
	}
	
	public short getHssfCode(){
		return hssfCode; 
	}
}
