/**
 * 
 */
package net.sf.reportengine.core.steps;


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
	
	
	protected void executeExit(){
		getReportOutput().endReport(); 
	}
	
	
//	protected ReportOutput extractRepOutputFromParameters(	Map<IOKeys, Object> algoInput, 
//			AlgoContext algoContext){
//		return (ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT); 
//	}
}
