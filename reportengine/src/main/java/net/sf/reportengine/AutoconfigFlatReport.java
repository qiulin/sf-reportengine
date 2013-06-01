/**
 * 
 */
package net.sf.reportengine;

import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.CloseReportInputExitStep;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.DecideInputInitStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.OpenReportIOInitStep;
import net.sf.reportengine.core.steps.OpenReportInputInitStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectFlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectPreviousRowManagerStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectTotalsCalculatorStep;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 * @see FlatReport
 */
public class AutoconfigFlatReport extends AbstractReport {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AutoconfigFlatReport.class);
	
	/**
     * the user column preferences
     */
    private Map<String, ColumnPreferences> userColumnPrefs = new HashMap<String, ColumnPreferences>(); 
    
    /**
	 *  the container for two potential algorithms : 
	 *   1. the sorting algorithm 
	 *   2. the reporting algorithm
	 */
	private AlgorithmContainer reportAlgoContainer = new AlgorithmContainer(); 
    
    /**
     * default constructor
     */
    public AutoconfigFlatReport(){}
    
    /**
     * validates the report
     */
    @Override protected void validate(){
    	super.validate(); 
    	
    	//TODO: add more validations here
    }
    
    /**
     * algorithm configuration 
     */
    @Override protected void config(){
    	LOGGER.trace("configuring the autodetect flat report"); 
    	
    	//preparing the context of the report algorithm 
    	reportAlgoContainer.addIn(IOKeys.REPORT_INPUT, getIn());
    	reportAlgoContainer.addIn(IOKeys.REPORT_OUTPUT, getOut());
    	reportAlgoContainer.addIn(IOKeys.USER_COLUMN_PREFERENCES, userColumnPrefs); 
    	reportAlgoContainer.addIn(IOKeys.SHOW_TOTALS, getShowTotals()); 
    	reportAlgoContainer.addIn(IOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal()); 
    	reportAlgoContainer.addIn(IOKeys.HAS_GROUP_VALUES_ORDERED, hasGroupValuesSorted()); 
    	
    	if(!hasGroupValuesSorted()){
    		reportAlgoContainer.addAlgo(configSortingAlgo()); 
    	}
    	
    	reportAlgoContainer.addAlgo(configReportAlgo()); 
    }
    
    /**
     * 
     * @return
     */
    private Algorithm configSortingAlgo(){
    	MultiStepAlgo sortingAlgo = new LoopThroughReportInputAlgo(); 
    	
    	sortingAlgo.addInitStep(new OpenReportInputInitStep());
    	sortingAlgo.addInitStep(new AutodetectConfigInitStep()); 
    	
    	sortingAlgo.addMainStep(new ExternalSortPreparationStep()); 
    	
    	sortingAlgo.addExitStep(new CloseReportInputExitStep()); 
    	return sortingAlgo; 
    }
    
    /**
     * 
     * @return
     */
    private Algorithm configReportAlgo(){
    	MultiStepAlgo algorithm = new LoopThroughReportInputAlgo(); 
    	//the init steps
    	algorithm.addInitStep(new DecideInputInitStep()); 
		algorithm.addInitStep(new OpenReportIOInitStep()); 
    	algorithm.addInitStep(new InitReportDataInitStep()); 
    	if(hasGroupValuesSorted()){
    		algorithm.addInitStep(new AutodetectConfigInitStep()); 
    	}
    	algorithm.addInitStep(new FlatReportExtractTotalsDataInitStep());
    	algorithm.addInitStep(new StartReportInitStep()); 
    	algorithm.addInitStep(new ColumnHeaderOutputInitStep(getTitle()));
        
    	//the main steps
    	algorithm.addMainStep(new GroupLevelDetectorStep());
    	algorithm.addMainStep(new AutodetectFlatReportTotalsOutputStep());
        algorithm.addMainStep(new AutodetectTotalsCalculatorStep());
        
        if(getShowDataRows()){
        	algorithm.addMainStep(new DataRowsOutputStep());
        }
        algorithm.addMainStep(new AutodetectPreviousRowManagerStep());
        
        //exit steps
        algorithm.addExitStep(new EndReportExitStep()); 
        algorithm.addExitStep(new CloseReportIOExitStep()); 
    	
    	return algorithm; 
    }
	
	/**
     * creates the column preferences object (if it's the case) and retrieves it
     * for later use. 
     * 
     * Please note that the internal storage of columnId is case insensitive 
     * 
     * @param columnId
     * @return
     */
    public ColumnPreferences forColumn(String columnId){
    	ColumnPreferences result = null; 
    	if(columnId != null){
    		String colIdUpperCase = columnId.toUpperCase(); 
    		if(!userColumnPrefs.containsKey(colIdUpperCase)){
    			userColumnPrefs.put(colIdUpperCase, new ColumnPreferences()); 
    		}
    		result = userColumnPrefs.get(colIdUpperCase); 
    	}else{
    		LOGGER.error("null passed as argument to AutoconfigFlatReport.forColumn method"); 
    	}
    	
    	return result; 
    }
    
    /**
     * retrieves the map of user column preferences
     * @return
     */
    protected Map<String, ColumnPreferences> getUserColumnPrefs(){
    	return userColumnPrefs; 
    }
    
    /**
     * the main execute method of this report
     */
	@Override public void execute() {
		validate(); 
		config(); 
		reportAlgoContainer.execute(); 
	}
}
