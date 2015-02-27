/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
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
	protected Map<IOKeys, Object> executeInit(Map<IOKeys, Object> inputParams) {
		getAlgoContext().set(ContextKeys.LOCAL_REPORT_INPUT, configReportInput(inputParams)); 
		getAlgoContext().set(ContextKeys.LOCAL_REPORT_OUTPUT, configReportOutput(inputParams)); 
		
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
	}
	
	protected ReportInput configReportInput(Map<IOKeys, Object> inputParams){
		return (ReportInput)inputParams.get(IOKeys.REPORT_INPUT); 
	}
	
	protected ReportOutput configReportOutput(Map<IOKeys, Object> inputParams){
		return (ReportOutput)inputParams.get(IOKeys.REPORT_OUTPUT); 
	}
}
