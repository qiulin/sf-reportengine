/*
 * Created on 05.04.2005
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;


/**
 * abstract implementation for ICalculator.
 * You can use this as basis for your own implementations
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 */
public abstract class AbstractNumericCalculator extends AbstractCalculator {
    
    public final static BigDecimal ZERO_VALUE = new BigDecimal("0");
    
    /**
     * the value to be computed
     */
    protected BigDecimal value;
    
    /**
     * simple constructor that calls init() method in order to prepare the
     * calculator for future usage
     */
    public AbstractNumericCalculator(){
        init();
    }
    
    public void init() {
        value = ZERO_VALUE;
    }
    
    /**
     * each subclass should override this method 
     * @param newValue
     */
    public abstract void compute(Object newValue) throws CalculatorException;

    /* (non-Javadoc)
     * @see net.sf.reportengine.core.calc.ICalculator#getResult()
     */
    public Object getResult(){
        return value;
    }
    
    /**
     * returns the name of the class and the value
     * @return a string
     */
    public String toString(){
        int lastIndexOfDot = this.getClass().toString().lastIndexOf(".");
        String className = this.getClass().toString().substring(lastIndexOfDot+1);
        return className+" [val="+value+"]";
    }    
}
