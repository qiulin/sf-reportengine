/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author balan
 *
 */
public class FirstCalculatorsFactory implements ICalculatorsFactory {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculatorsFactory#newInstance()
	 */
	public ICalculator newCalculatorInstance() {
		return new FirstCalculator();
	}

}
