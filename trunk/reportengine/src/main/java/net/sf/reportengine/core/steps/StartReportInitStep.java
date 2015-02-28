/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class StartReportInitStep extends AbstractReportInitStep {
	
	/**
	 * contains a call to output.startReport()
	 */
	@Override protected void executeInit(Map<IOKeys, Object> inputParams) {
		getReportOutput().startReport(new ReportProps());
	}
}
