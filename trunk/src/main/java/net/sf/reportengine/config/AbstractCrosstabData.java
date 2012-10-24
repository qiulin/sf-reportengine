/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.calc.ICalculatorsFactory;


/**
 * @author dragos balan
 *
 */
public abstract class AbstractCrosstabData implements ICrosstabData {

	private Format formatter; 
	
	private ICalculatorsFactory calculatorFactory; 
	
	/**
	 * 
	 * @param calcFactory
	 * @param formatter
	 */
	public AbstractCrosstabData(ICalculatorsFactory calcFactory, Format formatter){
		setCalculator(calcFactory);
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

	public ICalculatorsFactory getCalculator() {
		return calculatorFactory;
	}

	public void setCalculator(ICalculatorsFactory calcFactory) {
		this.calculatorFactory = calcFactory;
	}

}
