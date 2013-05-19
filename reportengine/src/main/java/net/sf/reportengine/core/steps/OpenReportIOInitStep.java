/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class OpenReportIOInitStep implements AlgorithmInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(java.util.Map, net.sf.reportengine.core.algorithm.AlgorithmContext)
	 */
	public void init(Map<IOKeys, Object> algoInput, AlgorithmContext reportContext) {
		((ReportInput)algoInput.get(IOKeys.REPORT_INPUT)).open(); 
		((ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT)).open();
	}
}
