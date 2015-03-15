package net.sf.reportengine.core.steps.intermed;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.ContextKeys;

public class ConfigIntermedReportOutputInitStep extends AbstractInitStep<IntermediateCrosstabOutput> {

	public StepResult<IntermediateCrosstabOutput> init(StepInput stepInput) {
		IntermediateCrosstabOutput intermCrosstabOutput = new IntermediateCrosstabOutput(); 
		//getAlgoContext().set(ContextKeys.LOCAL_REPORT_OUTPUT, intermCrosstabOutput); 
		return new StepResult<IntermediateCrosstabOutput>(ContextKeys.LOCAL_REPORT_OUTPUT, intermCrosstabOutput); 
	}

}
