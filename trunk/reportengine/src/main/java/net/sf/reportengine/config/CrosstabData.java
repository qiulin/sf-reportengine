/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculator;

/**
 * Crosstab data
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface CrosstabData {
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent);
	
	/**
	 * 
	 * @param unformattedValue
	 * @return
	 */
	public String getFormattedValue(Object unformattedValue);
	
	/**
	 * 
	 */
	public String getFormattedTotal(Object unformattedTotalValue);
	
	/**
	 * 
	 * @return
	 */
	public Calculator getCalculator();
	
	/**
	 * returns the horizontal alignment of the values of this column
	 */
	public HorizAlign getHorizAlign();
	
	/**
	 * returns the vertical alignment of the values of this column
	 * @return
	 */
	public VertAlign getVertAlign();
	
}
