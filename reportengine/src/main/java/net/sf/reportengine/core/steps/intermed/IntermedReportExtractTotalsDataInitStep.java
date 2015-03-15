/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan
 * @since 0.9
 */
public class IntermedReportExtractTotalsDataInitStep extends FlatReportExtractTotalsDataInitStep {
	
	/**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumnsLength()}
     * 
     * @param stepInput
     * @return
     */
    @Override public List<DataColumn> getDataColumns(StepInput stepInput){
    	return (List<DataColumn>)stepInput.getContextParam(ContextKeys.INTERNAL_DATA_COLS); 
	}
}
