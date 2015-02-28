/**
 * 
 */
package net.sf.reportengine;

import static net.sf.reportengine.util.IOKeys.CROSSTAB_DATA;
import static net.sf.reportengine.util.IOKeys.CROSSTAB_HEADER_ROWS;
import static net.sf.reportengine.util.IOKeys.DATA_COLS;
import static net.sf.reportengine.util.IOKeys.GROUP_COLS;
import static net.sf.reportengine.util.IOKeys.REPORT_INPUT;
import static net.sf.reportengine.util.IOKeys.REPORT_OUTPUT;
import static net.sf.reportengine.util.IOKeys.REPORT_TITLE;
import static net.sf.reportengine.util.IOKeys.SHOW_GRAND_TOTAL;
import static net.sf.reportengine.util.IOKeys.SHOW_TOTALS;
import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_NOT_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.FALSE_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_NOT_REQUESTED_BY_USER;
import static net.sf.reportengine.util.UserRequestedBoolean.TRUE_REQUESTED_BY_USER;

import java.io.File;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultCrosstabData;
import net.sf.reportengine.config.DefaultCrosstabHeaderRow;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DefaultGroupColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.DefaultLoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.steps.CloseReportOutputExitStep;
import net.sf.reportengine.core.steps.ConfigReportOutputInitStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.ExternalSortPreparationStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.NewRowComparator;
import net.sf.reportengine.core.steps.OpenReportOutputInitStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.crosstab.ConfigCrosstabColumnsInitStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.GenerateCrosstabMetadataInitStep;
import net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep;
import net.sf.reportengine.core.steps.intermed.ConfigIntermedColsInitStep;
import net.sf.reportengine.core.steps.intermed.ConfigIntermedReportOutputInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedDataRowsOutputStep;
import net.sf.reportengine.core.steps.intermed.IntermedGroupLevelDetectorStep;
import net.sf.reportengine.core.steps.intermed.IntermedPreviousRowManagerStep;
import net.sf.reportengine.core.steps.intermed.IntermedReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedSetResultsExitStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsCalculatorStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsOutputStep;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.in.MultipleExternalSortedFilesInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.ReportUtils;
import net.sf.reportengine.util.UserRequestedBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  This is the main class to be used for Cross tab reports (or Pivot tables).
 *  The layout of pivot tables will look like: <br/>
 *  <table border="1">
 *  	<tr><td>&nbsp;</td><td>&nbsp;</td>						<td colspan="4" align="center"><b>Row header 1</b></td></tr>
 *  	<tr><td><b>Column 1</b></td><td><b>Column 2</b></td>	<td colspan="4" align="center"><b>Row header 2</b></td></tr>
 *  	<tr><td>value 1</td><td>value 2</td>					<td>ct data 11</td><td>ct data 12</td><td>ct data 13</td><td>ct data 14</td></tr>
 *  	<tr><td>value 3</td><td>value 4</td>					<td>ct data 21</td><td>ct data 22</td><td>ct data 23</td><td>ct data 24</td></tr>
 *  	<tr><td>value 5</td><td>value 6</td>					<td>ct data 31</td><td>ct data 32</td><td>ct data 33</td><td>ct data 34</td></tr>
 *  </table><br/>
 *  where the values from Row header 1, Row header 2, etc. are taken from the input 
 *  
 * <p>
 * Each pivot table report needs at least five elements configured: 
 * <ul>
 * 	<li>input</li>
 * 	<li>data columns configuration</li>
 *  <li>row headers configuration</li> 
 *  <li>crosstab data</li>
 * 	<li>output</li>
 * </ul>
 * </p>
 * A simple pivot table report example is: 
 * <pre>
 * {@code
 * CrossTabReport crosstabReport = new CrossTabReport.Builder()
 *		.input(new TextInput("./inputData/expenses.csv", ","))
 *		.output(new Html5Output("./output/expensesPivot.html"))
 *		.addDataColumn(new DefaultDataColumn("Month", 0))
 *		.addHeaderRow(new DefaultCrosstabHeaderRow(1))
 *		.crosstabData(new DefaultCrosstabData(2))
 *		.build();
 *		
 * crosstabReport.execute();
 * }
 * </pre>
 * </p>
 * 
 * @see ReportInput
 * @see ReportOutput
 * @see CrosstabHeaderRow
 * @see DataColumn
 * @see GroupColumn
 * @see CrosstabData
 * 
 * @see DefaultDataColumn
 * @see DefaultGroupColumn
 * @see DefaultCrosstabHeaderRow
 * @see DefaultCrosstabData
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2 
 */
