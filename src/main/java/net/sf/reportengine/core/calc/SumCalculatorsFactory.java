/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class SumCalculatorsFactory implements ICalculatorsFactory {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculatorsFactory#newInstance()
	 */
	public ICalculator newCalculatorInstance() {
		return new SumCalculator(); 
	}
}
