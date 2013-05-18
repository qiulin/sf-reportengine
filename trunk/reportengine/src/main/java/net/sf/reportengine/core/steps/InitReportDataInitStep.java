/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)
	 */
	public void init(Map<InputKeys, Object> algoInput, ReportContext context) {
		context.set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
	}

}
