/*
 * Created on 06.04.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

/**
 * <p>
 * Simple count calculator
 *</p>
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class CountCalculator extends AbstractNumericCalculator {
    
    /**
     * the constructor makes a call to init() method
     */
    CountCalculator(){
        super();
    }
    
    /**
     *	this method increases the value of the internal counter 
     * @param newValue
     */
    public void compute(Object newValue) {
        if(newValue != null){
            value = value.add(new BigDecimal(1));
        }
    }
    
}
