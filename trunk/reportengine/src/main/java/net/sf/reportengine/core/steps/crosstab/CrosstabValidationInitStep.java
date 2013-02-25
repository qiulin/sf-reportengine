/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import static net.sf.reportengine.util.ContextKeys.CROSSTAB_DATA;
import static net.sf.reportengine.util.ContextKeys.CROSSTAB_HEADER_ROWS;
import static net.sf.reportengine.util.ContextKeys.DATA_COLUMNS;
import static net.sf.reportengine.util.ContextKeys.GROUPING_COLUMNS;
import static net.sf.reportengine.util.ContextKeys.SHOW_GRAND_TOTAL;
import static net.sf.reportengine.util.ContextKeys.SHOW_TOTALS;

import java.util.List;

import net.sf.reportengine.config.ICrosstabData;
import net.sf.reportengine.config.ICrosstabHeaderRow;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;

import org.apache.log4j.Logger;

/**
 * validates the configuration for any crosstab report
 * 
 * @author dragos balan
 * @since 0.8
 */
public class CrosstabValidationInitStep implements IAlgorithmInitStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = Logger.getLogger(CrosstabValidationInitStep.class);
	
	/**
	 * validates report configuration so that next steps to be able to run 
	 */
	public void init(IReportContext reportContext) {
		if(LOGGER.isInfoEnabled())LOGGER.info("Validating crosstab report configuration ..");
		
		//input/output verification
		if(reportContext.getInput() == null) throw new ConfigValidationException("The report has no input");
        if(reportContext.getOutput() == null) throw new ConfigValidationException("The report has no output");
        
        //crosstab data existence check
        ICrosstabData ctData = (ICrosstabData)reportContext.get(CROSSTAB_DATA);
        if(ctData == null){
			throw new ConfigValidationException("Crosstab reports need crosstab data configured"); 
		}
		
        //crosstab header validation
        List<ICrosstabHeaderRow> ctHeader = (List<ICrosstabHeaderRow>)reportContext.get(CROSSTAB_HEADER_ROWS); 
		if(ctHeader == null || ctHeader.size() == 0){
			throw new ConfigValidationException("Crosstab reports need header rows configured");
		}
		
		//the crosstab report needs at least one group column or data column
		List<IDataColumn> dataColumns = (List<IDataColumn>)reportContext.get(DATA_COLUMNS);
		List<IGroupColumn> groupColumns = (List<IGroupColumn>)reportContext.get(GROUPING_COLUMNS);
		if((dataColumns == null || dataColumns.size() == 0) 
			&& (groupColumns == null || groupColumns.size() == 0)){
			throw new ConfigValidationException("Crosstab reports need data and/or group columns configured"); 
		}
		
		//if totals are needed then check if any Calculators have been added to ALL DataColumns
        if(	((Boolean)reportContext.get(SHOW_TOTALS) || (Boolean)reportContext.get(SHOW_GRAND_TOTAL)) 
			&& ctData.getCalculator() == null){
			throw new ConfigValidationException("Please configure a Calculator to CrosstabData to display totals");
		}
	}
}
