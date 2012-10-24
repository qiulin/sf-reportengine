/*
 * Created on 05.04.2005
 * Author: dragos balan
 */
package net.sf.reportengine.core.calc;

import java.io.Serializable;

/**
 * <p>
 *  Definition interface for any calculator
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public interface ICalculator extends Serializable{
    
    /**
     * initializer of the totals 
     * Example:<br>
     *  for a sum calculator the default initial value should be 0 <br>
     *  for a product calculator the default value should be 1
     */
    public void init();
    
    /**
     * compute the value
     * 
     * @param value    an object representing a new value to be computed
     */
    public void compute(Object value);
    
    /**
     * the result of computation
     * @return	result
     */
    public Object getResult();
}
