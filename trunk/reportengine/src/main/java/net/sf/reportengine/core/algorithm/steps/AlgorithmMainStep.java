/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;

/**
 * <p>
 *      Interface step in the algorithm. 
 *      The general contract is that :
 *          1. init() method is called after all useful values have been set
 *          2. exit() method is called after all executions have been done
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 */
public interface AlgorithmMainStep<T,U, V> {
    
    
	/**
     * this method is called only once for a report 
     * and represents the construction step where you can define the keys and 
     * values used inside the execute method.
     * <b>Warning: this is the only place where you can add keys to the report context
	 * @param stepInput TODO
     *  
     * @return TODO
     */
    public StepResult<T> init(StepInput stepInput);
	
    /**
     * callback method called for each row
     * @param newRowEvent
     * @param stepInput TODO
     * @return TODO
     */
    public StepResult<U> execute(NewRowEvent newRowEvent, StepInput stepInput);
    
    
    /**
     * called when finishing the algorithm execution
     */
    public StepResult<V> exit(StepInput stepInput);
    
   
    
}
