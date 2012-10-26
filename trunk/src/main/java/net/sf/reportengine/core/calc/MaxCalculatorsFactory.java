/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author balan
 *
 */
public class MaxCalculatorsFactory implements ICalculatorsFactory {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculatorsFactory#newInstance()
	 */
	public ICalculator newCalculatorInstance() {
		return new MaxCalculator(); 
	}

}
