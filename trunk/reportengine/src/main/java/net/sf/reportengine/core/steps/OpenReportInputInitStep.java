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
	protected void executeInit(Map<IOKeys, Object> inputParams) {
		getReportInput().open(); 
	}
}
