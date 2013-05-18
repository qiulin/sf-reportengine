/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.util.InputKeys;

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
    public void exit(Map<InputKeys,Object> algoInput, ReportContext context);
}
