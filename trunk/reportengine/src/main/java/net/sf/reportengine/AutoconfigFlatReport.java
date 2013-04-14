/**
 * 
 */
package net.sf.reportengine;

import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.OneLoopAlgorithm;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectConfigInitStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectFlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectPreviousRowManagerStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectTotalsCalculatorStep;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.util.ContextKeys;

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
    private Algorithm algorithm = new OneLoopAlgorithm(); 
    
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
    	ReportContext context = algorithm.getContext();
    	
    	//preparing the context of the report algorithm 
    	algorithm.setIn(getIn());
    	algorithm.setOut(getOut());
    	
    	context.set(ContextKeys.USER_COLUMN_PREFERENCES, userColumnPrefs);
    	context.set(ContextKeys.SHOW_TOTALS, Boolean.valueOf(getShowTotals()));
    	context.set(ContextKeys.SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
    	
    	//adding steps to the algorithm :
    	//we start with the init steps
    	algorithm.addInitStep(new AutodetectConfigInitStep()); 
    	algorithm.addInitStep(new FlatReportExtractDataInitStep());
    	algorithm.addInitStep(new ColumnHeaderOutputInitStep(getTitle()));
        
    	//then we add the main steps
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupingLevelDetectorStep());
    	
        
        algorithm.addMainStep(new AutodetectFlatReportTotalsOutputStep());
        algorithm.addMainStep(new AutodetectTotalsCalculatorStep());
        
        
        if(getShowDataRows()){
        	algorithm.addMainStep(new DataRowsOutputStep());
        }
        
        algorithm.addMainStep(new AutodetectPreviousRowManagerStep());
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
		algorithm.executeAlgorithm(); 
	}
}
