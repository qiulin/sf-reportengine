/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.io.File;
import java.util.Map;

import net.sf.reportengine.core.steps.ConfigReportIOInitStep;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class ConfigCrosstabIOInitStep extends ConfigReportIOInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected ReportInput configReportInput(Map<IOKeys, Object> inputParams) {
		File previousAlgoSerializedOutput = (File)inputParams.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
		return new IntermediateCrosstabReportInput(previousAlgoSerializedOutput); 
		//getAlgoInput().put(IOKeys.REPORT_INPUT, newReportInput); //TODO: one shouldn't be able to modify 
	}
}
