package net.sf.reportengine.core.steps;

import static net.sf.reportengine.util.ContextKeys.DATA_COLUMNS;
import static net.sf.reportengine.util.ContextKeys.SHOW_GRAND_TOTAL;
import static net.sf.reportengine.util.ContextKeys.SHOW_TOTALS;

import java.util.List;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;

import org.apache.log4j.Logger;

/**
 * validates configuration for any flat report
 * 
 * @author dragos balan
 * @since 0.8
 */
public class FlatReportValidationInitStep implements IAlgorithmInitStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = Logger.getLogger(FlatReportValidationInitStep.class);
	
	/**
	 * validates report configuration so that next steps to be able to run 
	 */
	public void init(IReportContext reportContext) {
		if(LOGGER.isInfoEnabled())LOGGER.info("Validating report configuration .."); 
		if(reportContext.getInput() == null) throw new ConfigValidationException("The report has no input");
        if(reportContext.getOutput() == null) throw new ConfigValidationException("The report has no output");
        
        List<IDataColumn> dataColumns = (List<IDataColumn>)reportContext.get(DATA_COLUMNS); 
        if(dataColumns == null || dataColumns.size() == 0){
        	throw new ConfigValidationException("The report needs at least one data column to work properly"); 
        }
        
        //if totals are needed then check if any Calculators have been added to ALL DataColumns
		if(	((Boolean)reportContext.get(SHOW_TOTALS) || (Boolean)reportContext.get(SHOW_GRAND_TOTAL)) 
			&& !atLeastOneDataColumHasCalculators(dataColumns)){
			throw new ConfigValidationException("Please configure a Calculator to at least one DataColumn in order to display totals");
		}
	}
	
	
	/**
     * check if all provided data columns have calculators assigned
     * @return
     */
    private boolean atLeastOneDataColumHasCalculators(List<IDataColumn> dataColumns){
    	boolean atLeastOneCalculator = false; 
    	for(IDataColumn dataColumn: dataColumns){
    		if(dataColumn.getCalculator() != null){
    			atLeastOneCalculator = true;
    			break; 
    		}
    	}
    	return atLeastOneCalculator; 
    }
}
