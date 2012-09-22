/*
 * Created on 05.04.2005
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * sum calculator implementation 
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 */
public class SumCalculator extends AbstractNumericCalculator {
    
    
    /**
     * constructor for SUM calculator  
     * calls the init() method
     */
    SumCalculator(){
        super();    
    }
    
    /**
     * if the new value is not null then we add the new value to the old one 
     * otherwise nothing 
     * this method doesn't thow NullPointerException if the newValue is null 
     */
    public void compute(Object newValue) throws CalculatorException {
        BigDecimal valueToAdd;
        if(newValue != null){
            if (newValue instanceof BigDecimal) {
                valueToAdd = (BigDecimal)newValue;
            }else{
                try{
                    valueToAdd = new BigDecimal(newValue.toString());
                }catch(NumberFormatException nex){
                    throw new CalculatorException(" Cannot use "+newValue+" for SumCalculator", nex);
                }
            }
            value = value.add(valueToAdd);
        }else{
            System.err.println("skipping null value ");
        }
    }
    
}
