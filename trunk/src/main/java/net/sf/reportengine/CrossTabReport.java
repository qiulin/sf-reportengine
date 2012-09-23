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
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep;
import net.sf.reportengine.in.IntermediateCrosstabReportInput;
import net.sf.reportengine.out.IntermediateCrosstabOutput;
import net.sf.reportengine.util.CtMetadata;
import net.sf.reportengine.util.IDistinctValuesHolder;

import org.apache.log4j.Logger;

/**
 * <p>
 *  Helper tool for Cross tab reports.
 *  A basic configuration consists in : 
 *      <ul>
 *          <li>Y Columns using <source>setYColumns(int[])</source></li>
 *          <li>X Columns using <source>setXColumns(int[])</source></li>
 *          <li>data Columns using <source>setDataColumns(int[])</source></li>
 *      </ul>
 *   However you can also set only two of them and let this class to detect the third .<br>
 *   For instance , you can set xColumns and y columnas and the data columns will be detected as being 
 *   all the rest.
 *   If you want to add some aggregated columns you have to specify :
 *      <ul>
 *          <li>The ICalculator used for x elements</li>
 *          <li>The ICalculators used for y elements</li>
 *      </ul>
 *   It will be a difficult task to use the <code>setXCalcultors()</code> method because you have to know how 
 *   many columns will be displayed on y axis that's why you can use <code>useSameForAllXCalculators()</code>
 *   if you don't know how many ICalculators to set on x axis and you want only one type of calculators
 * </p>
 * @author dragos balan (dragos.balan@gmail.com)
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
	
	private ICrosstabHeaderRow[] crosstabHeaderRows; 
	private ICrosstabData crosstabData; 
	
	/**
	 * 
	 */
	public static final String CONTEXT_KEY_CROSSTAB_GROUP_COLS = "net.sf.reportengine.crosstab.groupCols";
	public static final String CONTEXT_KEY_CROSSTAB_HEADER_ROWS = "net.sf.reportengine.crosstab.headerRows"; 
	public static final String CONTEXT_KEY_CROSSTAB_DATA = "net.sf.reportengine.crosstab.data"; 
	public static final String CONTEXT_KEY_CROSSTAB_METADATA = "net.sf.reportengine.crosstab.metadata";
	public static final String CONTEXT_KEY_CROSSTAB_HEADER_HAS_TOTALS = "net.sf.reportengine.crosstab.headerHasTotals";
	
	
	@Override
	protected void validateConfig() throws ConfigValidationException {
		super.validateConfig();
	}

    
	@Override
	protected void configAlgorithmSteps() {
		try{
			IGroupColumn[] groupCols = getGroupingColumns(); 
			IDataColumn[] dataCols = getDataColumns(); 
			
			int groupColsLength = groupCols != null ? groupCols.length : 0;
			int dataColsLength = dataCols != null ? dataCols.length : 0;
					
			firstReportOutput = new IntermediateCrosstabOutput(); 		
			firstReport = new IntermediateCrosstabReport(groupColsLength, dataColsLength); 
			firstReport.setIn(getIn()); 
			firstReport.setOut(firstReportOutput); 
			firstReport.setGroupingColumns(getGroupingColumns()); 
			firstReport.setDataColumns(getDataColumns()); 
			firstReport.setCrosstabHeaderRows(getCrosstabHeaderRows()); 
			firstReport.setCrosstabData(getCrosstabData()); 
			firstReport.setShowDataRows(true); 
			firstReport.setShowTotals(getShowTotals());
			firstReport.setShowGrandTotal(getShowGrandTotal()); 
			
			//the execute method
			firstReport.execute(); 
			
			//transfer data from first report to the second
			IAlgorithmContext firstReportContext = firstReport.getAlgorithm().getContext(); 
			IDistinctValuesHolder distinctValuesHolder = (IDistinctValuesHolder)firstReportContext.get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_DISTINCT_VALUES_HOLDER);
			CtMetadata crosstabMetadata = new CtMetadata(distinctValuesHolder);
			crosstabMetadata.computeCoefficients(); 
			
			//config the second report
			secondReport = new SecondCrosstabProcessorReport(crosstabMetadata);  
			InputStream secondReportInput = new FileInputStream(firstReportOutput.getSerializedOutputFile()); 
			secondReport.setIn(new IntermediateCrosstabReportInput(secondReportInput)); 
			secondReport.setOut(getOut()); 
			
			IDataColumn[] secondReportDataCols = constructDataColumnsForSecondProcess(crosstabMetadata, 
													getDataColumns(), 
													getShowTotals(), 
													getShowGrandTotal());
			IGroupColumn[] secondReportGroupCols = constructGroupColumnsForSecondProcess(getGroupingColumns()); 
			
			secondReport.setGroupingColumns(secondReportGroupCols); 
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
	
	public ICrosstabHeaderRow[] getCrosstabHeaderRows() {
		return crosstabHeaderRows;
	}

	public void setCrosstabHeaderRows(ICrosstabHeaderRow[] crosstabHeaderRows) {
		this.crosstabHeaderRows = crosstabHeaderRows;
	}
	
	public ICrosstabData getCrosstabData() {
		return crosstabData;
	}

	public void setCrosstabData(ICrosstabData crosstabData) {
		this.crosstabData = crosstabData;
	}
	
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
						
						auxListOfDataCols.add(new SecondProcessTotalColumn(positionForCurrentTotal, Calculator.SUM, null, "Total column="+column+ ",colspan= "+colspan));
					}
				}
			}//end if has totals
			
			//data columns coming from data columns
			int[] positionForCurrentColumn = new int[headerRowsCount];
			for(int j=0; j < headerRowsCount; j++){
				positionForCurrentColumn[j] = (column / crosstabMetadata.getColspanForLevel(j)) % crosstabMetadata.getDistinctValuesCountForLevel(j);
			}
			
			auxListOfDataCols.add(new SecondProcessDataColumn(positionForCurrentColumn, Calculator.SUM, null)); 
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
					auxListOfDataCols.add(new SecondProcessTotalColumn(positionForCurrentTotal, Calculator.SUM, null, "Total column="+(dataColsCount)+ ",colspan= "+colspan));
				}
			}
		}
		
		//grand total
		if(hasGrandTotal){
			auxListOfDataCols.add(new SecondProcessTotalColumn(null, Calculator.SUM, null, "GrandTotal")); 
		}
		
		return auxListOfDataCols.toArray(new IDataColumn[]{}); 
	}
}
