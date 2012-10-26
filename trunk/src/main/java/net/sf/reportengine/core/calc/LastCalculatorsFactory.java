package net.sf.reportengine.core.calc;

public class LastCalculatorsFactory implements ICalculatorsFactory {

	public ICalculator newCalculatorInstance() {
		return new LastCalculator(); 
	}

}
