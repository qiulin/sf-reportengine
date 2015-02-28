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
public class OpenReportOutputInitStep extends AbstractReportInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit(java.util.Map)
	 */
	@Override protected void executeInit(Map<IOKeys, Object> inputParams) {
		getReportOutput().open();
	}

}
