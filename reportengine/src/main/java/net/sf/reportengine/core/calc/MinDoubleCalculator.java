/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos balan
 *
 */
public class MinDoubleCalculator extends AbstractMinCalculator<Double> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1495229040676746567L;
	

	@Override
	protected Double getInitValue() {
		return Double.MAX_VALUE; 
	}

}
