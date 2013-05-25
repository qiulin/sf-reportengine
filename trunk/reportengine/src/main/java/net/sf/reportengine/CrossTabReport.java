/**
 * 
 */
package net.sf.reportengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.Algorithm;
import net.sf.reportengine.core.algorithm.AlgorithmContainer;
import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.InitReportDataInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.crosstab.ConfigAndOpenCrosstabIOInitStep;
import net.sf.reportengine.core.steps.crosstab.ConfigCrosstabColumnsInitStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabHeaderOutputInitStep;
import net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.GenerateCrosstabMetadataInitStep;
import net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep;
import net.sf.reportengine.core.steps.intermed.ConfigAndOpenIntermedIOInitStep;
import net.sf.reportengine.core.steps.intermed.ConfigIntermedColsInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedCloseReportIOExitStep;
import net.sf.reportengine.core.steps.intermed.IntermedDataRowsOutputStep;
import net.sf.reportengine.core.steps.intermed.IntermedEndReportExitStep;
import net.sf.reportengine.core.steps.intermed.IntermedGroupLevelDetectorStep;
import net.sf.reportengine.core.steps.intermed.IntermedPreviousRowManagerStep;
import net.sf.reportengine.core.steps.intermed.IntermedReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedStartReportInitStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsCalculatorStep;
import net.sf.reportengine.core.steps.intermed.IntermedTotalsOutputStep;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  This is the main class to be used for Cross tab reports (or Pivot tables).
 *  The layout of pivot tables will look like: <br/>
 *  <table>
 *  	<tr><td>&nbsp;</td><td>&nbsp;</td>						<td colspan="4" align="center"><b>Row header 1</b></td></tr>
 *  	<tr><td><b>Column 1</b></td><td><b>Column 2</b></td>	<td colspan="4" align="center"><b>Row header 2</b></td></tr>
 *  	<tr><td>value 1</td><td>value 2</td>					<td>ct data 11</td><td>ct data 12</td><td>ct data 13</td><td>ct data 14</td></tr>
 *  	<tr><td>value 3</td><td>value 4</td>					<td>ct data 21</td><td>ct data 22</td><td>ct data 23</td><td>ct data 24</td></tr>
 *  	<tr><td>value 5</td><td>value 6</td>					<td>ct data 31</td><td>ct data 32</td><td>ct data 33</td><td>ct data 34</td></tr>
 *  </table><br/>
 * Each pivot table report needs five elements configured: 
 * <ul>
 * 	<li>input</li>
 * 	<li>column config</li>
 *  <li>row headers config</li> 
 *  <li>crosstab data</li>
 * 	<li>output</li>
 * </ul>
 * 
 * A simple pivot table report example is: 
 * <pre>
 * {@code
 *  CrossTabReport report = new CrossTabReport(); 
 *	
 *  //set up the input/output			
 *  ReportInput in = new TextInput(new FileInputStream("expenses.csv"));
 *  report.setIn(input); 
 *			
 *  ReportOutput output = new HtmlOutput(new FileOutputStream("xpenses.html")); 
 *  report.setOut(output);
 *			
 *  //set up data column
 *  report.addDataColumn(new DefaultDataColumn("Month", 0)); 
 *			
 *  //set up the header rows (from the second column)
 *  report.addHeaderRow(new DefaultCrosstabHeaderRow(1));
 *			
 *  //set up the crosstab data
 *  report.setCrosstabData(new DefaultCrosstabData(2));
 *			
 *  //report execution
 *  report.execute();
 *
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
	 * the intermediate report is in charge of : 
	 * 	1. discovering the distinct values for the header 
	 *  2. arranging the initial crosstab data into rows for the second report
	 *  3. computing totals on the crosstab data.
	 */
	//private IntermediateCrosstabReport firstReport;
	
	/**
	 * the second report is in charge of: 
	 * 1. getting the input from the first report's output
	 * 2. computing the totals on data rows 
	 * 3. outputting into the user defined output
	 */
	private SecondCrosstabProcessorReport secondReport; 
	
	/**
	 * the crosstab header rows
	 */
	private List<CrosstabHeaderRow> crosstabHeaderRowsAsList = new ArrayList<CrosstabHeaderRow>(); 
	
	/**
	 * the crossta data
	 */
	private CrosstabData crosstabData; 
	
	/**
	 * constructs a new crosstab report
	 */
	public CrossTabReport(){
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
		
		//if totals are needed then check if any Calculators have been added to ALL DataColumns
        if(	(getShowTotals() || getShowGrandTotal()) 
			&& ctData.getCalculator() == null){
			throw new ConfigValidationException("Please configure a Calculator to CrosstabData to display totals");
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
		
		//IntermediateCrosstabOutput firstReportOutput = new IntermediateCrosstabOutput(); 		
		//firstReport = new IntermediateCrosstabReport(groupColsLength, dataColsLength); 
		//firstReport.setIn(getIn()); 
		//firstReport.setOut(firstReportOutput); 
		//firstReport.setGroupColumns(groupCols); 
		//firstReport.setDataColumns(dataColsList); 
		//firstReport.setCrosstabHeaderRows(getCrosstabHeaderRows()); 
		//firstReport.setCrosstabData(getCrosstabData()); 
		//firstReport.setShowDataRows(true); 
		//firstReport.setShowTotals(getShowTotals());
		//firstReport.setShowGrandTotal(getShowGrandTotal()); 
		
		//new implementation start
		//setting the input/output
		reportAlgoContainer.addIn(IOKeys.REPORT_INPUT, getIn());
		reportAlgoContainer.addIn(IOKeys.REPORT_OUTPUT, getOut());
		
		//context keys specific to a flat report
		//reportAlgoContainer.addIn(IOKeys.DATA_COLS, intermediateDataCols); 
		//reportAlgoContainer.addIn(IOKeys.GROUP_COLS, intermediateGroupCols); 
		reportAlgoContainer.addIn(IOKeys.DATA_COLS, getDataColumns()); 
		reportAlgoContainer.addIn(IOKeys.GROUP_COLS, getGroupColumns()); 
		reportAlgoContainer.addIn(IOKeys.CROSSTAB_HEADER_ROWS, crosstabHeaderRowsAsList); 
		reportAlgoContainer.addIn(IOKeys.CROSSTAB_DATA, crosstabData); 
		
		reportAlgoContainer.addIn(IOKeys.SHOW_TOTALS, Boolean.valueOf(getShowTotals())); 
		reportAlgoContainer.addIn(IOKeys.SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
		
		//reportAlgoContainer.addIn(IOKeys.ORIGINAL_CT_DATA_COLS_COUNT, originalDataColsCount); 
		//reportAlgoContainer.addIn(IOKeys.ORIGINAL_CT_GROUP_COLS_COUNT, originalGroupColsCount); 
		
		reportAlgoContainer.addAlgo(configFirstReport()); 
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
	private Algorithm configFirstReport(){
		
		MultiStepAlgo algorithm = new LoopThroughReportInputAlgo(); 
		//initial steps 
		algorithm.addInitStep(new ConfigIntermedColsInitStep()); 
		algorithm.addInitStep(new ConfigAndOpenIntermedIOInitStep()); // instead of new OpenReportIOInitStep()
		algorithm.addInitStep(new IntermedReportExtractTotalsDataInitStep(IOKeys.DATA_COLS));//TODO: only when totals
		algorithm.addInitStep(new IntermedStartReportInitStep()); 
    	//only for debug
    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Intermediate report"));
        
    	//main steps
    	algorithm.addMainStep(new DistinctValuesDetectorStep());
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new IntermedGroupLevelDetectorStep(ContextKeys.INTERNAL_GROUP_COLS));
    	
    	//only for debug 
    	//if( getShowTotals() || getShowGrandTotal()){
    	//algorithm.addMainStep(new FlatReportTotalsOutputStep());
		//}
    	algorithm.addMainStep(new IntermedRowMangerStep());
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new IntermedTotalsCalculatorStep());
    	}
    	
    	//only for debug algorithm.addMainStep(new DataRowsOutputStep());
    	
    	//if( intermediateGroupCols.size() > 0){
    		algorithm.addMainStep(new IntermedPreviousRowManagerStep());
    	//}
    	
    	algorithm.addExitStep(new IntermedEndReportExitStep()); 
    	algorithm.addExitStep(new IntermedCloseReportIOExitStep()); 
		
		return algorithm; 
	}
	
	private Algorithm configSecondReport(){
		MultiStepAlgo algorithm = new LoopThroughReportInputAlgo(); 
		
		//adding steps to the algorithm
		algorithm.addInitStep(new GenerateCrosstabMetadataInitStep()); 
		algorithm.addInitStep(new ConfigCrosstabColumnsInitStep());
		algorithm.addInitStep(new ConfigAndOpenCrosstabIOInitStep()); //new OpenReportIOInitStep()
    	algorithm.addInitStep(new InitReportDataInitStep()); 
    	
    	//algorithm.addInitStep(new FlatReportExtractTotalsDataInitStep());
    	algorithm.addInitStep(new IntermedReportExtractTotalsDataInitStep(ContextKeys.INTERNAL_DATA_COLS));
    	
    	algorithm.addInitStep(new StartReportInitStep()); 
    	algorithm.addInitStep(new CrosstabHeaderOutputInitStep());
    	
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new IntermedGroupLevelDetectorStep(ContextKeys.INTERNAL_GROUP_COLS));
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new IntermedTotalsOutputStep(ContextKeys.INTERNAL_DATA_COLS, ContextKeys.INTERNAL_GROUP_COLS));
    		algorithm.addMainStep(new IntermedTotalsCalculatorStep());//this uses the internal data
    	}
    	
        algorithm.addMainStep(new IntermedDataRowsOutputStep(ContextKeys.INTERNAL_DATA_COLS, ContextKeys.INTERNAL_GROUP_COLS));
        
        if(getGroupColumns() != null && getGroupColumns().size() > 0){
        	algorithm.addMainStep(new IntermedPreviousRowManagerStep());//uses internal data
        }
        
        algorithm.addExitStep(new EndReportExitStep()); //uses original output
        algorithm.addExitStep(new CloseReportIOExitStep()); 
        
        return algorithm; 
	}
	
	/**
	 * configuration of the second report based on the results of the first one
	 */
