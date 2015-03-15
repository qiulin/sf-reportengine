/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.Map;

import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class DefaultOpenLoopCloseInputOutputAlgo extends OpenCloseOutputAlgo {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.OpenCloseOutputAlgo#buildReportOutput(java.util.Map)
	 */
	@Override
	protected ReportOutput buildReportOutput(Map<IOKeys, Object> inputParams) {
		return (ReportOutput) inputParams.get(IOKeys.REPORT_OUTPUT);
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.OpenLoopCloseInputAlgo#buildReportInput(java.util.Map)
	 */
	@Override
	protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams) {
		return (ReportInput) inputParams.get(IOKeys.REPORT_INPUT);
	}

}
