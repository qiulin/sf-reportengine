/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class IntermedStartReportInitStep extends StartReportInitStep {
	
	@Override public ReportOutput getReportOutput(){
		return (ReportOutput)getAlgoContext().get(ContextKeys.INTERMEDIATE_OUTPUT); 
	}
}
