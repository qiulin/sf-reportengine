/*
 * Created on 14.01.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;


/**
 * this is a simple SumCalculator which counts the computed elements and returns
 * the result as the result of SumCalculator divided by count.
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
class AvgCalculator extends SumCalculator {
    
    /**
     * counter for computed elements 
     */
    int elementsCount;
    
    /**
     * average calculator constructor
     * calls super() -> init()
     */
    AvgCalculator(){
        super();
    }
    
    /**
     * initializer
     */
    public void init(){
        super.init();
        elementsCount = 0;
    }

    /**
     * compute
     */
    public void compute(Object newValue) throws CalculatorException {
        super.compute(newValue);
        elementsCount ++;
    }

    /**
     * result getter
     */
    public Object getResult(){
    	BigDecimal sum = (BigDecimal)super.getResult(); 
    	return sum.doubleValue() / elementsCount; 
        //return ((BigDecimal)super.getResult()).divide(new BigDecimal(elementsCount), BigDecimal.ROUND_HALF_EVEN);
    }

}
