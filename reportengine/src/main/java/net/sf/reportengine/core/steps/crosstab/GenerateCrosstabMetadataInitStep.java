/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

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
	protected void executeInit() {
		DistinctValuesHolder distinctValuesHolder = 
				(DistinctValuesHolder)getAlgoInput().get(IOKeys.DISTINCT_VALUES_HOLDER); 
		
		getAlgoContext().set(ContextKeys.CROSSTAB_METADATA, new CtMetadata(distinctValuesHolder)); 
	}

}
