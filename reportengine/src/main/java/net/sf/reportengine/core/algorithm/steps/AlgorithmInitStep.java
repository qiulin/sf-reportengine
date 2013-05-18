/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.InputKeys;

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
     * @param reportContext     the context of the report
     */
    public void init(Map<InputKeys, Object> algoInput, ReportContext reportContext);
    
    

}