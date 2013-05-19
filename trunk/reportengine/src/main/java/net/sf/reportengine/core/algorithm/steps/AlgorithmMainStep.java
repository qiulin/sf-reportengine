/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.IOKeys;

/**
 * <p>
 *      Interface step in the algorithm. 
 *      The general contract is that :
 *          1. init() method is called after all useful values have been set
 *          2. exit() method is called after all executions have been done
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 */
public interface AlgorithmMainStep extends AlgorithmInitStep, AlgorithmExitStep{
    
    
    /**
     * callback method called for each row
     * @param newRowEvent
     */
    public void execute(NewRowEvent newRowEvent);
    
    
    public Map<IOKeys, Object> getResultsMap(); 
    
}
