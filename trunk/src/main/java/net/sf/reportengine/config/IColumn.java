/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * The interface for a report column configuration
 * 
 * @author dragos balan
 * @deprecated since 0.4 this interface was replaced by IGrouping and IDataColumn
 */
public interface IColumn {
	
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
	 * @param newRowEvent
	 * @return
	 */
	public String getFormattedValue(NewRowEvent newRowEvent);

	/**
	 * returns the grouping order for this column
	 * @return
	 */
	public int getGroupingOrder();
	
	
	/**
	 * returns the calculator (if any) to be used on this column 
	 * @return
	 */
	public ICalculator getCalculator();
	
	
	/**
	 * returns true if the totals should be displayed when values on this column are changed
	 * This has no effect if no there is no grouping order defined on this column
	 * 
	 * @return
	 */
	public boolean isShowTotalsOnChange();
}
