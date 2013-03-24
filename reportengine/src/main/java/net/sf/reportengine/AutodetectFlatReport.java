/**
 * 
 */
package net.sf.reportengine;

import java.util.HashMap;
import java.util.Map;

import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.OneLoopAlgorithm;
import net.sf.reportengine.core.steps.AutodetectColumnsInitStep;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.util.ContextKeys;

import org.apache.log4j.Logger;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.8
 * @see FlatReport
 */
public class AutodetectFlatReport extends AbstractAlgoColumnBasedReport {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = Logger.getLogger(AutodetectFlatReport.class);
	
	/**
     * the user column preferences
     */
    private Map<String, ColumnPreferences> userColumnPrefs = new HashMap<String, ColumnPreferences>(); 
    
    /**
     * default constructor
     */
    public AutodetectFlatReport(){
    	super(new OneLoopAlgorithm()); 
    }
    
    /**
     * validates the report
     */
    @Override protected void validate(){
    	super.validate(); 
    	
    	if(!getIn().supportsMetadata()){
    		throw new ConfigValidationException(""+this.getClass()+" cannot work with non metadata input");
    	}
    	//TODO: add more validations here
    }
    
    /**
     * algorithm configuration 
     */
    @Override protected void config(){
    	Algorithm algorithm = getAlgorithm();
    	ReportContext context = algorithm.getContext();
    	
    	//preparing the context of the report algorithm 
    	algorithm.setIn(getIn());
    	algorithm.setOut(getOut());
    	
    	context.set(ContextKeys.USER_COLUMN_PREFERENCES, userColumnPrefs);
    	context.set(ContextKeys.SHOW_TOTALS, Boolean.valueOf(getShowTotals()));
    	context.set(ContextKeys.SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
    	
    	//adding steps to the algorithm :
    	//we start with the init steps
    	algorithm.addInitStep(new AutodetectColumnsInitStep()); 
    	algorithm.addInitStep(new FlatReportExtractDataInitStep());
    	algorithm.addInitStep(new ColumnHeaderOutputInitStep(getTitle()));
        
    	//then we add the main steps
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupingLevelDetectorStep());
    	
        if(getShowTotals() || getShowGrandTotal()){
        	algorithm.addMainStep(new FlatReportTotalsOutputStep());
        	algorithm.addMainStep(new TotalsCalculatorStep());
        }
        
        if(getShowDataRows()){
        	algorithm.addMainStep(new DataRowsOutputStep());
        }
        
        if(getGroupColumns() != null && getGroupColumns().size() > 0){
        	algorithm.addMainStep(new PreviousRowManagerStep());
        }
    }
	
	/**
     * creates the column preferences object (if it's the case) and retrieves it
     * for later use
     * 
     * @param columnId
     * @return
     */
    public ColumnPreferences forColumn(String columnId){
    	if(!userColumnPrefs.containsKey(columnId)){
    		userColumnPrefs.put(columnId, new ColumnPreferences()); 
    	}    	
    	return userColumnPrefs.get(columnId); 
    }
    
    /**
     * retrieves the map of user column preferences
     * @return
     */
    protected Map<String, ColumnPreferences> getUserColumnPrefs(){
    	return userColumnPrefs; 
    }
}
