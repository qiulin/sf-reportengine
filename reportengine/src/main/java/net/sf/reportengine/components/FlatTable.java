/**
 * 
 */
package net.sf.reportengine.components;

import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_NOT_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_REQUESTED_BY_USER;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.algorithm.OpenLoopCloseInputAlgo;
import net.sf.reportengine.core.steps.ConfigReportOutputInitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.core.steps.neo.EndTableExitStep;
import net.sf.reportengine.core.steps.neo.FlatTableTotalsOutputStep;
import net.sf.reportengine.core.steps.neo.NewColumnHeaderOutputInitStep;
import net.sf.reportengine.core.steps.neo.NewDataRowsOutputStep;
import net.sf.reportengine.core.steps.neo.StartTableInitStep;
import net.sf.reportengine.in.MultipleExternalSortedFilesInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.neo.NewReportOutput;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;
import net.sf.reportengine.util.UserRequestedBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class FlatTable extends AbstractColumnBasedTable {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FlatTable.class);
    
	/**
	 *  the container for two potential algorithms : 
	 *   1. the sorting algorithm 
	 *   2. the reporting algorithmn
	 */
	private AlgorithmContainer reportAlgoContainer = new AlgorithmContainer(); 
	
    
    /**
     * 
     * @param builder
     */
    private FlatTable(Builder builder){
    	this.setShowDataRows(builder.showDataRows); 
    	this.setShowGrandTotal(builder.showGrandTotal.getValue()); 
    	this.setShowTotals(builder.showTotals.getValue());
    	this.setValuesSorted(builder.valuesSorted); 
    	this.setIn(builder.reportInput); 
    	this.setDataColumns(builder.dataColumns); 
    	this.setGroupColumns(builder.groupColumns); 
    }
    
    /**
     * reportAlgo configuration 
     */
    @Override protected void config(){
    	LOGGER.trace("configuring flat report"); 
    	
    	boolean needsProgramaticSorting = !hasValuesSorted() || ReportUtils.isSortingInColumns(getGroupColumns(), getDataColumns()); 
    	LOGGER.info("programatic sorting needed {} ", needsProgramaticSorting); 
    	
    	if(needsProgramaticSorting){
    		reportAlgoContainer.addAlgo(configSortingAlgo()); 
    	}
    	reportAlgoContainer.addAlgo(configReportAlgo(needsProgramaticSorting)); 
    }
    
    /**
     * configuration of the sorting algorithm
     * @return
     */
    private Algorithm configSortingAlgo(){
    	MultiStepAlgo sortingAlgo = new OpenLoopCloseInputAlgo(){
    		@Override protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams){
    			return (ReportInput)inputParams.get(IOKeys.REPORT_INPUT); 
    		}
    	};
    	
    	//init steps
    	sortingAlgo.addInitStep(new ConfigReportOutputInitStep());
    	
    	//main steps
    	sortingAlgo.addMainStep(new ExternalSortPreparationStep()); 
    	
    	//exit steps
    	return sortingAlgo; 
    }
    
    /**
     * configures the report algorithm
     * @return
     */
    private Algorithm configReportAlgo(final boolean hasBeenPreviouslySorted){
    	MultiStepAlgo reportAlgo = new OpenLoopCloseInputAlgo(){
    		@Override 
    		protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams){
    			if(hasBeenPreviouslySorted){
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
    	
//    	if(!needsSorting){
//    		reportAlgo.addInitStep(new ConfigReportIOInitStep()); 
//    	}else{
//    		reportAlgo.addInitStep(new ConfigMultiExternalFilesInputInitStep()); 
//    	}
    	reportAlgo.addInitStep(new ConfigReportOutputInitStep());
    	
    	//no longer needed
    	//reportAlgo.addInitStep(new OpenReportOutputInitStep()); 
    	
    	reportAlgo.addInitStep(new InitReportDataInitStep()); 
    	reportAlgo.addInitStep(new FlatReportExtractTotalsDataInitStep());//TODO: only when report has totals
    	reportAlgo.addInitStep(new StartTableInitStep()); 
    	reportAlgo.addInitStep(new NewColumnHeaderOutputInitStep());
        
    	//then we add the main steps
    	reportAlgo.addMainStep(new GroupLevelDetectorStep());
    	
        if(getShowTotals() || getShowGrandTotal()){
        	reportAlgo.addMainStep(new FlatTableTotalsOutputStep());
        	reportAlgo.addMainStep(new TotalsCalculatorStep());
        }
        
        if(getShowDataRows()){
        	reportAlgo.addMainStep(new NewDataRowsOutputStep());
        }
        
        if(getGroupColumns() != null && getGroupColumns().size() > 0){
        	reportAlgo.addMainStep(new PreviousRowManagerStep());
        }
        
        reportAlgo.addExitStep(new EndTableExitStep()); 
        //reportAlgo.addExitStep(new CloseReportOutputExitStep());
        
        return reportAlgo; 
    }
    
    
    /**
     * validation of configuration
     */
	@Override 
	protected void validate() {
		LOGGER.trace("validating flat report"); 
		//validate non null input and output
		super.validate(); 
        
        List<DataColumn> dataColumns = getDataColumns(); 
        if(dataColumns == null || dataColumns.size() == 0){
        	throw new ConfigValidationException("The report needs at least one data column to work properly"); 
        }
        
        //if totals are needed then check if any GroupCalculators have been added to ALL DataColumns
		if(	(getShowTotals() || getShowGrandTotal()) 
			&& !ReportUtils.atLeastOneDataColumHasCalculators(dataColumns)){
			throw new ConfigValidationException("Please configure a GroupCalculator to at least one DataColumn in order to display totals");
		}
	}


	/**
     * Call this method for execution of the report<br> 
     * What this method does: <br>
     * <ul>
     *  <li>validates the configuration - validateConfig() method call</li>
     *  <li>opens the output - output.open()</li>
     *  <li>runs each reportAlgo execute() method</li>
     *  <li>closes the output - output.close()</li>
     * </ul>
     */
    public void output(NewReportOutput reportOutput){
    	validate(); 
        config();
        
        //preparing the context of the report reportAlgo 
        Map<IOKeys, Object> inputParams = new EnumMap<IOKeys, Object>(IOKeys.class);	
    	inputParams.put(IOKeys.REPORT_TITLE, "report title should not be used"); 
    	inputParams.put(IOKeys.REPORT_INPUT, getIn());
    	inputParams.put(IOKeys.NEW_REPORT_OUTPUT, reportOutput);
    	inputParams.put(IOKeys.DATA_COLS, getDataColumns()); 
    	inputParams.put(IOKeys.GROUP_COLS, getGroupColumns()); 
    	inputParams.put(IOKeys.SHOW_TOTALS, getShowTotals()); 
    	inputParams.put(IOKeys.SHOW_GRAND_TOTAL, getShowGrandTotal()); 
    	
    	Map<IOKeys, Object> result = reportAlgoContainer.execute(inputParams); 
    }
	

	/**
     * 
     * 
     *
     */
    public static class Builder {
    	
    	
    	private UserRequestedBoolean showTotals = UserRequestedBoolean.FALSE_NOT_REQUESTED_BY_USER; 
    	private UserRequestedBoolean showGrandTotal = UserRequestedBoolean.FALSE_NOT_REQUESTED_BY_USER; 
    	
    	private boolean showDataRows = true; 
    	private boolean valuesSorted = true; 
    	
    	private ReportInput reportInput = null; 
    	
    	private List<DataColumn> dataColumns = new ArrayList<DataColumn>(); 
    	private List<GroupColumn> groupColumns = new ArrayList<GroupColumn>();
    	
    	public Builder() {
    		
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
    	
    	/**
    	 * adds the data column to the internal list of data columns 
    	 * and checks if there is any calculator assigned to it. 
    	 * If there is a calculator then the showTotals is recomputed 
    	 * taking into account the groupCalculators and the user's choice
    	 * 
    	 * @param dataCol
    	 */
    	private void internalAddDataColumn(DataColumn dataCol){
    		this.dataColumns.add(dataCol);
    		if(dataCol.getCalculator() != null){
    			
    			//if the user didn't requested to show totals
    			if(!showTotals.isRequestedByUser()){
    				this.showTotals = TRUE_NOT_REQUESTED_BY_USER; 
    			}
    			//else ( the user requested a specific value for the showTotals) 
    			// we keep that value
    			
    			//if the user didn't requested to show the grand total
    			if(!showGrandTotal.isRequestedByUser()){
    				this.showGrandTotal = TRUE_NOT_REQUESTED_BY_USER; 
    			}
    			//else we keep the user value
    		}	
    	}
    	
    	public Builder addDataColumn(DataColumn dataCol){
    		internalAddDataColumn(dataCol); 
    		return this; 
    	}
    	
    	
    	public Builder dataColumns(List<DataColumn> dataCols){
    		for (DataColumn dataColumn : dataCols) {
				internalAddDataColumn(dataColumn); 
			} 
    		return this; 
    	}
    	
    	
    	public Builder groupColumns(List<GroupColumn> groupCols){
    		this.groupColumns = groupCols; 
    		return this; 
    	}
    	
    	public Builder addGroupColumn(GroupColumn groupCol){
    		this.groupColumns.add(groupCol); 
    		return this; 
    	}
    	
    	public FlatTable build(){
    		return new FlatTable(this); 
    	}
    }

}
