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
public class CountGroupCalculator extends AbstractGroupCalculator<Integer, DefaultCalcIntermResult<Integer>, Object> {
	
	/**
	 * the label
	 */
	public static final String LABEL = "Count"; 
	
	/**
	 * constructor (sets the label = "Count")
	 */
	public CountGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * Use this constructor when you want to control the label
	 * 
	 * @param label	the label
	 */
	public CountGroupCalculator(String label){
		super(label); 
	}
	
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
