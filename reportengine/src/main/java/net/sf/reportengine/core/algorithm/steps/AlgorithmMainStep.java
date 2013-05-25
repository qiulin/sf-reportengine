/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
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
public interface AlgorithmMainStep {
    
    
	/**
     * this method is called only once for a report 
     * and represents the construction step where you can define the keys and 
     * values used inside the execute method.
     * <b>Warning: this is the only place where you can add keys to the report context
     *  
     * @param algoInput		the input parameters 	
     * @param algoContext   the context of the report
     */
    public void init(Map<IOKeys, Object> algoInput, AlgoContext algoContext);
	
    /**
     * callback method called for each row
     * @param newRowEvent
     */
    public void execute(NewRowEvent newRowEvent);
    
    /**
     * called when finishing the algorithm execution
     */
    public void exit(Map<IOKeys,Object> algoInput, AlgoContext context);
    
    /**
     * 
     */
    public Map<IOKeys, Object> getResultsMap(); 
    
}
