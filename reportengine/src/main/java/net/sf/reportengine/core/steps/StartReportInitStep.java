/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class StartReportInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.AlgorithmContext)
	 */
	public void init(Map<IOKeys, Object> algoInput, AlgorithmContext reportContext) {
		ReportProps reportProps = new ReportProps(); 
		((ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT)).startReport(reportProps);
	}

}
