/**
 * 
 */
package net.sf.reportengine.core.steps;


/**
 * @author dragos balan
 *
 */
public class OpenReportIOInitStep extends AbstractReportInitStep {

	@Override protected void executeInit() {
		getReportInput().open(); 
		getReportOutput().open();
	}
}
