/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos
 *
 */
public class StartReportInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void init(Map<InputKeys, Object> algoInput, ReportContext reportContext) {
		ReportProps reportProps = new ReportProps(); 
		((ReportOutput)algoInput.get(InputKeys.REPORT_OUTPUT)).startReport(reportProps);
	}

}
