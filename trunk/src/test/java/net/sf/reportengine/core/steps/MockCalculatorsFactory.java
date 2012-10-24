/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.calc.ICalculatorsFactory;

/**
 * @author dragos balan
 *
 */
public class MockCalculatorsFactory implements ICalculatorsFactory {
	
	private Object value; 
	
	/**
	 * 
	 * @param value
	 */
	public MockCalculatorsFactory(Object value){
		this.value = value; 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculatorsFactory#newInstance()
	 */
	public ICalculator newInstance() {
		return new MockCalculator(value); 
	}

}
