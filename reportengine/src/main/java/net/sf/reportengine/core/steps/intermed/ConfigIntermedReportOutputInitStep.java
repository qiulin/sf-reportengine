package net.sf.reportengine.core.steps.intermed;

import java.util.Map;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

public class ConfigIntermedReportOutputInitStep extends AbstractInitStep {

	@Override
	protected void executeInit(Map<IOKeys, Object> inputParams) {
		getAlgoContext().set(ContextKeys.LOCAL_REPORT_OUTPUT, new IntermediateCrosstabOutput()); 
	}

}
