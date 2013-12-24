/**
 * 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * @author dragos balan
 *
 */
public class UniversalAvgGroupCalculator extends AbstractGroupCalculator<BigDecimal, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8831284068664519075L;
	
	/**
	 * 
	 */
	private UniversalSumGroupCalculator sumCalculator = new UniversalSumGroupCalculator(); 
	
	/**
	 * 
	 */
	private CountGroupCalculator countCalculator = new CountGroupCalculator(); 
	
	
	/**
	 * 
	 */
	public void init() {
		sumCalculator.init(); 
		countCalculator.init(); 
	}

	/**
	 * 
	 */
	public void compute(Object value) {
		sumCalculator.compute(value); 
		countCalculator.compute(value); 
	}
	
	/**
     * result getter
     */
    public BigDecimal getResult(){
    	BigDecimal sum = sumCalculator.getResult(); 
    	return sum.divide(new BigDecimal(countCalculator.getResult()), MathContext.DECIMAL32); 
    }
	
}
