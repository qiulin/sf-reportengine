/**
 * 
 */
package net.sf.reportengine.core.steps;


/**
 * @author dragos balan
 *
 */
public class OpenReportInputInitStep extends AbstractReportInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected void executeInit() {
		//((ReportInput)getAlgoInput().get(IOKeys.REPORT_INPUT)).open();
		getReportInput().open(); 
	}
}
