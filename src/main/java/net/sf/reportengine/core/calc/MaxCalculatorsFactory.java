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
	public ICalculator newInstance() {
		return new MaxCalculator(); 
	}

}
