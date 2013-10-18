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
public abstract class AbstractCrosstabInitStep extends AbstractReportInitStep {

	protected CtMetadata getCrosstabMetadata(){
		return (CtMetadata)getAlgoContext().get(ContextKeys.CROSSTAB_METADATA);
	}
	
	public CrosstabData getCrosstabData(){
		 return (CrosstabData)getAlgoInput().get(IOKeys.CROSSTAB_DATA); 
	 }
}
