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
public class MinGroupCalculator extends AbstractGroupCalculator<BigDecimal, DefaultCalcIntermResult<BigDecimal>, Object> {

	/**
	 * the default label
	 */
	public static final String LABEL = "Minimum"; 
	
	/**
	 * builds this group calculator with the default label
	 */
	public MinGroupCalculator(){
		this(LABEL);
	}
	
	/**
	 * builds this group calculator with a custom label
	 * 
	 * @param label	the label
	 */
	public MinGroupCalculator(String label){
		super(label); 
	}
	
	/**
	 * returns a BigDecimal having Double.MAX_VALUE value
	 */
	public DefaultCalcIntermResult<BigDecimal> init() {
		return new DefaultCalcIntermResult<BigDecimal>(new BigDecimal(Double.MAX_VALUE)); 
	}
	
	/**
	 * compares the previous result with the new value and returns the minimum
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
