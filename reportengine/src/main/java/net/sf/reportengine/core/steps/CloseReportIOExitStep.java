/**
 * 
 */
package net.sf.reportengine.core.steps;


/**
 * @author dragos balan
 *
 */
public class CloseReportIOExitStep extends AbstractReportExitStep {

	protected void executeExit(){
		getReportOutput().close(); 
		getReportInput().close(); 
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
