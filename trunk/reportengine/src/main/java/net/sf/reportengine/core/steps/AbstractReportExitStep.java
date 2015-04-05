/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.steps.AbstractExitStep;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportExitStep<T> extends AbstractExitStep<T> {

	protected TableInput getReportInput(StepInput stepInput){
		return (TableInput)stepInput.getContextParam(ContextKeys.LOCAL_REPORT_INPUT); 
	}
	
	protected ReportOutput getReportOutput(StepInput stepInput){
		return (ReportOutput)stepInput.getContextParam(ContextKeys.LOCAL_REPORT_OUTPUT); 
	}
	
}
