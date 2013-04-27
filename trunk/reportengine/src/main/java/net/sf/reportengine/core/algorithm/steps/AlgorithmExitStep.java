/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import net.sf.reportengine.core.algorithm.ReportContext;

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
    public void exit(ReportContext context);
}
