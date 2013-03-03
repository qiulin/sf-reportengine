/**
 * 
 */
package net.sf.reportengine;

import net.sf.reportengine.core.algorithm.IAlgorithm;

/**
 * abstract parent class for reports relying on one single iteration over the provided data
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 * @see FlatReport
 */
abstract class AbstractOneStepReport extends AbstractReport {
	
	/**
     * the algorithm behind 
     */
    private IAlgorithm algorithm ;
	
    /**
     * configures the reports 
     */
    protected abstract void configAlgorithmSteps();
    
    /**
     * 
     * @param algo
     */
    public AbstractOneStepReport(IAlgorithm algo) {
    	this.algorithm = algo; 
	}
    
    /**
	 * executes the algorithm
	 */
	protected void executeAlgorithm(){
    	algorithm.executeAlgorithm();
    }
    
    /**
     * Call this method for execution of the report<br> 
     * What this method does: <br>
     * <ul>
     *  <li>validates the configuration - validateConfig() method call</li>
     *  <li>configures the algorithms - configAlgorithms() method call</li>
     *  <li>opens the output - output.open()</li>
     *  <li>runs each algorithm execute() method</li>
     *  <li>closes the output - output.close()</li>
     * </ul>
     */
    @Override public void execute(){
        configAlgorithmSteps();
        executeAlgorithm();
    }
    
	
	/**
	 * 
	 * @return
	 */
	public IAlgorithm getAlgorithm(){
		return algorithm; 
	}	
	
}
