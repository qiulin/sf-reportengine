package net.sf.reportengine.core.calc;

public class LastCalculatorsFactory implements ICalculatorsFactory {

	public ICalculator newInstance() {
		return new LastCalculator(); 
	}

}
