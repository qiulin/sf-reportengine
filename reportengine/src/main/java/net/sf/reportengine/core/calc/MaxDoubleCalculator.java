/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos
 *
 */
public class MaxDoubleCalculator extends AbstractMaxCalculator<Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -340218578500883479L;

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.AbstractCalculator#getInitValue()
	 */
	@Override
	protected Double getInitValue() {
		return Double.MIN_VALUE; 
	}

}
