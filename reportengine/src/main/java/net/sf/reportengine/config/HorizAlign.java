/**
 * 
 */
package net.sf.reportengine.config;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;


/**
 * enumeration of possible horizontal alignments
 * 
 * @author dragos balan
 * @since 0.6
 */
public enum HorizAlign {
	LEFT("left", "left", HSSFCellStyle.ALIGN_LEFT), 
	CENTER("center", "center", HSSFCellStyle.ALIGN_CENTER), 
	RIGHT("right", "right", HSSFCellStyle.ALIGN_RIGHT); 
	
	private String htmlCode; 
	private String foCode; 
	private short hssfCode; 
	
	private HorizAlign(String htmlCode, String foCode, short hssfCode){
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