public class CrossTabReport extends AbstractColumnBasedReport{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(CrossTabReport.class);
	
	/**
	 *  the container for potential algorithms : 
	 *  the sorting algorithm , the intermediate algorithm and the reporting algorithm
	 */
	private AlgorithmContainer reportAlgoContainer = new AlgorithmContainer();
	
	/**
	 * the crosstab header rows
	 */
	private List<CrosstabHeaderRow> crosstabHeaderRowsAsList = new ArrayList<CrosstabHeaderRow>(); 
	
	/**
	 * the crosstab data
	 */
	private CrosstabData crosstabData; 
	
	/**
	 * whether or not this report needs programatic sorting
	 */
	private boolean needsProgramaticSorting = false;
	
	/**
	 * constructs a new crosstab report
	 */
	public CrossTabReport(){}
	
	/**
	 * constructs a crosstab report based on the builder values 
	 * @param builder
	 */
	public CrossTabReport(Builder builder){
		this.setShowDataRows(builder.showDataRows); 
    	this.setShowGrandTotal(builder.showGrandTotal.getValue()); 
    	this.setShowTotals(builder.showTotals.getValue());
    	this.setValuesSorted(builder.valuesSorted); 
    	this.setTitle(builder.reportTitle); 
    	this.setIn(builder.reportInput); 
    	this.setOut(builder.reportOutput); 
    	this.setDataColumns(builder.dataColumns); 
    	this.setGroupColumns(builder.groupColumns); 
    	this.setCrosstabData(builder.crosstabData); 
    	this.setHeaderRows(builder.headerRows); 
	}
	
	/**
	 * validates the configuration 
	 */
	@Override protected void validate(){
		LOGGER.trace("validating crosstab configuration ..."); 
		//input/output verification
		super.validate(); 
        
        //crosstab data existence check
        CrosstabData ctData = getCrosstabData(); 
        if(getCrosstabData() == null){
			throw new ConfigValidationException("Crosstab reports need crosstab data configured"); 
		}
		
        //crosstab header validation
        List<CrosstabHeaderRow> ctHeader = getCrosstabHeaderRows(); 
		if(ctHeader == null || ctHeader.size() == 0){
			throw new ConfigValidationException("Crosstab reports need header rows configured");
		}
		
		//the crosstab report needs at least one group column or data column
		List<DataColumn> dataColumns = getDataColumns();
		List<GroupColumn> groupColumns = getGroupColumns();
		if((dataColumns == null || dataColumns.size() == 0) 
			&& (groupColumns == null || groupColumns.size() == 0)){
			throw new ConfigValidationException("Crosstab reports need data and/or group columns configured"); 
		}
		
		//if totals are needed then check if any GroupCalculators have been added to ALL DataColumns
        if(	(getShowTotals() || getShowGrandTotal()) 
			&& ctData.getCalculator() == null){
			throw new ConfigValidationException("Please configure a GroupCalculator to CrosstabData to display totals");
		}
	}
	
