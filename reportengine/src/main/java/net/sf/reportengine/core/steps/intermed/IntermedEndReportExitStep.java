/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos
 *
 */
public class IntermedEndReportExitStep extends EndReportExitStep {
	
	@Override protected ReportOutput extractRepOutputFromParameters(	Map<IOKeys, Object> algoInput, 
			AlgoContext algoContext){
		return (ReportOutput)algoContext.get(ContextKeys.INTERMEDIATE_OUTPUT); 
	}
}
