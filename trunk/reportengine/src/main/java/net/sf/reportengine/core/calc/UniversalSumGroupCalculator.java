package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

import net.sf.reportengine.util.ReportUtils;

/**
 * 
 * @author dragos balan
 */
public class UniversalSumGroupCalculator extends AbstractInitializedGroupCalculator <BigDecimal, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9060738221845931714L;
	

	/**
	 * 
	 */
	public void compute(Object value) {
		setValue(getResult().add(ReportUtils.createBigDecimal(value))); 
	}


	@Override
	protected BigDecimal getInitValue() {
		return BigDecimal.ZERO;
	}

}