	/**
	 * configures the first intermediate report. 
	 * Keep in mind that another method is needed to configure the second report
	 * 
	 * @see #configSecondReport()
	 */
	protected void config() {
		LOGGER.trace("configuring crosstab report ..."); 
		
//		reportAlgoContainer.addIn(REPORT_TITLE, getTitle()); 
//		
//		//setting the input/output
//		reportAlgoContainer.addIn(REPORT_INPUT, getIn());
//		reportAlgoContainer.addIn(REPORT_OUTPUT, getOut());
//		
//		//context keys specific to a flat report
//		reportAlgoContainer.addIn(DATA_COLS, getDataColumns()); 
//		reportAlgoContainer.addIn(GROUP_COLS, getGroupColumns()); 
//		reportAlgoContainer.addIn(CROSSTAB_HEADER_ROWS, crosstabHeaderRowsAsList); 
//		reportAlgoContainer.addIn(CROSSTAB_DATA, crosstabData); 
//		
//		reportAlgoContainer.addIn(SHOW_TOTALS, Boolean.valueOf(getShowTotals())); 
//		reportAlgoContainer.addIn(SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
		
		needsProgramaticSorting = 
					!hasValuesSorted() 
					|| ReportUtils.isSortingInColumns(getGroupColumns(), getDataColumns());
		
		LOGGER.info("programatic sorting needed {} ", needsProgramaticSorting); 
    	//reportAlgoContainer.addIn(IOKeys.HAS_VALUES_ORDERED, !needsProgramaticSorting); 
    	
    	if(needsProgramaticSorting){
    		reportAlgoContainer.addAlgo(configSortingAlgo()); 
    	}
		
		reportAlgoContainer.addAlgo(configFirstReport(needsProgramaticSorting)); 
		reportAlgoContainer.addAlgo(configSecondReport()); 
	}
			
	/**
	 * configures the first algorithm responsible for : 
	 * 	1. discovering the distinct values for the header 
	 *  2. arranging the initial crosstab data into rows for the second report
	 *  3. computing totals on the crosstab data.
	 *  
	 * @return	the intermediate algorithm
	 */
	private Algorithm configFirstReport(final boolean hasBeenPreviouslySorted){
		
		MultiStepAlgo algorithm = new LoopThroughReportInputAlgo(){
			@Override protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams){
				if(hasBeenPreviouslySorted){
					//if the input has been previously sorted
    				//then the sorting algorithm (the previous) has created external sorted files
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
		
		//initial steps 
		algorithm.addInitStep(new ConfigIntermedColsInitStep()); 
		
//		if(!needsProgramaticSorting){
//			algorithm.addInitStep(new ConfigIntermedIOInitStep());
//		}else{
//			algorithm.addInitStep(new ConfigMultiExternalFilesInputForIntermReportInitStep()); 
//		}
		algorithm.addInitStep(new ConfigIntermedReportOutputInitStep());
		
		//algorithm.addInitStep(new OpenReportIOInitStep());
		algorithm.addInitStep(new OpenReportOutputInitStep());
		
		//TODO: only when totals add the step below
		algorithm.addInitStep(new IntermedReportExtractTotalsDataInitStep());
		algorithm.addInitStep(new StartReportInitStep()); 
    	//only for debug
    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Intermediate report"));
        
    	//main steps
    	algorithm.addMainStep(new DistinctValuesDetectorStep());
    	algorithm.addMainStep(new IntermedGroupLevelDetectorStep());
    	
    	//only for debug 
    	//if( getShowTotals() || getShowGrandTotal()){
    	//algorithm.addMainStep(new FlatReportTotalsOutputStep());
		//}
    	algorithm.addMainStep(new IntermedRowMangerStep());
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new IntermedTotalsCalculatorStep());
    	}
    	
    	//only for debug 
    	//algorithm.addMainStep(new DataRowsOutputStep());
    	
    	//if( intermediateGroupCols.size() > 0){
    		algorithm.addMainStep(new IntermedPreviousRowManagerStep());
    	//}
    	
    	algorithm.addExitStep(new EndReportExitStep()); 
    	
    	//algorithm.addExitStep(new CloseReportIOExitStep()); 
    	algorithm.addExitStep(new CloseReportOutputExitStep());
    	
		algorithm.addExitStep(new IntermedSetResultsExitStep()); 
		
		return algorithm; 
	}
	
