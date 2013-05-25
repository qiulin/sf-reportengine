/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class EndReportExitStep implements AlgorithmExitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit()
	 */
	public void exit(Map<IOKeys, Object> algoInput, AlgoContext context) {
		//context.getOutput().endReport(); 
		extractRepOutputFromParameters(algoInput, context).endReport(); 
	}
	
	protected ReportOutput extractRepOutputFromParameters(	Map<IOKeys, Object> algoInput, 
			AlgoContext algoContext){
		return (ReportOutput)algoInput.get(IOKeys.REPORT_OUTPUT); 
	}
}
