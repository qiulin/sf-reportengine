/*
 * Created on 14.01.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;
import java.math.MathContext;


/**
 * Average calculator. 
 * This is just a SumNumberCalculator that divides the sum to the count of the elements passed 
 * as arguments to {@link #compute(Object)} method
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
class AvgNumberCalculator extends AbstractCalculator<BigDecimal, Number> {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -2717104274824984991L;

	private SumNumberCalculator sumCalculator = new SumNumberCalculator(); 
	private CountCalculator countCalculator = new CountCalculator(); 
	
	/**
	 * 
	 */
	public void init(){
		sumCalculator.init(); 
		countCalculator.init(); 
	}
	
    /**
     * compute
     */
    public void compute(Number newValue){
        sumCalculator.compute(newValue);
        countCalculator.compute(newValue); 
    }

    /**
     * result getter
     */
    public BigDecimal getResult(){
    	BigDecimal sum = sumCalculator.getResult(); 
    	return sum.divide(new BigDecimal(countCalculator.getResult()), MathContext.DECIMAL32); 
    }
}
