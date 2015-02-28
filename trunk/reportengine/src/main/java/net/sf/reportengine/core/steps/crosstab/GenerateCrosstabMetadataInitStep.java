/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.steps.AbstractInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.DistinctValuesHolder;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class GenerateCrosstabMetadataInitStep extends AbstractInitStep {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AbstractInitStep#executeInit()
	 */
	@Override
	protected void executeInit(Map<IOKeys, Object> inputParams) {
		DistinctValuesHolder distinctValuesHolder = 
				(DistinctValuesHolder)inputParams.get(IOKeys.DISTINCT_VALUES_HOLDER); 
		
		getAlgoContext().set(ContextKeys.CROSSTAB_METADATA, new CtMetadata(distinctValuesHolder)); 
	}

}
