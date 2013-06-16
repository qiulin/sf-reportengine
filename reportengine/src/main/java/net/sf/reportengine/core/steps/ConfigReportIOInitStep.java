/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;


/**
 * @author dragos balan
 *
 */
public class ConfigReportIOInitStep extends AbstractReportInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected void executeInit() {
		getAlgoContext().set(ContextKeys.LOCAL_REPORT_INPUT, configReportInput()); 
		getAlgoContext().set(ContextKeys.LOCAL_REPORT_OUTPUT, configReportOutput()); 
	}
	
	protected ReportInput configReportInput(){
		return (ReportInput)getAlgoInput().get(IOKeys.REPORT_INPUT); 
	}
	
	protected ReportOutput configReportOutput(){
		return (ReportOutput)getAlgoInput().get(IOKeys.REPORT_OUTPUT); 
	}
}
