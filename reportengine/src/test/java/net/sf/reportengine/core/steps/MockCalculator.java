/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.calc.AbstractCalculator;
import net.sf.reportengine.core.calc.Calculator;

/**
 * dummy implementation for Calculator. 
 * It will always return the value passed as parameter to the constructor
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class MockCalculator extends AbstractCalculator {
	
	
	/**
	 * serial version id
	 */
	private static final long serialVersionUID = -8229633349217325363L;
	
	/**
	 * the value to be returned when calling {@link #getResult()}
	 */
	private Object result; 
	
	/**
	 * constructor
	 * @param result
	 */
	public MockCalculator(Object result){
		this.result = result;
	}
	
	/**
	 * it doesn't do anything
	 */
	public void compute(Object value) {}
	
	/**
	 * retrieves the object set in the constructor
	 */
	public Object getResult() {return result;}
	
	/**
	 * it doesn't do anything
	 */
	public void init() {}
}
