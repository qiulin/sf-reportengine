/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedCloseReportIOExitStep extends CloseReportIOExitStep{
	
	
	@Override protected ReportOutput getReportOutput(	Map<IOKeys, Object> algoInput, 
																		AlgoContext algoContext){
		return (ReportOutput)algoContext.get(ContextKeys.INTERMEDIATE_OUTPUT); 
	}

	
	
}
