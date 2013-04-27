/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.out.ReportProps;

/**
 * @author dragos
 *
 */
public class StartReportInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void init(ReportContext reportContext) {
		ReportProps reportProps = new ReportProps(); 
		reportContext.getOutput().startReport(reportProps);
	}

}
