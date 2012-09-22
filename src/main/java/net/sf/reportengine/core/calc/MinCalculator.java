/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * <p>
 * Minimum calculator
 * </p> 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
class MinCalculator extends AbstractNumericCalculator {
    
    /**
     * min calculator constructor
     *
     */
    MinCalculator(){
        super();
    }
    
    public void init() {
        value = new BigDecimal(Integer.MAX_VALUE);
    }
    
    /**
     * compute method
     */
    public void compute(Object newValue) throws CalculatorException {
        
        if(newValue != null){
            BigDecimal valueToCompare = null;
            if(newValue instanceof BigDecimal){
                valueToCompare = (BigDecimal)newValue;
            }else{
                try{
                    valueToCompare = new BigDecimal(newValue.toString());
                }catch(NumberFormatException ex){
                    throw new CalculatorException("Cannot use "+newValue+" for MinCalculator !",ex);
                }
            }
            //min computation 
            if(valueToCompare.compareTo(value) < 0){
                value = valueToCompare;
            }
        }

    }
    
}
