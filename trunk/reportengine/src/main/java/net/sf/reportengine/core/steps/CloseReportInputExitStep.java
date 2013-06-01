/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class CloseReportInputExitStep implements AlgorithmExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(java.util.Map, net.sf.reportengine.core.algorithm.AlgoContext)
	 */
	public void exit(Map<IOKeys, Object> algoInput, AlgoContext context) {
		getReportInput(algoInput, context).close(); 
	}
	
	protected ReportInput getReportInput(	Map<IOKeys, Object> algoInput, 
											AlgoContext algoContext){
		return (ReportInput)algoInput.get(IOKeys.REPORT_INPUT); 
	}

}
