/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos
 *
 */
public class OpenReportIOInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(java.util.Map, net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void init(Map<InputKeys, Object> algoInput, ReportContext reportContext) {
		((ReportInput)algoInput.get(InputKeys.REPORT_INPUT)).open(); 
		((ReportOutput)algoInput.get(InputKeys.REPORT_OUTPUT)).open();
	}
}
