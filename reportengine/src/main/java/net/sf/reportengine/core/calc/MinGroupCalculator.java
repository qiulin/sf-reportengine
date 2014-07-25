/**
 * 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * This is an universal min group calculator. 
 * It accepts as new values any object and tries to convert them to BigDecimal ( by new BigDecimal(object.toString())). 
 * The new constructed big decimal is then compared to the previous min so that a new min is obtained. 
 * 
 * @author dragos balan
 */
public class MinGroupCalculator implements GroupCalculator<BigDecimal, DefaultCalcIntermResult<BigDecimal>, Object> {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 2910018078671866178L;
	
	/**
	 * 
	 */
	public DefaultCalcIntermResult<BigDecimal> init() {
		return new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(Double.MAX_VALUE)); 
	}
	
	/**
	 * 
	 */
	public DefaultCalcIntermResult<BigDecimal> compute(	DefaultCalcIntermResult<BigDecimal> intermResult, 
														Object newValue) {
		DefaultCalcIntermResult<BigDecimal> result = intermResult; 
		BigDecimal newValueAsBD = new BigDecimal(newValue.toString()); 
		if(intermResult.getResult().compareTo(newValueAsBD) > 0){
			result = new DefaultCalcIntermResult<BigDecimal>(newValueAsBD); 
		}
		return result;
	}
}
