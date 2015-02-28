/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.util.IOKeys;

/**
 * @author balan
 *
 */
public class CloseReportOutputExitStep extends AbstractReportExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractExitStep#executeExit(java.util.Map)
	 */
	@Override
	protected Map<IOKeys, Object> executeExit(Map<IOKeys, Object> algoInput) {
		getReportOutput().close(); 
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
	}

}
