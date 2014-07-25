/**
 * 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * computes the sum of the values passed through the compute method. 
 * This implementation is not optimized because 
 * 	1.it uses the BigDecimal as result type 
 * 	2.it tries to work with any kind of object by converting it to BigDecimal (i.e new BigDecimal(object.toString())) 
 * 
 * @author dragos balan
 *
 */
public class SumGroupCalculator implements GroupCalculator<BigDecimal, DefaultCalcIntermResult<BigDecimal>,  Object> {
	
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = 7717198690428873303L;
	
	/**
	 * initializes with zero the intermediate result ( to start the sum computation)
	 */
	public DefaultCalcIntermResult<BigDecimal> init() {
		return new DefaultCalcIntermResult<BigDecimal>(BigDecimal.ZERO);
	}
	
	/**
	 * adds the new value to the previous intermediate result by converting it to BigDecimal.
	 */
	public DefaultCalcIntermResult<BigDecimal> compute(	DefaultCalcIntermResult<BigDecimal> intermResult, 
														Object newValue) {
		return new DefaultCalcIntermResult<BigDecimal>(
				intermResult.getResult().add(new BigDecimal(newValue.toString())));
	}

	

	
	
	
}
