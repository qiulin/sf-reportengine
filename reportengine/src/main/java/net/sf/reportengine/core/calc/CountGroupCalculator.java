/**
 * 
 */
package net.sf.reportengine.core.calc;

import org.apache.commons.lang.math.NumberUtils;

/**
 * Universal count calculator.
 * Counts the objects passed through {@code #compute(DefaultCalcIntermResult, Object)} method using the an immutable
 * {@code DefaultCalcIntermResult} object. 
 * 
 * @author dragos balan
 */
public class CountGroupCalculator implements GroupCalculator<Integer, DefaultCalcIntermResult<Integer>, Object> {
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = -3168637033035009530L;

	/**
	 * initializes the counter to zero
	 */
	public DefaultCalcIntermResult<Integer> init() {
		return new DefaultCalcIntermResult<Integer>(NumberUtils.INTEGER_ZERO);
	}
	
	/**
	 * increases the count 
	 */
	public DefaultCalcIntermResult<Integer> compute(DefaultCalcIntermResult<Integer> intermResult, Object newValue) {
		return new DefaultCalcIntermResult<Integer>(Integer.valueOf(intermResult.getResult().intValue()+ 1)); 
	}
}
