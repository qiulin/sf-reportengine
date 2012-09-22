/**
 * 
 */
package net.sf.reportengine.filter;

import net.sf.reportengine.config.SubtotalsInfo;

/**
 * filter interface to totals output
 * 
 * @author dragos balan
 *
 */
public interface ITotalsOutputFilter {
	
	/**
	 * 
	 * @param info
	 * @return
	 */
	public boolean isDisplayable(SubtotalsInfo info);
	
}
