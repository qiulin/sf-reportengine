/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * This calculator will return the first value passed after calling init. 
 * No matter how many times you call {@link #compute(Object)} it will always 
 * return the first value
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class FirstCalculator<T> extends AbstractCalculator<T, T> {
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = 6437488441945365316L;
	
	/**
	 * 
	 */
	private boolean first = true; 
	
	/**
	 * 
	 */
	public void compute(T newValue) {
		//remember only the first value
		if(first){
			setValue(newValue);  
			first = false; 
		}
	}
	
	/**
	 * 
	 */
	public void init() {
		first = true; 
	}
}