	/**
	 * the second report is in charge of: 
	 * 1. getting the input from the first report's output
	 * 2. computing the totals on data rows 
	 * 3. outputting into the user defined output
	 */
	private Algorithm configSecondReport(){
		MultiStepAlgo algorithm = new LoopThroughReportInputAlgo(){
			@Override protected ReportInput buildReportInput(Map<IOKeys, Object> inputParams){
				File previousAlgoSerializedOutput = (File)inputParams.get(IOKeys.INTERMEDIATE_OUTPUT_FILE); 
				return new IntermediateCrosstabReportInput(previousAlgoSerializedOutput); 
			}
		};
		
		//adding steps to the algorithm
		algorithm.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algorithm.addInitStep(new ConfigCrosstabColumnsInitStep());
		
		//algorithm.addInitStep(new ConfigCrosstabIOInitStep());
		algorithm.addInitStep(new ConfigReportOutputInitStep());
		
		//algorithm.addInitStep(new OpenReportIOInitStep()); 
		algorithm.addInitStep(new OpenReportOutputInitStep());
		
    	algorithm.addInitStep(new InitReportDataInitStep()); 
    	
    	algorithm.addInitStep(new IntermedReportExtractTotalsDataInitStep());
    	
    	algorithm.addInitStep(new StartReportInitStep()); 
    	algorithm.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	algorithm.addMainStep(new IntermedGroupLevelDetectorStep());
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new IntermedTotalsOutputStep());
    		algorithm.addMainStep(new IntermedTotalsCalculatorStep());
    	}
    	
        algorithm.addMainStep(new IntermedDataRowsOutputStep());
        
        if(getGroupColumns() != null && getGroupColumns().size() > 0){
        	algorithm.addMainStep(new IntermedPreviousRowManagerStep());
        }
        
        algorithm.addExitStep(new EndReportExitStep()); 
        //algorithm.addExitStep(new CloseReportIOExitStep()); 
        algorithm.addExitStep(new CloseReportOutputExitStep()); 
        
        return algorithm; 
	}
	
	
	/**
     * 
     * @return
     */
    private Algorithm configSortingAlgo(){
    	
    	//TODO: improve here (this sorting algo doesn't have multiple steps)
    	MultiStepAlgo sortingAlgo = new DefaultLoopThroughReportInputAlgo();
    	
    	//init steps
    	//sortingAlgo.addInitStep(new ConfigReportIOInitStep()); 
    	//sortingAlgo.addInitStep(new ConfigReportOutputInitStep());
    	
    	//sortingAlgo.addInitStep(new OpenReportInputInitStep()); 
    	
    	//main steps
    	sortingAlgo.addMainStep(new ExternalSortPreparationStep()); 
    	
    	return sortingAlgo; 
    }
	
	/**
	 * getter method for cross table header rows
	 * @return	a list of header rows
	 */
	public List<CrosstabHeaderRow> getCrosstabHeaderRows() {
		return crosstabHeaderRowsAsList; 
	}
	
	/**
	 * setter for the header rows of the crosstab report
	 * @param crosstabHeaderRowsList	
	 */
	public void setHeaderRows(List<CrosstabHeaderRow> crosstabHeaderRowsList) {
		this.crosstabHeaderRowsAsList = crosstabHeaderRowsList; 
	}
	
	/**
	 * adds a new header row at the end of the existing header rows list
	 * @param newHeaderRow
	 */
	public void addHeaderRow(CrosstabHeaderRow newHeaderRow){
		this.crosstabHeaderRowsAsList.add(newHeaderRow);
	}
	
	/**
	 * getter for crosstab data
	 * @return	the crosstab data
	 */
	public CrosstabData getCrosstabData() {
		return crosstabData;
	}

	/**
	 * setter for crosstab data
	 * @param crosstabData
	 */
	public void setCrosstabData(CrosstabData crosstabData) {
		this.crosstabData = crosstabData;
	}
	
	
	/**
     * Call this method for execution of the report<br> 
     * Behind the scenes, this method does the following :<br>
     * <ul>
     *  <li>validates the configuration</li>
     *  <li>configures the report(s)</li>
     *  <li>opens the output - output.open()</li>
     *  <li>runs each algorithm execute() method</li>
     *  <li>closes the output - output.close()</li>
     * </ul>
     */
    @Override public void execute(){
    	//validation of the configuration
    	validate(); 
    	
    	//configuration of the first report
        config();
        
        Map<IOKeys, Object> inputParams = new EnumMap<IOKeys, Object>(IOKeys.class);
        inputParams.put(REPORT_TITLE, getTitle()); 
		
		//setting the input/output
        inputParams.put(REPORT_INPUT, getIn());
        inputParams.put(REPORT_OUTPUT, getOut());
		
		//context keys specific to a flat report
        inputParams.put(DATA_COLS, getDataColumns()); 
        inputParams.put(GROUP_COLS, getGroupColumns()); 
        inputParams.put(CROSSTAB_HEADER_ROWS, crosstabHeaderRowsAsList); 
        inputParams.put(CROSSTAB_DATA, crosstabData); 
		
        inputParams.put(SHOW_TOTALS, Boolean.valueOf(getShowTotals())); 
        inputParams.put(SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
        
        Map<IOKeys, Object> result = reportAlgoContainer.execute(inputParams); 
        LOGGER.debug("crosstab report ended with {}", result); 
    }
    
public static class Builder {
    	
    	private String reportTitle = null; 
    	
    	private UserRequestedBoolean showTotals = FALSE_NOT_REQUESTED_BY_USER; 
    	private UserRequestedBoolean showGrandTotal = FALSE_NOT_REQUESTED_BY_USER; 
    	
    	private boolean showDataRows = true; 
    	private boolean valuesSorted = true; 
    	
    	private ReportInput reportInput = null; 
    	private ReportOutput reportOutput = null;
    	
    	private List<DataColumn> dataColumns = new ArrayList<DataColumn>(); 
    	private List<GroupColumn> groupColumns = new ArrayList<GroupColumn>();
    	private List<CrosstabHeaderRow> headerRows = new ArrayList<CrosstabHeaderRow>(); 
    	private CrosstabData crosstabData = null; 
    	
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
    	
    	public Builder dataColumns(List<DataColumn> dataCols){
    		//if(dataCols != null){
	    		for (DataColumn dataColumn : dataCols) {
					internalAddDataColumn(dataColumn); 
				}
    		//}
    		return this; 
    	}
    	
    	private void internalAddDataColumn(DataColumn dataCol){
    		this.dataColumns.add(dataCol); 
    		if(dataCol.getCalculator() != null){
    			if(!showTotals.isRequestedByUser()){
    				this.showTotals = TRUE_NOT_REQUESTED_BY_USER; 
    			}
    			if(!showGrandTotal.isRequestedByUser()){
    				this.showGrandTotal = TRUE_NOT_REQUESTED_BY_USER; 
    			}
    		}
    	}
    	
    	public Builder addDataColumn(DataColumn dataCol){
    		internalAddDataColumn(dataCol); 
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
    	
    	public Builder headerRows(List<CrosstabHeaderRow> headerRows){
    		this.headerRows = headerRows; 
    		return this; 
    	}
    	
    	public Builder addHeaderRow(CrosstabHeaderRow headerRow){
    		this.headerRows.add(headerRow);
    		return this; 
    	}
    	
    	public Builder crosstabData(CrosstabData data){
    		this.crosstabData = data; 
    		return this; 
    	}
    	
    	public CrossTabReport build(){
    		return new CrossTabReport(this); 
    	}
    }
}