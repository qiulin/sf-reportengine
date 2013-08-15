/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;

/**
 * @author dragos balan
 *
 */
public class ConfigMultiExternalFilesInputForIntermReportInitStep extends
		ConfigMultiExternalFilesInputInitStep {

	@Override protected ReportOutput configReportOutput(){
		return new IntermediateCrosstabOutput();
	}

}
