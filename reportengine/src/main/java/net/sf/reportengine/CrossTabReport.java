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
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessGroupColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.AlgoResult;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IDistinctValuesHolder;

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
	 * the intermediate report is in charge of : 
	 * 	1. discovering the distinct values for the header 
	 *  2. arranging the initial crosstab data into rows for the second report
	 *  3. computing totals on the crosstab data.
	 */
	private IntermediateCrosstabReport firstReport;
	
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
	private List<CrosstabHeaderRow> crosstabHeaderRowsAsList; 
	
	/**
	 * the crossta data
	 */
	private CrosstabData crosstabData; 
	
	/**
	 * constructs a new crosstab report
	 */
	public CrossTabReport(){
		this.crosstabHeaderRowsAsList = new ArrayList<CrosstabHeaderRow>();
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
		List<GroupColumn> groupCols = getGroupColumns(); 
		List<DataColumn> dataColsList = getDataColumns(); 
		
		int groupColsLength = groupCols != null ? groupCols.size() : 0;
		int dataColsLength = dataColsList != null ? dataColsList.size() : 0;
		
		IntermediateCrosstabOutput firstReportOutput = new IntermediateCrosstabOutput(); 		
		firstReport = new IntermediateCrosstabReport(groupColsLength, dataColsLength); 
		firstReport.setIn(getIn()); 
		firstReport.setOut(firstReportOutput); 
		firstReport.setGroupColumns(groupCols); 
		firstReport.setDataColumns(dataColsList); 
		firstReport.setCrosstabHeaderRows(getCrosstabHeaderRows()); 
		firstReport.setCrosstabData(getCrosstabData()); 
		firstReport.setShowDataRows(true); 
		firstReport.setShowTotals(getShowTotals());
		firstReport.setShowGrandTotal(getShowGrandTotal()); 
	}
			
	
	/**
	 * configuration of the second report based on the results of the first one
	 */
	private void configSecondReport() {
		try{
			//transfer data from first report to the second 
			//TODO: Move this in the transfer method and improve
			
			//ReportContext firstReportContext = firstReport.getAlgorithm().getContext(); 
			//IDistinctValuesHolder distinctValuesHolder = 
			//	(IDistinctValuesHolder)firstReportContext.get(ContextKeys.INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			
			IDistinctValuesHolder distinctValuesHolder = 
					(IDistinctValuesHolder)firstReport.getAlgorithm().getResult("intermediateDistictValuesHolder"); 
			
			CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
			crosstabMetadata.computeCoefficients(); 
			
			secondReport = new SecondCrosstabProcessorReport(crosstabMetadata);  
			InputStream secondReportInput = new FileInputStream(((IntermediateCrosstabOutput)firstReport.getOut()).getSerializedOutputFile()); 
			secondReport.setIn(new IntermediateCrosstabReportInput(secondReportInput)); 
			secondReport.setOut(getOut()); 
			
			List<DataColumn> secondReportDataCols = 
					constructDataColumnsForSecondProcess(crosstabMetadata, 
														getDataColumns(), 
														getShowTotals(), 
														getShowGrandTotal());
			List<GroupColumn> secondReportGroupCols = constructGroupColumnsForSecondProcess(getGroupColumns()); 
			
			secondReport.setGroupColumns(secondReportGroupCols); 
			secondReport.setDataColumns(secondReportDataCols);
			secondReport.setShowDataRows(true); 
			secondReport.setShowTotals(getShowTotals());
			secondReport.setShowGrandTotal(getShowGrandTotal()); 
		}catch(FileNotFoundException fnfExc){
			throw new ConfigValidationException(fnfExc); 
		}
	}
	
	/**
	 * transfer data between reports
	 */
	private void transferDataBetweenReports() {
		
	}
	
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
	 * creates a list of group columns for the second report based on the original group columns
	 * 
	 * @param originalGroupCols
	 * @return	a list of group columns necessary to the second processing
	 */
	protected List<GroupColumn> constructGroupColumnsForSecondProcess(List<GroupColumn> originalGroupCols){
		List<GroupColumn> result = null; 
		if(originalGroupCols != null && originalGroupCols.size() > 0){
			result = new ArrayList<GroupColumn>(originalGroupCols.size());
			for (GroupColumn originalGroupColumn : originalGroupCols) {
				result.add(new SecondProcessGroupColumn(originalGroupColumn));
			}
		}
		return result; 
	}
	
	/**
	 * creates a list of DataColumn objects from 
	 * 
	 * 1. original data columns 
	 * 2. columns needed for displaying the values under the header values ( computed form crosstab-data) 
	 * 3. (if needed ) columns needed to display the totals and grand total 
	 * 
	 * @param crosstabMetadata
	 * @param originalDataColumns
	 * @param hasTotals
	 * @param hasGrandTotal
	 * @return	a list data columns necessary to the second process
	 */
	protected List<DataColumn> constructDataColumnsForSecondProcess(	CtMetadata crosstabMetadata, 
																		List<DataColumn> originalDataColumns, 
																		boolean hasTotals, 
																		boolean hasGrandTotal){
		int dataColsCount = crosstabMetadata.getDataColumnCount(); 
		int headerRowsCount = crosstabMetadata.getHeaderRowsCount(); 
		
		List<DataColumn> resultDataColsList = new ArrayList<DataColumn>();
		
		//first we add the original data columns (those declared by the user in his configuration)
		for(int i=0; i < originalDataColumns.size(); i++){
			resultDataColsList.add(new SecondProcessDataColumnFromOriginalDataColumn(originalDataColumns.get(i), i));
		}

		// then we construct the columns 
		for(int column=0; column < dataColsCount; column++){
		
			//construct total columns 
			if(hasTotals){
				//we start bottom to top (from last header row -1 to first header row) 
				for(int row=headerRowsCount-2; row >= 0; row--){
					int colspan = crosstabMetadata.getColspanForLevel(row); 
				
					if(column != 0 && (column % colspan)==0){
						int[] positionForCurrentTotal = new int[row+1]; //new int[headerRowsCount-1];
					
						for(int j=0; j < positionForCurrentTotal.length; j++){
							positionForCurrentTotal[j] = ((column-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
						}
		
						resultDataColsList.add(
								new SecondProcessTotalColumn(	positionForCurrentTotal, 
																Calculators.SUM, 
																null, 
																"Total column="+column+ ",colspan= "+colspan));
					}
				}//end for
			}//end if has totals

			//data columns coming from data columns
			int[] positionForCurrentColumn = new int[headerRowsCount];
			for(int j=0; j < headerRowsCount; j++){
				positionForCurrentColumn[j] = (column / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
			}
		
			resultDataColsList.add(new SecondProcessDataColumn(positionForCurrentColumn, Calculators.SUM, null)); 
		}//end for columns
		
		//at the end we add one more total 
		if(hasTotals){
			//final total columns
			for(int row=headerRowsCount-2; row >= 0; row--){
				int colspan = crosstabMetadata.getColspanForLevel(row); 
			
				if(dataColsCount!= 0 && (dataColsCount % colspan)==0){
					int[] positionForCurrentTotal = new int[row+1];
			
					for(int j=0; j < positionForCurrentTotal.length; j++){
						positionForCurrentTotal[j] = ((dataColsCount-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
					}
					resultDataColsList.add(new SecondProcessTotalColumn(positionForCurrentTotal, Calculators.SUM, null, "Total column="+(dataColsCount)+ ",colspan= "+colspan));
				}
			}
		}//end if has totals

		// .. and finally the grand total
		if(hasGrandTotal){
			resultDataColsList.add(new SecondProcessTotalColumn(null, Calculators.SUM, null, "Grand Total")); 
		}

		return resultDataColsList; 
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
        firstReport.execute(); 
        
        //transfer data between reports
        transferDataBetweenReports(); 
        
        //configure the second report
        configSecondReport();
        secondReport.execute(); 
    }
}