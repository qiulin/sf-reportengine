/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * This is an immutable wrapper around the value stored as the intermediate result of the group computations. 
 * It is used for SumGroupCalculator, CountGroupCalculator, MaxGroupCalculator and others. 
 * 
 * @author dragos balan
 *
 */
public class DefaultCalcIntermResult<E> implements CalcIntermResult<E> {
	
	/**
	 * the result 
	 */
	private final E result; 
	
	/**
	 * constructs this wrapper based on the value/result
	 * @param initialValue
	 */
	public DefaultCalcIntermResult(E initialValue){
		this.result = initialValue; 
	}
	
	/**
	 * getter for the result
	 */
	public E getResult(){
		return result; 
	}
}
