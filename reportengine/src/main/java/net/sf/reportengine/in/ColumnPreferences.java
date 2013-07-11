package net.sf.reportengine.in;

import java.text.Format;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.calc.Calculator;

/**
 * column preferences
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 */
public class ColumnPreferences {
	
	/**
	 * the column header
	 */
	private String header = null; 
	
	/**
	 * group flag
	 */
	private boolean isGroup = false; 
	
	/**
	 * horizontal alignment
	 */
	private HorizAlign horizAlign = null;
	
	/**
	 * vertical alignment
	 */
	private VertAlign vertAlign = null ;
	
	/**
	 * the formatter of the column
	 */
	private Format formatter = null; 
	
	/**
	 * the calculator
	 */
	private Calculator calculator = null; 
	
	
	/**
	 * @return the header
	 */
	public String getHeader() {
		return header;
	}
	/**
	 * @param header the header to set
	 */
	public ColumnPreferences setHeader(String header) {
		this.header = header;
		return this; 
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
	public ColumnPreferences setGroup(boolean isGroup) {
		this.isGroup = isGroup;
		return this; 
	}
	/**
	 * @return the horizAlign
	 */
	public HorizAlign getHorizAlign() {
		return horizAlign;
	}
	
	/**
	 * 
	 * @return the vertical align
	 */
	public VertAlign getVertAlign(){
		return vertAlign; 
	}
	
	/**
	 * @param horizAlign the horizAlign to set
	 */
	public ColumnPreferences setHAlign(HorizAlign horizAlign) {
		this.horizAlign = horizAlign;
		return this; 
	}
	
	/**
	 * @param vertAlign the horizAlign to set
	 */
	public ColumnPreferences setVAlign(VertAlign vertAlign) {
		this.vertAlign = vertAlign;
		return this; 
	}
	
	/**
	 * @return the formatter
	 */
	public Format getFormatter() {
		return formatter;
	}
	/**
	 * @param formatter the formatter to set
	 */
	public ColumnPreferences setFormatter(Format formatter) {
		this.formatter = formatter;
		return this; 
	}
	/**
	 * @return the calculator
	 */
	public Calculator getCalculator() {
		return calculator;
	}
	/**
	 * @param calculator the calculator to set
	 */
	public ColumnPreferences setCalculator(Calculator calculator) {
		this.calculator = calculator;
		return this; 
	} 
}