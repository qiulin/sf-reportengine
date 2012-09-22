/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * Maximum calculator 
 * @author dragos balan(dragos.balan@gmail.com)
 */
class MaxCalculator extends AbstractNumericCalculator {
    
    /**
     * 
     *
     */
    MaxCalculator(){
        super();
    }
    
    /**
     * compute mehtod which counts the maximum
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
                    throw new CalculatorException("Cannot use "+newValue+" for MaxCalculator !",ex);
                }
            }
            //max computation 
            if(valueToCompare.compareTo(value) > 0){
                value = valueToCompare;
            }
        }

    }
    
}
