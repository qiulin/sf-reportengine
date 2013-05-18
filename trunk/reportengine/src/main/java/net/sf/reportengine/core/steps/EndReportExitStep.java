/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos
 *
 */
public class EndReportExitStep implements AlgorithmExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit()
	 */
	public void exit(Map<InputKeys, Object> algoInput, ReportContext context) {
		//context.getOutput().endReport(); 
		((ReportOutput)algoInput.get(InputKeys.REPORT_OUTPUT)).endReport(); 
	}

}
