/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.util.IOKeys;

/**
 * <p>
 *   Definition interface for an algorithm init step
 * </p>
 * @author dragos balan(dragos.balan@gmail.com)
 * @since 0.2
 */
public interface AlgorithmInitStep {
    
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
    
}