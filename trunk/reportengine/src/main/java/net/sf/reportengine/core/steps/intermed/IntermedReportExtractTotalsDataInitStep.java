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
	 * 
	 */
	private IOKeys dataColumnIOKey = null;
	
	/**
	 * 
	 */
	private ContextKeys dataColumContextKey = null; 
	
	/**
	 * 
	 * @param dataColumnKey
	 */
	public IntermedReportExtractTotalsDataInitStep(IOKeys dataColumnKey){
		this.dataColumnIOKey = dataColumnKey; 
	}
	
	/**
	 * 
	 * @param dataColumnKey
	 */
	public IntermedReportExtractTotalsDataInitStep(ContextKeys dataColumnKey){
		this.dataColumContextKey = dataColumnKey; 
	}
	
	/**
     * ATTENTION : changing the implementation of this method will have effect on the 
     * following methods: 
     * {@link #getDataColumns()}
     * {@link #getDataColumnsLength()}
     * 
     * @param algoInput
     * @param algoContext
     * @return
     */
    @Override protected List<DataColumn> extractDataColsFromParameters(	Map<IOKeys, Object> algoInput, 
    																	AlgoContext algoContext){
    	if(dataColumnIOKey != null){
    		//return (List<DataColumn>)algoInput.get(IOKeys.DATA_COLS); 
    		return (List<DataColumn>)algoInput.get(dataColumnIOKey);
    	}else{
    		return (List<DataColumn>)algoContext.get(dataColumContextKey); 
    	}
	}
}
