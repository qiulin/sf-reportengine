/**
 * 
 */
package net.sf.reportengine;

import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_NOT_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_REQUESTED_BY_USER;

import java.io.File;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.ConfigDetectorAlgorithm;
import net.sf.reportengine.core.algorithm.DefaultLoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.algorithm.OpenLoopCloseInputAlgo;
import net.sf.reportengine.core.steps.CloseReportOutputExitStep;
import net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.ConfigReportOutputInitStep;
import net.sf.reportengine.core.steps.DataRowsOutputStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.core.steps.OpenReportOutputInitStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectFlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectPreviousRowManagerStep;
import net.sf.reportengine.core.steps.autodetect.AutodetectTotalsCalculatorStep;
import net.sf.reportengine.in.ColumnPreferences;
import net.sf.reportengine.in.MultipleExternalSortedFilesInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;
import net.sf.reportengine.util.UserRequestedBoolean;

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
     * 
     * @param builder
     */
    private AutoconfigFlatReport(Builder builder){
    	this.setShowDataRows(builder.showDataRows); 
    	this.setShowTotals(builder.showTotals.getValue()); 
    	this.setShowGrandTotal(builder.showGrandTotal.getValue()); 
    	this.setValuesSorted(builder.valuesSorted); 
    	this.setTitle(builder.reportTitle); 
    	this.setIn(builder.reportInput); 
    	this.setOut(builder.reportOutput);
    }
    
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
    	
    	boolean needsProgramaticSorting = !hasValuesSorted() || ReportUtils.isSortingInPreferences(userColumnPrefs); 
    	reportAlgoContainer.addAlgo(new ConfigDetectorAlgorithm());
    	    	
    	if(needsProgramaticSorting){
    		reportAlgoContainer.addAlgo(configSortingAlgo(getIn())); 
    	}
    	
    	reportAlgoContainer.addAlgo(configReportAlgo(needsProgramaticSorting)); 
    }
    
    /**
     * 
     * @return
     */
    private Algorithm configSortingAlgo(ReportInput sortingAlgoReportInput){
    	//TODO: improve here (this sorting algo doesn't have multiple steps)
    	MultiStepAlgo sortingAlgo = new DefaultLoopThroughReportInputAlgo(); 
    	
    	//sortingAlgo.addInitStep(new ConfigReportIOInitStep()); 
    	//sortingAlgo.addInitStep(new ConfigReportOutputInitStep());
    	
    	//sortingAlgo.addInitStep(new OpenReportInputInitStep());
    	//sortingAlgo.addInitStep(new AutodetectConfigInitStep()); 
    	
    	sortingAlgo.addMainStep(new ExternalSortPreparationStep()); 
    	
    	//sortingAlgo.addExitStep(new CloseReportInputExitStep()); 
    	return sortingAlgo; 
    }
    
    /**
     * 
     * @return
     */
    private Algorithm configReportAlgo(final boolean inputHasBeenPreviouslySorted){
    	MultiStepAlgo algorithm = new OpenLoopCloseInputAlgo(){
    		@Override protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams){
    			if(inputHasBeenPreviouslySorted){
    				//if the input has been previously sorted
    				//then the sorting algorithm ( the previous) has created external sorted files
    				//which will serve as input from this point on
    				return new MultipleExternalSortedFilesInput(
							(List<File>)inputParams.get(IOKeys.SORTED_FILES), 
							new NewRowComparator(
									(List<GroupColumn>)inputParams.get(IOKeys.GROUP_COLS), 
									(List<DataColumn>)inputParams.get(IOKeys.DATA_COLS)));
    			}else{
    				return (ReportInput)inputParams.get(IOKeys.REPORT_INPUT);
    			}
    		}
    	};
    	
    	//the initial steps
//    	if(!inputHasBeenPreviouslySorted){
//    		algorithm.addInitStep(new ConfigReportIOInitStep()); 
//    	}else{
//    		algorithm.addInitStep(new ConfigMultiExternalFilesInputInitStep()); 
//    	}
    	algorithm.addInitStep(new ConfigReportOutputInitStep());
    	
		//algorithm.addInitStep(new OpenReportIOInitStep()); 
    	algorithm.addInitStep(new OpenReportOutputInitStep());
    	
    	algorithm.addInitStep(new InitReportDataInitStep()); 
//    	if(!inputHasBeenPreviouslySorted){
//    		//if the report has values already sorted 
//    		//then the configuration has not been detected using 
//    		//the previous algorithm
//    		algorithm.addInitStep(new AutodetectConfigInitStep()); 
//    	}
    	algorithm.addInitStep(new FlatReportExtractTotalsDataInitStep());
    	algorithm.addInitStep(new StartReportInitStep()); 
    	algorithm.addInitStep(new ColumnHeaderOutputInitStep());
        
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
        
        //algorithm.addExitStep(new CloseReportIOExitStep()); 
    	algorithm.addExitStep(new CloseReportOutputExitStep());
    	
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
		
		Map<IOKeys, Object> inputParams = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	inputParams.put(IOKeys.REPORT_TITLE, getTitle()); 
    	inputParams.put(IOKeys.REPORT_INPUT, getIn());
    	inputParams.put(IOKeys.REPORT_OUTPUT, getOut());
    	inputParams.put(IOKeys.USER_COLUMN_PREFERENCES, userColumnPrefs); 
    	inputParams.put(IOKeys.SHOW_TOTALS, getShowTotals()); 
    	inputParams.put(IOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal()); 
		
    	Map<IOKeys, Object> result = reportAlgoContainer.execute(inputParams); 
    	LOGGER.debug("algorithm ended with with the following result {}", result);
	}
	
	
	public static class Builder {
    	
    	private String reportTitle = null; 
    	
    	private UserRequestedBoolean showTotals = FALSE_NOT_REQUESTED_BY_USER; 
    	private UserRequestedBoolean showGrandTotal = FALSE_NOT_REQUESTED_BY_USER; 
    	
    	private boolean showDataRows = true; 
    	private boolean valuesSorted = true; 
    	
    	private ReportInput reportInput = null; 
    	private ReportOutput reportOutput = null;
    	
    	public Builder() {
    		
    	}
    	
    	public Builder title(String title){
    		this.reportTitle = title; 
    		return this; 
    	}
    	
    	public Builder showTotals(boolean show){
    		this.showTotals = show ? TRUE_REQUESTED_BY_USER : FALSE_REQUESTED_BY_USER; 
    		return this; 
    	}
    	
    	public Builder showTotals(){
    		return showTotals(true); 
    	}
    	
    	public Builder showGrandTotal(boolean show){
    		this.showGrandTotal = show ? TRUE_REQUESTED_BY_USER : FALSE_REQUESTED_BY_USER; 
    		return this; 
    	}
    	
    	public Builder showGrandTotal(){
    		return showGrandTotal(true); 
    	}
    	
    	public Builder showDataRows(boolean show){
    		this.showDataRows = show; 
    		return this; 
    	}
    	
    	public Builder showDataRows(){
    		return showDataRows(true); 
    	}
    	
    	public Builder sortValues(){
    		this.valuesSorted = false; 
    		return this; 
    	}
    	
    	public Builder input(ReportInput input){
    		this.reportInput = input; 
    		return this; 
    	}
    	
    	public Builder output(ReportOutput output){
    		this.reportOutput = output; 
    		return this; 
    	}
    	
    	public AutoconfigFlatReport build(){
    		return new AutoconfigFlatReport(this); 
    	}
    }
}
