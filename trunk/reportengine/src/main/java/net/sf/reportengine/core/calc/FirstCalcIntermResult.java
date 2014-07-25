/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos balan
 *
 */
public class FirstCalcIntermResult<T> extends DefaultCalcIntermResult<T> {
	
	/**
	 * flat for the first value passed to the calculator
	 */
	private final boolean isFirst; 
	
	
	/**
	 * 
	 * @param initialValue
	 * @param isFirst
	 */
	public FirstCalcIntermResult(T initialValue, boolean isFirst){
		super(initialValue); 
		this.isFirst = isFirst; 
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isFirst(){
		return this.isFirst; 
	}
}
