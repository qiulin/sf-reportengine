/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import net.sf.reportengine.core.steps.ConfigReportIOInitStep;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class ConfigIntermedIOInitStep extends ConfigReportIOInitStep {
	
	
	@Override protected ReportOutput configReportOutput(){
		return new IntermediateCrosstabOutput();
	}
}
