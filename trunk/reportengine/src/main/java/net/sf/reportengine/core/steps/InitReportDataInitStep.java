/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos
 *
 */
public class InitReportDataInitStep extends AbstractInitStep<Integer> {

	public StepResult<Integer> init(StepInput stepInput) {
		//getAlgoContext().set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
		return new StepResult<Integer>(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0)); 
	}
}
