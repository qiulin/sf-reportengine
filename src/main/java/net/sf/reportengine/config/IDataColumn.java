/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * The basic interface for a report data column
 * 
 * @author dragos balan
 *
 */
public interface IDataColumn{
	
	/**
	 * returns the header of the column. 
	 * The header will be displayed in the final report as the header of this column and 
	 * it shouldn't be confused with the column name in case the report input is an SQL query
	 * 
	 * @return the header of the report column
	 */
	public String getHeader();
	
	
	/**
	 * returns the formatted value ready to be displayed on the report
	 * @param value
	 * @return
	 */
	public String getFormattedValue(Object value);
	
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
	
	/**
	 * returns the calculator (if any) to be used on this column 
	 * @return
	 */
	public ICalculator getCalculator();
	
}
