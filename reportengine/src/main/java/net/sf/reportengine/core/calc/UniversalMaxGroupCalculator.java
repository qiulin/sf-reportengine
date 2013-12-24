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
public class UniversalMaxGroupCalculator extends AbstractInitializedGroupCalculator<BigDecimal, Object> {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 142112476192764700L;

	
	
	/**
     * counts the maximum
     */
    public void compute(Object newValue) {
        if(newValue != null){
            BigDecimal newValueAsBd = ReportUtils.createBigDecimal(newValue); 
            if(getResult().compareTo(newValueAsBd) < 0){
            	setValue(newValueAsBd); 
            }
        }
    }


	@Override
	protected BigDecimal getInitValue() {
		return new BigDecimal(Double.MIN_VALUE); 
	}
}
