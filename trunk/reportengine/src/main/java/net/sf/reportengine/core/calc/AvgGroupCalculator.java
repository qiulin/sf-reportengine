/**
 * 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;
import java.math.MathContext;

/**
 * Universal average calculator. 
 * This calculator is not optimized because: 
 * <ul>
 * <li>it accepts any values (i.e java.lang.Object objects and tries to convert them into BigDecimals 
 * (by using the conversion  new BigDecimal(object.toString()). </li>
 * <li>The whole internal computation is based on BigDecimals. </li>
 * </ul>
 * You are strongly encouraged to write your own avg calculator based on your needs and using your own types . 
 * 
 * @author dragos balan
 *
 */
public class AvgGroupCalculator implements GroupCalculator<BigDecimal, AvgCalcIntermResult<BigDecimal>, Object> {
	
	
	/**
	 * the serial version id
	 */
	private static final long serialVersionUID = -8084427675511918932L;
	
	/**
	 * the math context to be used when executing BigDecimal operations
	 */
	private final MathContext mathContext; 
	
	/**
	 * by default the number of exact decimals is 32
	 */
	public AvgGroupCalculator(){
		this(MathContext.DECIMAL32);
	}
	
	/**
	 * use this constructor if you want to control the number exact decimals
	 * @param mathContext
	 */
	public AvgGroupCalculator(MathContext mathContext){
		this.mathContext = mathContext; 
	}
	
	/**
	 * init method required by the GroupCalculator
	 */
	public AvgCalcIntermResult<BigDecimal> init() {
		return new AvgCalcIntermResult<BigDecimal>(BigDecimal.ZERO, 0); 
	}

	/**
	 * this compute method is based on the following formula: 
	 *  AVG[n+1] = (n * AVG[n] + element[n+1] ) / (n+1)
	 */
	public AvgCalcIntermResult<BigDecimal> compute(	AvgCalcIntermResult<BigDecimal> intermResult, 
													Object newValue) {
		BigDecimal newValueAsBD = new BigDecimal(newValue.toString()); 
		int count = intermResult.getCount(); 
		BigDecimal avg = intermResult.getResult()
				.multiply(new BigDecimal(count),mathContext)
				.add(newValueAsBD, mathContext)
				.divide(new BigDecimal(count+1), mathContext);  
		return new AvgCalcIntermResult<BigDecimal>(avg, count+1);
	}
}
