/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;


/**
 * <p>
 * Minimum calculator. 
 * 
 * </p> 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
abstract class AbstractMinGroupCalculator<T extends Comparable<T>> extends AbstractInitializedGroupCalculator<T, T> {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = 1001415188212673503L;

    /**
     * compute method
     */
    public void compute(T newValue) {
        if(newValue != null){
            if(newValue.compareTo(getResult()) < 0){
                setValue(newValue); 
            }
        }

    }
}
