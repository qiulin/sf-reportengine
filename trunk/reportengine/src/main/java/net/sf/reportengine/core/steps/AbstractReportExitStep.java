/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.steps.AbstractExitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractReportExitStep extends AbstractExitStep {

	protected ReportInput getReportInput(){
		return (ReportInput)getAlgoContext().get(ContextKeys.LOCAL_REPORT_INPUT); 
	}
	
	protected ReportOutput getReportOutput(){
		return (ReportOutput)getAlgoContext().get(ContextKeys.LOCAL_REPORT_OUTPUT); 
	}
	
}
