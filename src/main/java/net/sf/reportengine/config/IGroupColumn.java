/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * Group column for flat and crosstab reports
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public interface IGroupColumn {
	
	/**
	 * this appears in the final report as the title/header of the column
	 * @return
	 */
	public String getHeader(); 
	
	
	/**
	 * 
	 * @return
	 */
	public int getGroupingLevel();
	
	/**
	 * 
	 * @param newRowEvent
	 * @return
	 */
	public Object getValue(NewRowEvent newRowEvent); 
	
	/**
	 * returns the formatted value for the given object according to the 
	 * formatting rules of this grouping column
	 * @param object
	 */
	public String getFormattedValue(Object object);
	
	/**
	 * returns the horizontal alignment for this column
	 */
	public HorizontalAlign getHorizAlign(); 
	
}
