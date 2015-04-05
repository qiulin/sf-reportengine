/**
 * 
 */
package net.sf.reportengine.core.steps;



/**
 * @author dragos balan
 * @deprecated the flat table no longer needs a end report exit step
 */
public class EndReportExitStep extends AbstractReportExitStep<String> {

	
	public StepResult<String> exit(StepInput stepInput){
		getReportOutput(stepInput).endReport(); 
		return StepResult.NO_RESULT; 
	}
	
	
//	protected ReportOutput extractRepOutputFromParameters(	Map<IOKeys, Object> algoInput, 
//			AlgoContext algoContext){
//		return (ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT); 
//	}
}