//	private void configSecondReportDeleteme() {
//		try{
//			//transfer data from first report to the second 
//			//TODO: Move this in the transfer method and improve
//			
//			//AlgoContext firstReportContext = firstReport.getAlgorithm().getContext(); 
//			//IDistinctValuesHolder distinctValuesHolder = 
//			//	(IDistinctValuesHolder)firstReportContext.get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
//			
//			IDistinctValuesHolder distinctValuesHolder = 
//					(IDistinctValuesHolder)firstReport.getAlgorithm().getResult(IOKeys.DISTINCT_VALUES_HOLDER); 
//			
//			CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
//			crosstabMetadata.computeCoefficients(); 
//			
//			secondReport = new SecondCrosstabProcessorReport(crosstabMetadata);  
//			InputStream secondReportInput = new FileInputStream(((IntermediateCrosstabOutput)firstReport.getOut()).getSerializedOutputFile()); 
//			secondReport.setIn(new IntermediateCrosstabReportInput(secondReportInput)); 
//			secondReport.setOut(getOut()); 
//			
//			List<DataColumn> secondReportDataCols = 
//					constructDataColumnsForSecondProcess(crosstabMetadata, 
//														getDataColumns(), 
//														getShowTotals(), 
//														getShowGrandTotal());
//			
//			List<GroupColumn> secondReportGroupCols = constructGroupColumnsForSecondProcess(getGroupColumns()); 
//			
//			secondReport.setGroupColumns(secondReportGroupCols); 
//			secondReport.setDataColumns(secondReportDataCols);
//			secondReport.setShowDataRows(true); 
//			secondReport.setShowTotals(getShowTotals());
//			secondReport.setShowGrandTotal(getShowGrandTotal()); 
//		}catch(FileNotFoundException fnfExc){
//			throw new ConfigValidationException(fnfExc); 
//		}
//	}
	
	
	/**
	 * getter method for crosstabl header rows
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
        
        
        reportAlgoContainer.execute(); 
         
    }
}