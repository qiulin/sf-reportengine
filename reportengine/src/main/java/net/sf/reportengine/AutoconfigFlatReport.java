/**
 * 
 */
package net.sf.reportengine;

import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.OpenReportIOInitStep;
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
     * the algorithm that holds all report steps
     */
    private MultiStepAlgo algorithm = new LoopThroughReportInputAlgo(); 
    
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
    	//AlgoContext context = algorithm.getContext();
    	
    	//preparing the context of the report algorithm 
    	algorithm.addIn(IOKeys.REPORT_INPUT, getIn());
		algorithm.addIn(IOKeys.REPORT_OUTPUT, getOut());
		algorithm.addIn(IOKeys.USER_COLUMN_PREFERENCES, userColumnPrefs); 
		algorithm.addIn(IOKeys.SHOW_TOTALS, getShowTotals()); 
		algorithm.addIn(IOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal()); 
    	
    	//adding steps to the algorithm :
    	//we start with the init steps
		algorithm.addInitStep(new OpenReportIOInitStep()); 
    	algorithm.addInitStep(new InitReportDataInitStep()); 
    	algorithm.addInitStep(new AutodetectConfigInitStep()); 
    	algorithm.addInitStep(new FlatReportExtractTotalsDataInitStep());
    	algorithm.addInitStep(new StartReportInitStep()); 
    	algorithm.addInitStep(new ColumnHeaderOutputInitStep(getTitle()));
        
    	//then we add the main steps
    	algorithm.addMainStep(new GroupLevelDetectorStep());
    	
        algorithm.addMainStep(new AutodetectFlatReportTotalsOutputStep());
        algorithm.addMainStep(new AutodetectTotalsCalculatorStep());
        
        
        if(getShowDataRows()){
        	algorithm.addMainStep(new DataRowsOutputStep());
        }
        
        algorithm.addMainStep(new AutodetectPreviousRowManagerStep());
        
        algorithm.addExitStep(new EndReportExitStep()); 
        algorithm.addExitStep(new CloseReportIOExitStep()); 
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
		algorithm.execute(); 
	}
}
