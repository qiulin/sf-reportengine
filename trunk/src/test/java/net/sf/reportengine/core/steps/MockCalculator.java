/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.calc.AbstractCalculator;
import net.sf.reportengine.core.calc.CalculatorException;

/**
 * @author dragos
 *
 */
public class MockCalculator extends AbstractCalculator {
	
	
	private Object result; 
	
	public MockCalculator(Object result){
		this.result = result;
	}
	public void compute(Object value) throws CalculatorException {}

	public Object getResult() {return result;}

	public void init() {}
}
