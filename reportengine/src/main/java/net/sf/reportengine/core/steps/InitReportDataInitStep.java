/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep extends AbstractInitStep {

	@Override protected void executeInit(Map<IOKeys, Object> inputParams) {
		getAlgoContext().set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
	}

}
