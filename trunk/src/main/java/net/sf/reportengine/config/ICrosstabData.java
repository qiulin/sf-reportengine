/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * @author Administrator
 *
 */
public interface ICrosstabData {
	
	public Object getValue(NewRowEvent newRowEvent);
	
	public String getFormattedValue(Object unformattedValue);
	
	public ICalculator getCalculator();
	
}
