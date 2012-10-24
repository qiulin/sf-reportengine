package net.sf.reportengine.core.calc;

/**
 * responsible for creating AvgCalculators
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public class AvgCalculatorsFactory implements ICalculatorsFactory {

	public ICalculator newInstance() {
		return new AvgCalculator(); 
	}
}
