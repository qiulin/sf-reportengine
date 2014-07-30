/**
 * 
 */
package net.sf.reportengine.core.calc;


/**
 * Interface for Group Calculators
 * 
 * @param R - type of the final result. Example: a count calculator should return a java.lang.Integer
 * @param I - type of the intermediate result (this should extend CalcIntermResult<R>). This is only used internally
 * @param V - the type of each new value (this is the value taken from the column where this calculator was set). 
 * 
 * @author dragos balan
 *
 */
public interface GroupCalculator <R, I extends CalcIntermResult<R>, V>{
	
	/**
	 * this is used for initializing the intermediate result. 
	 * <p>
	 * Example: for a COUNT calculator the initial value should be zero 
	 * (well, actually it should be an implementation of CalcIntermResult<Integer> containing ZERO as data) 
	 * </p>
	 * @return the initial value of the intermediate result
	 */
	public I init(); 
	
	/**
	 * compute the new value with the previous result and return a new result.
	 * 
	 * <p>
	 * Example: for a COUNT calculator after computing 10 records the next call to 
	 * this method should be :  compute(10, "whatever text"). This call should return 11. 
	 * </p>
	 * @param intermResult	the previous intermediate result ( it's the 10 in my example)
	 * @param newValue		the current value (it's the "whatever text" in my example above)
	 * @return
	 */
	public I compute(I intermResult, V newValue);
	
	/**
	 * returns the label to be displayed at the beginning of the total row. 
	 * Example: 
	 * 1. for a SumGroupCalculator this label should be "Total" so that each 
	 * total row to display Total and Grand Total at the end
	 * 2. for a AvgGroupCalculator this label should be "Average" 
	 * @return
	 */
	public String getLabel(); 
}
