/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.calc.AbstractGroupCalculator;
import net.sf.reportengine.core.calc.GroupCalculator;

/**
 * dummy implementation for GroupCalculator. 
 * It will always return the value passed as parameter to the constructor
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class MockGroupCalculator<T, V> extends AbstractGroupCalculator<T, V> {
	
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = -8229633349217325363L;
	
	/**
	 * the value to be returned when calling {@link #getResult()}
	 */
	private T result; 
	
	/**
	 * constructor
	 * @param result
	 */
	public MockGroupCalculator(T result){
		this.result = result;
	}
	
	/**
	 * it doesn't do anything
	 */
	public void compute(V value) {}
	
	/**
	 * retrieves the object set in the constructor
	 */
	public T getResult() {return result;}
	
	/**
	 * it doesn't do anything
	 */
	public void init() {}
}
