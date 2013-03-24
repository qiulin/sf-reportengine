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
	 * 
	 */
	private HorizontalAlign horizAlign; 
	
	/**
	 * 
	 * @param calcFactory
	 * @param formatter
	 */
	public AbstractCrosstabData(Calculator calc, 
								Format formatter, 
								HorizontalAlign horizAlign){
		setCalculator(calc);
		setFormatter(formatter);
		setHorizAlign(horizAlign); 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.CrosstabData#getFormattedValue(java.lang.Object)
	 */
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
