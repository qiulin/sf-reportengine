package net.sf.reportengine.core.calc;

public class CountCalculatorsFactory implements ICalculatorsFactory {

	public ICalculator newCalculatorInstance() {
		return new CountCalculator(); 
	}

}
