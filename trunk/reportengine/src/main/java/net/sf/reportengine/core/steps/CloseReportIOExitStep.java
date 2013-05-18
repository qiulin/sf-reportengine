/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos
 *
 */
public class CloseReportIOExitStep implements AlgorithmExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(java.util.Map, net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void exit(Map<InputKeys, Object> algoInput, ReportContext context) {
		((ReportOutput)algoInput.get(InputKeys.REPORT_OUTPUT)).close(); 
		((ReportInput)algoInput.get(InputKeys.REPORT_INPUT)).close(); 
	}
}
