/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractCrosstabInitStep extends AbstractReportInitStep {

	protected CtMetadata getCrosstabMetadata(){
		return (CtMetadata)getAlgoContext().get(ContextKeys.CROSSTAB_METADATA);
	}
	
	
}
