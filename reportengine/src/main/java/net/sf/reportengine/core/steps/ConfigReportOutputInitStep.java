/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class ConfigReportOutputInitStep extends AbstractReportInitStep<ReportOutput> {

	public StepResult<ReportOutput> init(StepInput input) {
		ReportOutput output = (ReportOutput)input.getAlgoInput(IOKeys.REPORT_OUTPUT); 
		//getAlgoContext().set(ContextKeys.LOCAL_REPORT_OUTPUT, output);
		return new StepResult<ReportOutput>(ContextKeys.LOCAL_REPORT_OUTPUT, output); 
	}
}
