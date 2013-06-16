/**
 * 
 */
package net.sf.reportengine.core.steps;


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
	protected void executeExit() {
		getReportInput().close(); 
		
	}
}
