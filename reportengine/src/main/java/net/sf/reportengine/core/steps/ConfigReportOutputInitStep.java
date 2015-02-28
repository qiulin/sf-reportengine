/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author balan
 *
 */
public class ConfigReportOutputInitStep extends AbstractReportInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit(java.util.Map)
	 */
	@Override
	protected Map<IOKeys, Object> executeInit(Map<IOKeys, Object> inputParams) {
		getAlgoContext().set(ContextKeys.LOCAL_REPORT_OUTPUT, (ReportOutput)inputParams.get(IOKeys.REPORT_OUTPUT)); 
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP;
	}

}
