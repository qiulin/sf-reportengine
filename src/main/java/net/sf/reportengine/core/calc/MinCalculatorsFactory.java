/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos balan
 *
 */
public class MinCalculatorsFactory implements ICalculatorsFactory {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculatorsFactory#newInstance()
	 */
	public ICalculator newInstance() {
		return new MinCalculator(); 
	}

}
