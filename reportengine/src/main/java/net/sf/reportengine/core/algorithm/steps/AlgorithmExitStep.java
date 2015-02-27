/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.util.IOKeys;

/**
 * <p>
 *      Algorithm exit step interface
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
 */
public interface AlgorithmExitStep {
    
    /**
     * called when finishing the algorithm execution
     */
    public Map<IOKeys, Object> exit(Map<IOKeys,Object> algoInput, AlgoContext context);
    
    /**
     * 
     * @return
     */
//    public Map<IOKeys, Object> getResultsMap(); 
}
