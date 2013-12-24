/*
 * Created on 19.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.calc;


/**
 * Maximum calculator 
 * @author dragos balan(dragos dot balan at gmail dot com)
 */
abstract class AbstractMaxGroupCalculator<T extends Comparable<T>> extends AbstractInitializedGroupCalculator<T, T> {
    
    /**
	 * serial version id
	 */
	private static final long serialVersionUID = -3090912975869267129L;

    /**
     * counts the maximum
     */
    public void compute(T newValue) {
        if(newValue != null){
            if(newValue.compareTo(getResult()) > 0){
                setValue(newValue); 
            }
        }
    }
}
