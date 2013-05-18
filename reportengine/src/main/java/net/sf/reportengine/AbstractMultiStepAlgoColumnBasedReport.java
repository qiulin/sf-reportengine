/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;

/**
 * abstract parent class for reports relying on one single iteration over the provided data
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 * @see FlatReport
 */
abstract class AbstractMultiStepAlgoColumnBasedReport extends AbstractColumnBasedReport {
	
	/**
     * the algorithm behind the report
     */
    private MultiStepAlgo algorithm ;
	
    /**
     * 
     * @param algo
     */
    public AbstractMultiStepAlgoColumnBasedReport(MultiStepAlgo algo) {
    	this.algorithm = algo; 
	}
    
    
    /**
     * Call this method for execution of the report<br> 
     * What this method does: <br>
     * <ul>
     *  <li>validates the configuration - validateConfig() method call</li>
     *  <li>opens the output - output.open()</li>
     *  <li>runs each algorithm execute() method</li>
     *  <li>closes the output - output.close()</li>
     * </ul>
     */
    @Override public void execute(){
    	validate(); 
        config();
        algorithm.execute(); 
    }
    
	
	/**
	 * 
	 * @return
	 */
	protected MultiStepAlgo getAlgorithm(){
		return algorithm; 
	}	
}
