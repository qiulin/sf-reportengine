/**
 * 
 */
package net.sf.reportengine.core.calc;

/**
 * @author dragos balan (dragos.balan@gmail.com)
 *
 */
public class FirstCalculator extends AbstractCalculator {
	
	private String INIT_VALUE = "net.sf.reportengine.impossible";
	
	private Object value;
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculator#compute(java.lang.Object)
	 */
	public void compute(Object newValue) throws CalculatorException {
		//remember only the first value
		if(INIT_VALUE.equals(value) && !value.equals(newValue)){
			value = newValue;
		}
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculator#getResult()
	 */
	public Object getResult() {
		return value;
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.calc.ICalculator#init()
	 */
	public void init() {
		value = INIT_VALUE;
	}

	
}
