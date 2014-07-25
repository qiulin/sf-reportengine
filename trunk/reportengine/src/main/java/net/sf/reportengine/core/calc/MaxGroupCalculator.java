package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * This is an universal max group calculator. 
 * It accepts as new values any object and tries to convert them to BigDecimal ( by new BigDecimal(object.toString())). 
 * The newly constructed BigDecimal is then compared to the previous max so that a new max is obtained. 
 * 
 * @author dragos balan
 */
public class MaxGroupCalculator implements GroupCalculator<BigDecimal, DefaultCalcIntermResult<BigDecimal>, Object> {

	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 1685207603024868298L;
	
	/**
	 * 
	 */
	public DefaultCalcIntermResult<BigDecimal> init() {
		return new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(Double.MIN_VALUE));
	}
	
	/**
	 * 
	 */
	public DefaultCalcIntermResult<BigDecimal> compute(
			DefaultCalcIntermResult<BigDecimal> intermResult, Object newValue) {
		DefaultCalcIntermResult<BigDecimal> result = intermResult; 
		BigDecimal newValueAsBD = new BigDecimal(newValue.toString()); 
		if(intermResult.getResult().compareTo(newValueAsBD) < 0){
			result = new DefaultCalcIntermResult<BigDecimal>(newValueAsBD); 
		}
		return result;
	}

	

}
