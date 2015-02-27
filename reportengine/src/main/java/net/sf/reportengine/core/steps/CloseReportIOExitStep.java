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
public class CloseReportIOExitStep extends AbstractReportExitStep {
	
	/**
	 * 
	 */
	protected Map<IOKeys, Object> executeExit(Map<IOKeys, Object> algoInput){
		getReportOutput().close(); 
		getReportInput().close(); 
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
	}
	
//	protected ReportOutput getReportOutput(		Map<IOKeys, Object> algoInput, 
//												AlgoContext algoContext){
//		return (ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT); 
//	}
//	
//	protected ReportInput getReportInput(	Map<IOKeys, Object> algoInput, 
//											AlgoContext algoContext){
//		return (ReportInput)algoInput.get(IOKeys.REPORT_INPUT); 
//	}
}
