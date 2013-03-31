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
	 * @return
	 */
	public Calculator getCalculator();
	
	/**
	 * 
	 * @return
	 */
	public HorizAlign getHorizAlign(); 
	
}
