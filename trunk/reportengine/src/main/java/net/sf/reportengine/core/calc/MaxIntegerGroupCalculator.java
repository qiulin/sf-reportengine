package net.sf.reportengine.core.calc;

public class MaxIntegerGroupCalculator extends AbstractMaxGroupCalculator<Integer> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2995234982845700402L;

	@Override
	protected Integer getInitValue() {
		return Integer.MIN_VALUE; 
	}
}
