/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(net.sf.reportengine.core.algorithm.AlgorithmContext)
	 */
	public void init(Map<IOKeys, Object> algoInput, AlgorithmContext context) {
		context.set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
	}

}
