/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void init(ReportContext context) {
		context.set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
	}

}
