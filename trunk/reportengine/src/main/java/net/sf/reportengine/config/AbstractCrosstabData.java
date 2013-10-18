/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.calc.Calculator;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public abstract class AbstractCrosstabData implements CrosstabData {
	
	/**
	 * 
	 */
	private Format formatter; 
	
	/**
	 * 
	 */
	private Calculator calculator; 
	
	/**
	 * the horizontal alignment for this crosstab data
	 */
	private HorizAlign horizAlign; 
	
	/**
	 * the vertical alignment for this crosstab data
	 */
	private VertAlign vertAlign; 
	
	/**
	 * 
	 * @param calc
	 * @param formatter
	 * @param horizAlign
	 */
	public AbstractCrosstabData(Calculator calc, 
								Format formatter, 
								HorizAlign horizAlign){
		this(calc, formatter, horizAlign, VertAlign.MIDDLE); 
	}
	
	/**
	 * 
	 * @param calc
	 * @param formatter
	 * @param horizAlign
	 * @param vertAlign
	 */
	public AbstractCrosstabData(Calculator calc, 
								Format formatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign){
		setCalculator(calc);
		setFormatter(formatter);
		setHorizAlign(horizAlign); 
		setVertAlign(vertAlign); 
	}
	
	
	public String getFormattedValue(Object unformattedValue) {
		String result = "";
		if(unformattedValue != null){
			if(formatter != null){
				result = formatter.format(unformattedValue);
			}else{
				result = unformattedValue.toString();
			}
		}
		return result; 
	}

	public Format getFormatter() {
		return formatter;
	}

	public void setFormatter(Format formatter) {
		this.formatter = formatter;
	}

	public Calculator getCalculator() {
		return calculator;
	}

	public void setCalculator(Calculator calc) {
		this.calculator = calc;
	}

	/**
	 * @return the horizAlign
	 */
	public HorizAlign getHorizAlign() {
		return horizAlign;
	}

	/**
	 * @param horizAlign the horizAlign to set
	 */
	public void setHorizAlign(HorizAlign horizAlign) {
		this.horizAlign = horizAlign;
	}
	
	/**
	 * @return the vertical alignment
	 */
	public VertAlign getVertAlign() {
		return vertAlign;
	}

	/**
	 * @param vertAlign the vertical alignment
	 */
	public void setVertAlign(VertAlign vertAlign) {
		this.vertAlign = vertAlign;
	}
}