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
public class OpenReportInputInitStep extends AbstractReportInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected Map<IOKeys, Object> executeInit(Map<IOKeys, Object> inputParams) {
		//((ReportInput)getAlgoInput().get(IOKeys.REPORT_INPUT)).open();
		getReportInput().open(); 
		return Algorithm.EMPTY_READ_ONLY_PARAMS_MAP; 
	}
}
