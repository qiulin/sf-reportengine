/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep extends AbstractInitStep {

	@Override protected Map<IOKeys, Object> executeInit(Map<IOKeys, Object> inputParams) {
		getAlgoContext().set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
	}

}
