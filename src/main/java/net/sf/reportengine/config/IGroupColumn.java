/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * 
 * 
 * @author dragos balan
 *
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
	 * @return
	 */
	public String getFormattedValue(Object object);
	
}
