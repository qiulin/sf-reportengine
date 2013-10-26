/**
 * 
 */
package net.sf.reportengine.core.calc;

import java.math.BigDecimal;

import net.sf.reportengine.util.ReportUtils;

/**
 * @author dragos balan
 *
 */
public class UniversalMinCalculator extends AbstractInitializedCalculator<BigDecimal, Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3087517338733476349L;
	
	/**
	 * 
	 */
	public void compute(Object value) {
		BigDecimal newValueAsBd = ReportUtils.createBigDecimal(value); 
		if(getResult().compareTo(newValueAsBd) < 0){
			setValue(newValueAsBd); 
		}
		
	}

	@Override
	protected BigDecimal getInitValue() {
		return new BigDecimal(Double.MAX_VALUE); 
	}

}
