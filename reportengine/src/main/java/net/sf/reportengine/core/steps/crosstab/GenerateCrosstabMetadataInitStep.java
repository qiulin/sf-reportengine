/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class GenerateCrosstabMetadataInitStep extends AbstractInitStep<CtMetadata> {

	public StepResult<CtMetadata> init(StepInput stepInput) {
		DistinctValuesHolder distinctValuesHolder = 
				(DistinctValuesHolder)stepInput.getAlgoInput(IOKeys.DISTINCT_VALUES_HOLDER); 
		CtMetadata ctMetadata = new CtMetadata(distinctValuesHolder); 
		//getAlgoContext().set(ContextKeys.CROSSTAB_METADATA, ctMetadata); 
		return new StepResult<CtMetadata>(ContextKeys.CROSSTAB_METADATA, ctMetadata); 
	}
}
