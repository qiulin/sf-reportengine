/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.util.IOKeys;


/**
 * @author dragos balan
 *
 */
public class CloseReportInputExitStep extends AbstractReportExitStep {

//	/* (non-Javadoc)
//	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(java.util.Map, net.sf.reportengine.core.algorithm.AlgoContext)
//	 */
//	public void exit(Map<IOKeys, Object> algoInput, AlgoContext context) {
//		getReportInput(algoInput, context).close(); 
//	}
//	
//	protected ReportInput getReportInput(	Map<IOKeys, Object> algoInput, 
//											AlgoContext algoContext){
//		return (ReportInput)algoInput.get(IOKeys.REPORT_INPUT); 
//	}

	@Override
	protected Map<IOKeys, Object> executeExit(Map<IOKeys, Object> inputParams) {
		getReportInput().close(); 
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
	}
}
