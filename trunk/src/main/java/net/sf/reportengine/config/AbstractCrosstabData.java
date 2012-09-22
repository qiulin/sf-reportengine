/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.calc.ICalculator;


/**
 * @author dragos balan
 *
 */
public abstract class AbstractCrosstabData implements ICrosstabData {

	private Format formatter; 
	
	private ICalculator calculator; 
	
	
	
	
	public AbstractCrosstabData(ICalculator calculator, Format formatter){
		setCalculator(calculator);
		setFormatter(formatter);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.ICrosstabData#getFormattedValue(java.lang.Object)
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

	public ICalculator getCalculator() {
		return calculator;
	}

	public void setCalculator(ICalculator calculator) {
		this.calculator = calculator;
	}

}
