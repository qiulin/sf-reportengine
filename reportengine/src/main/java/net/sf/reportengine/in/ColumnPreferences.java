package net.sf.reportengine.in;

import java.text.Format;

import net.sf.reportengine.config.HorizontalAlign;
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
	private HorizontalAlign horizAlign = null;
	
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
	public ColumnPreferences header(String header) {
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
	public ColumnPreferences group(boolean isGroup) {
		this.isGroup = isGroup;
		return this; 
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
	public ColumnPreferences align(HorizontalAlign horizAlign) {
		this.horizAlign = horizAlign;
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
	public ColumnPreferences formatter(Format formatter) {
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
	public ColumnPreferences calculator(Calculator calculator) {
		this.calculator = calculator;
		return this; 
	} 
}