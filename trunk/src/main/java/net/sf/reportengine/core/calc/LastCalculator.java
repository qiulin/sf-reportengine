package net.sf.reportengine.core.calc;

public class LastCalculator extends AbstractCalculator {
	
	private Object last;
	
	public void compute(Object value) throws CalculatorException {
		last = value;
	}

	public Object getResult() {
		return last;
	}

	public void init() {}

}
