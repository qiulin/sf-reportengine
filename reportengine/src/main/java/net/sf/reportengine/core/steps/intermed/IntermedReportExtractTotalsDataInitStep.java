/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

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
     * @param algoInput
     * @param algoContext
     * @return
     */
    @Override public List<DataColumn> getDataColumns(){
    	return (List<DataColumn>)getAlgoContext().get(ContextKeys.INTERNAL_DATA_COLS); 
	}
}
