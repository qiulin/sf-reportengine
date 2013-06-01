/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class OpenReportInputInitStep extends AbstractInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected void executeInit() {
		((ReportInput)getAlgoInput().get(IOKeys.REPORT_INPUT)).open();
	}
}
