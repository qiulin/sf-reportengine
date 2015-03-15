/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;

/**
 * <p>
 *      Algorithm exit step interface
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 */
public interface AlgorithmExitStep<T> {
    
    /**
     * called when finishing the algorithm execution
     * 
     * @param stepInput the input 
     */
    public StepResult<T> exit(StepInput stepInput);
    
}
