/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;

/**
 * @author dragos
 *
 */
public interface MultiStepAlgo extends Algorithm {
	
	/**
     * add an init step to the algorithm
     * @param initStep
     */
    public void addInitStep(AlgorithmInitStep initStep);
    
    /**
     * adds a main step to the algorithm
     * @param step  the main step to be added
     */
    public void addMainStep(AlgorithmMainStep step);
    
    /**
     * 
     * @param exitStep
     */
    public void addExitStep(AlgorithmExitStep exitStep);
    
    
    /**
     * getter for the context
     * @return
     */
    //public AlgoContext getContext();
}
