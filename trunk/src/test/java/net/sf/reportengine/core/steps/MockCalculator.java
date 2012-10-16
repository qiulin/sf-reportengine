/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.calc.AbstractCalculator;
import net.sf.reportengine.core.calc.CalculatorException;

/**
 * dummy implementation for ICalculator. 
 * It will always return the value passed as parameter to the contructor
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
	
	public MockCalculator(Object result){
		this.result = result;
	}
	public void compute(Object value) {}

	public Object getResult() {return result;}

	public void init() {}
}
