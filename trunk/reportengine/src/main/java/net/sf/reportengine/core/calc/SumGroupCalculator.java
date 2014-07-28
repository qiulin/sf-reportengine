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
public class SumGroupCalculator extends AbstractGroupCalculator<BigDecimal, DefaultCalcIntermResult<BigDecimal>,  Object> {
	
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = 7717198690428873303L;
	
	/**
	 * the default label
	 */
	public static final String LABEL = "Total";
	
	/**
	 * builds this group calculator with the default label
	 */
	public SumGroupCalculator(){
		this(LABEL); 
	}
	
	/**
	 * builds this group calculator with the given label
	 * 
	 * @param label	the label
	 */
	public SumGroupCalculator(String label){
		super(label); 
	}
	
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
