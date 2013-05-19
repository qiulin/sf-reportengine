/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class EndReportExitStep implements AlgorithmExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit()
	 */
	public void exit(Map<IOKeys, Object> algoInput, AlgorithmContext context) {
		//context.getOutput().endReport(); 
		((ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT)).endReport(); 
	}

}
