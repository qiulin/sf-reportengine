/**
 * 
 */
package net.sf.reportengine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.ICrosstabData;
import net.sf.reportengine.config.ICrosstabHeaderRow;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.config.SecondProcessDataColumn;
import net.sf.reportengine.config.SecondProcessDataColumnFromOriginalDataColumn;
import net.sf.reportengine.config.SecondProcessGroupColumn;
import net.sf.reportengine.config.SecondProcessTotalColumn;
import net.sf.reportengine.core.ConfigValidationException;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IDistinctValuesHolder;

import org.apache.log4j.Logger;

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
 * A simple pivot table repost example is: 
 * <pre>
 * {@code
 *  CrossTabReport report = new CrossTabReport(); 
 *	
 *  //set up the input/output			
 *  IReportInput in = new TextInput(new FileInputStream("expenses.csv"));
 *  report.setIn(input); 
 *			
 *  IReportOutput output = new HtmlOutput(new FileOutputStream("xpenses.html")); 
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
 * @see IReportInput
 * @see IReportOutput
 * @see IHeaderRow
 * @see IDataColumn
 * @see IGroupColumn
 * @see ICrosstabData
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2 
 */
public class CrossTabReport extends AbstractReport{
	
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(CrossTabReport.class);
	
	private IntermediateCrosstabReport firstReport;
	private IntermediateCrosstabOutput firstReportOutput;
	
	private SecondCrosstabProcessorReport secondReport; 
	
	private List<ICrosstabHeaderRow> crosstabHeaderRowsAsList; 
	
	private ICrosstabData crosstabData; 
	
	
	public CrossTabReport(){
		this.crosstabHeaderRowsAsList = new ArrayList<ICrosstabHeaderRow>();
	}
	
	
	/**
	 * validates the configuration
	 */
	@Override protected void validateConfig(){
		super.validateConfig();
		if(getCrosstabData() == null){
			throw new ConfigValidationException("Crosstab reports need crosstab data configured"); 
		}
		
		if(getCrosstabHeaderRows() == null || getCrosstabHeaderRows().size() ==0){
			throw new ConfigValidationException("Crosstab reports need header rows configured");
		}
		
		if((getDataColumns() == null || getDataColumns().size() == 0) 
			&&
			(getGroupColumns() == null || getGroupColumns().size() == 0)
			){
			throw new ConfigValidationException("Crosstab reports need data and/or group columns configured"); 
		}
	}

    
	@Override
	protected void configAlgorithmSteps() {
		try{
			List<IGroupColumn> groupCols = getGroupColumns(); 
			List<IDataColumn> dataColsList = getDataColumns(); 
			
			int groupColsLength = groupCols != null ? groupCols.size() : 0;
			int dataColsLength = dataColsList != null ? dataColsList.size() : 0;
					
			firstReportOutput = new IntermediateCrosstabOutput(); 		
			firstReport = new IntermediateCrosstabReport(groupColsLength, dataColsLength); 
			firstReport.setIn(getIn()); 
			firstReport.setOut(firstReportOutput); 
			firstReport.setGroupColumns(getGroupColumns()); 
			firstReport.setDataColumns(dataColsList); 
			firstReport.setCrosstabHeaderRows(getCrosstabHeaderRows()); 
			firstReport.setCrosstabData(getCrosstabData()); 
			firstReport.setShowDataRows(true); 
			firstReport.setShowTotals(getShowTotals());
			firstReport.setShowGrandTotal(getShowGrandTotal()); 
			
			//the execute method
			firstReport.execute(); 
			
			//transfer data from first report to the second
			IReportContext firstReportContext = firstReport.getAlgorithm().getContext(); 
			IDistinctValuesHolder distinctValuesHolder = (IDistinctValuesHolder)firstReportContext.get(ContextKeys.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
			crosstabMetadata.computeCoefficients(); 
			
			//config the second report
			secondReport = new SecondCrosstabProcessorReport(crosstabMetadata);  
			InputStream secondReportInput = new FileInputStream(firstReportOutput.getSerializedOutputFile()); 
			secondReport.setIn(new IntermediateCrosstabReportInput(secondReportInput)); 
			secondReport.setOut(getOut()); 
			
			List<IDataColumn> secondReportDataCols = 
					constructDataColumnsForSecondProcess(crosstabMetadata, 
														getDataColumns(), 
														getShowTotals(), 
														getShowGrandTotal());
			List<IGroupColumn> secondReportGroupCols = constructGroupColumnsForSecondProcess(getGroupColumns()); 
			
			secondReport.setGroupColumns(secondReportGroupCols); 
			secondReport.setDataColumns(secondReportDataCols);
			secondReport.setShowDataRows(true); 
			secondReport.setShowTotals(getShowTotals());
			secondReport.setShowGrandTotal(getShowGrandTotal()); 
		}catch(FileNotFoundException fnfExc){
			throw new ConfigValidationException(fnfExc); 
		}
	}

	@Override
	protected void executeAlgorithm() {
		secondReport.execute(); 
	}
	
	/**
	 * returns a list with the header rows
	 * @return
	 */
	public List<ICrosstabHeaderRow> getCrosstabHeaderRows() {
//		ICrosstabHeaderRow[] result = null; 
//		if(crosstabHeaderRowsAsList != null){
//			result = crosstabHeaderRowsAsList.toArray(new ICrosstabHeaderRow[crosstabHeaderRowsAsList.size()]);
//		}else{
//			//TODO : is this really necessary ? 
//			throw new ReportEngineRuntimeException("No header rows have been configured. Please use the setHeaderRows or addHeaderRow methods to fix this issue");
//		}
//		return result;
		return crosstabHeaderRowsAsList; 
	}
	
//	/**
//	 * 
//	 * @param crosstabHeaderRows
//	 * @deprecated use setHeaderRows(List) or addHeaderRow(IHeaderRow) instead
//	 */
//	public void setHeaderRows(ICrosstabHeaderRow[] crosstabHeaderRows) {
//		this.crosstabHeaderRowsAsList = Arrays.asList(crosstabHeaderRows);
//	}
	
	public void setHeaderRows(List<ICrosstabHeaderRow> crosstabHeaderRowsList) {
		this.crosstabHeaderRowsAsList = crosstabHeaderRowsList; 
	}
	
	public void addHeaderRow(ICrosstabHeaderRow newHeaderRow){
		this.crosstabHeaderRowsAsList.add(newHeaderRow);
	}
	
	public ICrosstabData getCrosstabData() {
		return crosstabData;
	}

	public void setCrosstabData(ICrosstabData crosstabData) {
		this.crosstabData = crosstabData;
	}
	
	/**
	 * 
	 * @param originalGroupCols
	 * @return
	 * @deprecated
	 */
	protected IGroupColumn[] constructGroupColumnsForSecondProcess(IGroupColumn[] originalGroupCols){
		IGroupColumn[] result = null; 
		if(originalGroupCols != null && originalGroupCols.length > 0){
			result = new IGroupColumn[originalGroupCols.length];
			for (int i = 0; i < originalGroupCols.length; i++) {
				IGroupColumn origGroupColumn = originalGroupCols[i];
				result[i] = new SecondProcessGroupColumn(origGroupColumn);
			}
		}
		return result; 
	}
	
	/**
	 * 
	 * @param originalGroupCols
	 * @return
	 */
	protected List<IGroupColumn> constructGroupColumnsForSecondProcess(List<IGroupColumn> originalGroupCols){
		List<IGroupColumn> result = null; 
		if(originalGroupCols != null && originalGroupCols.size() > 0){
			result = new ArrayList<IGroupColumn>(originalGroupCols.size());
			for (int i = 0; i < originalGroupCols.size(); i++) {
				IGroupColumn origGroupColumn = originalGroupCols.get(i);
				result.add(new SecondProcessGroupColumn(origGroupColumn));
				//TODO: remove the i index 
			}
		}
		return result; 
	}
	
	/**
	 * 
	 * @param crosstabMetadata
	 * @param originalDataColumns
	 * @param hasTotals
	 * @param hasGrandTotal
	 * @return
	 * @deprecated
	 */
	protected IDataColumn[] constructDataColumnsForSecondProcess(	CtMetadata crosstabMetadata, 
																	IDataColumn[] originalDataColumns, 
																	boolean hasTotals, 
																	boolean hasGrandTotal){
		
		int dataColsCount = crosstabMetadata.getDataColumnCount(); 
		int headerRowsCount = crosstabMetadata.getHeaderRowsCount(); 
		
		List<IDataColumn> auxListOfDataCols = new ArrayList<IDataColumn>();
		
		//first we add the original data columns (those declared by the user in his configuration)
		for(int i=0; i < originalDataColumns.length; i++){
			auxListOfDataCols.add(new SecondProcessDataColumnFromOriginalDataColumn(originalDataColumns[i], i));
		}
		
		//then we construct the columns 
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
						
						auxListOfDataCols.add(new SecondProcessTotalColumn(positionForCurrentTotal, Calculators.SUM, null, "Total column="+column+ ",colspan= "+colspan));
					}
				}
			}//end if has totals
			
			//data columns coming from data columns
			int[] positionForCurrentColumn = new int[headerRowsCount];
			for(int j=0; j < headerRowsCount; j++){
				positionForCurrentColumn[j] = (column / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
			}
			
			auxListOfDataCols.add(
					new SecondProcessDataColumn(positionForCurrentColumn, 
												Calculators.SUM, 
												null)); 
		}
		
		if(hasTotals){
			//final total columns
			for(int row=headerRowsCount-2; row >= 0; row--){
				int colspan = crosstabMetadata.getColspanForLevel(row); 
				
				if(dataColsCount!= 0 && (dataColsCount % colspan)==0){
					int[] positionForCurrentTotal = new int[row+1];
					
					for(int j=0; j < positionForCurrentTotal.length; j++){
						positionForCurrentTotal[j] = ((dataColsCount-1) / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
					}
					auxListOfDataCols.add(new SecondProcessTotalColumn(positionForCurrentTotal, Calculators.SUM, null, "Total column="+(dataColsCount)+ ",colspan= "+colspan));
				}
			}
		}
		
		//grand total
		if(hasGrandTotal){
			auxListOfDataCols.add(new SecondProcessTotalColumn(null, Calculators.SUM, null, "GrandTotal")); 
		}
		
		return auxListOfDataCols.toArray(new IDataColumn[auxListOfDataCols.size()]); 
	}
	
	/**
	 * 
	 * @param crosstabMetadata
	 * @param originalDataColumns
	 * @param hasTotals
	 * @param hasGrandTotal
	 * @return
	 */
	protected List<IDataColumn> constructDataColumnsForSecondProcess(	CtMetadata crosstabMetadata, 
																		List<IDataColumn> originalDataColumns, 
																		boolean hasTotals, 
																		boolean hasGrandTotal){

		int dataColsCount = crosstabMetadata.getDataColumnCount(); 
		int headerRowsCount = crosstabMetadata.getHeaderRowsCount(); 
		
		List<IDataColumn> resultDataColsList = new ArrayList<IDataColumn>();
		
		//first we add the original data columns (those declared by the user in his configuration)
		for(int i=0; i < originalDataColumns.size(); i++){//TODO come back here and call addAll
			resultDataColsList.add(new SecondProcessDataColumnFromOriginalDataColumn(originalDataColumns.get(i), i));
		}

		//then we construct the columns 
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
		
						resultDataColsList.add(new SecondProcessTotalColumn(positionForCurrentTotal, Calculators.SUM, null, "Total column="+column+ ",colspan= "+colspan));
					}
				}
			}//end if has totals

			//data columns coming from data columns
			int[] positionForCurrentColumn = new int[headerRowsCount];
			for(int j=0; j < headerRowsCount; j++){
				positionForCurrentColumn[j] = (column / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
			}
		
			resultDataColsList.add(new SecondProcessDataColumn(positionForCurrentColumn, Calculators.SUM, null)); 
		}//end for columns
		
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

		//grand total
		if(hasGrandTotal){
			resultDataColsList.add(new SecondProcessTotalColumn(null, Calculators.SUM, null, "GrandTotal")); 
		}

		return resultDataColsList; 
	}
}