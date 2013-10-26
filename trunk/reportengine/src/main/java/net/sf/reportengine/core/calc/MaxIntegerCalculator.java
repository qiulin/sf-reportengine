package net.sf.reportengine.core.calc;

public class MaxIntegerCalculator extends AbstractMaxCalculator<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2995234982845700402L;

	@Override
	protected Integer getInitValue() {
		return Integer.MIN_VALUE; 
	}
}
