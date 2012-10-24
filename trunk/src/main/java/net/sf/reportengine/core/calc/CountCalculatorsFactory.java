package net.sf.reportengine.core.calc;

public class CountCalculatorsFactory implements ICalculatorsFactory {

	public ICalculator newInstance() {
		return new CountCalculator(); 
	}

}
