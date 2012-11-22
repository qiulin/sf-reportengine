/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.algorithm.IReportAlgorithm;
import net.sf.reportengine.core.algorithm.OneIterationAlgorithm;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 *
 */
public abstract class AbstractOneIterationReport extends AbstractReport {
	
	/**
     * the algorithm behind flat reports
     */
    private OneIterationAlgorithm algorithm = new OneIterationAlgorithm();
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.AbstractReport#executeAlgorithm()
	 */
	@Override protected void executeAlgorithm(){
    	algorithm.executeAlgorithm();
    }
	
	public IReportAlgorithm getAlgorithm(){
		return algorithm; 
	}	
	
}
