/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep extends AbstractInitStep {

	@Override protected void executeInit() {
		getAlgoContext().set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
	}

}
