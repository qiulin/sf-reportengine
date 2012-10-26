/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * calculators factory class
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.5
 */
public interface ICalculatorsFactory {
	
	/**
	 * creates a new instance of the calculator
	 * @return
	 */
	public ICalculator newCalculatorInstance(); 
	
}
