/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class CloseReportIOExitStep implements AlgorithmExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(java.util.Map, net.sf.reportengine.core.algorithm.AlgorithmContext)
	 */
	public void exit(Map<IOKeys, Object> algoInput, AlgorithmContext context) {
		((ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT)).close(); 
		((ReportInput)algoInput.get(IOKeys.REPORT_INPUT)).close(); 
	}
}
