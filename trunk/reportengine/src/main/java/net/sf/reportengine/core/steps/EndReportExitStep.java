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
public class EndReportExitStep extends AbstractReportExitStep {

//	/* (non-Javadoc)
//	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit()
//	 */
//	public void exit(Map<IOKeys, Object> algoInput, AlgoContext context) {
//		//context.getOutput().endReport(); 
//		extractRepOutputFromParameters(algoInput, context).endReport(); 
//	}
	
	
	protected Map<IOKeys, Object> executeExit(Map<IOKeys, Object> inputParams){
		getReportOutput().endReport(); 
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
	}
	
	
//	protected ReportOutput extractRepOutputFromParameters(	Map<IOKeys, Object> algoInput, 
//			AlgoContext algoContext){
//		return (ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT); 
//	}
}
