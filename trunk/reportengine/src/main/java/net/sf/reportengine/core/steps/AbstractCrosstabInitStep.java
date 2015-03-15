/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractCrosstabInitStep<U> extends AbstractReportInitStep<U> {

	protected CtMetadata getCrosstabMetadata(StepInput stepInput){
		return (CtMetadata)stepInput.getContextParam(ContextKeys.CROSSTAB_METADATA);
	}
	
	public CrosstabData getCrosstabData(StepInput stepInput){
		 return (CrosstabData)stepInput.getAlgoInput(IOKeys.CROSSTAB_DATA); 
	}
}
