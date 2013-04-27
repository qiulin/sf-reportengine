/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;

/**
 * @author dragos
 *
 */
public class EndReportExitStep implements AlgorithmExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit()
	 */
	public void exit(ReportContext context) {
		context.getOutput().endReport(); 
	}

}
