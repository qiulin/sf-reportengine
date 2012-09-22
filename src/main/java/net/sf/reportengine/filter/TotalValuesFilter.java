/**
 * 
 */
package net.sf.reportengine.filter;

import java.util.List;

import net.sf.reportengine.config.SubtotalsInfo;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * filter for totals value. 
 * Use the setExcluded values method ( or addExcluded ) to configure this filter 
 * and disable the showing of total rows containing those values
 * 
 * @author dragos balan
 *
 */
public class TotalValuesFilter extends AbstractTotalsOutputFilter {
	
	private int subtotalIndex;
	private List<Object> excludedSubtotalValue;
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.filter.ITotalsOutputFilter#allowDisplay(net.sf.reportengine.config.SubtotalsInfo)
	 */
	public boolean isDisplayable(SubtotalsInfo subtotalsInfo) {
        ICalculator[] calculatorsRow = subtotalsInfo.getGroupingCalculators();
        return !excludedSubtotalValue.contains(calculatorsRow[subtotalIndex].getResult());
	}

	/**
	 * @return the subtotalIndex
	 */
	public int getSubtotalIndex() {
		return subtotalIndex;
	}

	/**
	 * @param subtotalIndex the subtotalIndex to set
	 */
	public void setSubtotalIndex(int subtotalIndex) {
		this.subtotalIndex = subtotalIndex;
	}

	/**
	 * @return the excludedSubtotalValue
	 */
	public List<Object> getExcludedValues() {
		return excludedSubtotalValue;
	}

	/**
	 * @param excludedSubtotalValue the excludedSubtotalValue to set
	 */
	public void setExcludedValues(List<Object> excludedSubtotalValue) {
		this.excludedSubtotalValue = excludedSubtotalValue;
	}

}
