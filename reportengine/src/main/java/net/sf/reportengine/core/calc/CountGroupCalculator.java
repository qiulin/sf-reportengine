/*
 * Created on 06.04.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;

import org.apache.commons.lang.math.NumberUtils;



/**
 * <p>
 * Simple count calculator.
 * Keeps a counter of the objects passed to {@link #compute(Object)} until the 
 * counter is re-initialized through {@link #init()}
 *</p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class CountGroupCalculator extends AbstractInitializedGroupCalculator<Integer, Object> {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -722184855794011072L;
	
	
	/**
     * the constructor makes a call to init() method
     */
    CountGroupCalculator(){
        super();
    }
    
    /**
     *	this method increases the value of the internal counter 
     * @param newValue
     */
    public void compute(Object newValue) {
        if(newValue != null){
            setValue(getResult()+1); 
        }
    }

	@Override
	protected Integer getInitValue() {
		return NumberUtils.INTEGER_ZERO;
	}
    
    
}
