/**
 * 
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.config.ICrosstabData;
import net.sf.reportengine.config.ICrosstabHeaderRow;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupColumn;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.IReportAlgorithm;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculatorsFactory;
import net.sf.reportengine.core.steps.ComputeColumnValuesStep;
import net.sf.reportengine.core.steps.FlatReportExtractDataInitStep;
import net.sf.reportengine.core.steps.GroupingLevelDetectorStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.IntermediateCrosstabRowMangerStep;
import net.sf.reportengine.util.ContextKeys;

import org.apache.log4j.Logger;

/**
 * This is for internal use only.  
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
class IntermediateCrosstabReport extends AbstractOneIterationReport {
	
	/**
	 * the logger
	 */
	private static final Logger logger = Logger.getLogger(IntermediateCrosstabReport.class);
	
	private ICrosstabData crosstabData; //TODO: try to remove the crosstab data from here
	
	private List<ICrosstabHeaderRow> crosstabHeaderRowsAsList; //TODO: remove the header rows and crosstabData from here ( they belong only to Crosstabreport)
	
	
	private int originalGroupColsCount;
	private int originalDataColsCount ;
	
	public IntermediateCrosstabReport(int originalGroupColsCount, int originalDataColsCount){
		this.originalDataColsCount = originalDataColsCount; 
		this.originalGroupColsCount = originalGroupColsCount;
		if(logger.isDebugEnabled()){
			logger.debug("constructing intermediate report algorithm origGrpCols="+originalGroupColsCount+", origDataColsCnt="+originalDataColsCount);
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.AbstractReport#configAlgorithmSteps()
	 */
	@Override
	protected void configAlgorithmSteps() {
		IReportAlgorithm algorithm = getAlgorithm();
    	IReportContext context = algorithm.getContext();
    	
    	if(logger.isDebugEnabled()){
    		logger.debug("dataColsIsNull ? "+(getDataColumns()==null));
    	}
    	
		IGroupColumn[] intermediateGroupCols = transformGroupingCrosstabConfigInFlatReportConfig(getGroupColumns(),
																									getDataColumns(), 
																									getCrosstabHeaderRows());
		IDataColumn[] intermediateDataCols = new IDataColumn[]{
				//TODO: come back here. We need to take the last in the groupingLevel order not in the current order
				/*new FlatDataColumnFromHeaderRow(crosstabHeaderRows[crosstabHeaderRows.length-1]), */ 
				new IntermDataColumnFromCrosstabData(crosstabData)};
		
		if(logger.isDebugEnabled()){
			logger.debug("configuring intermediate report algorithm: intermediateGroupCols "+Arrays.toString(intermediateGroupCols)+" ");
		}
		
		//setting the input/output
		algorithm.setIn(getIn());
		algorithm.setOut(getOut());
		
		//context keys specific to a flat report
		context.set(ContextKeys.CONTEXT_KEY_DATA_COLUMNS, intermediateDataCols);
		context.set(ContextKeys.CONTEXT_KEY_GROUPING_COLUMNS, intermediateGroupCols); 
		
		context.set(ContextKeys.CONTEXT_KEY_SHOW_TOTALS, Boolean.valueOf(getShowTotals()));
    	context.set(ContextKeys.CONTEXT_KEY_SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
    	
    	context.set(ContextKeys.CONTEXT_KEY_ORIGINAL_CT_DATA_COLS_COUNT, originalDataColsCount);
    	context.set(ContextKeys.CONTEXT_KEY_ORIGINAL_CT_GROUP_COLS_COUNT, originalGroupColsCount);
    	
		context.set(ContextKeys.CONTEXT_KEY_CROSSTAB_HEADER_ROWS, getCrosstabHeaderRows()); 
		context.set(ContextKeys.CONTEXT_KEY_CROSSTAB_DATA, crosstabData); 
		
		//adding specific flat report steps to the algorithm
    	algorithm.addInitStep(new FlatReportExtractDataInitStep());
    	//only for debug
    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Intermediate report"));
        
    	//main steps
    	algorithm.addMainStep(new CrosstabDistinctValuesDetectorStep());
    	algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupingLevelDetectorStep());
    	
    	//only for debug if( getShowTotals() || getShowGrandTotal()) algorithm.addMainStep(new FlatReportTotalsOutputStep());
    	
    	
    	algorithm.addMainStep(new IntermediateCrosstabRowMangerStep());
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new TotalsCalculatorStep());
    	}
    	
    	//only for debug algorithm.addMainStep(new DataRowsOutputStep());
    	
    	if( intermediateGroupCols.length > 0){
    		algorithm.addMainStep(new PreviousRowManagerStep());
    	}
	}

	public ICrosstabHeaderRow[] getCrosstabHeaderRows() {
		return crosstabHeaderRowsAsList.toArray(new ICrosstabHeaderRow[crosstabHeaderRowsAsList.size()]);
	}
	
	/**
	 * 
	 * @param crosstabHeaderRows
	 * @deprecated
	 */
	public void setCrosstabHeaderRows(ICrosstabHeaderRow[] crosstabHeaderRows) {
		this.crosstabHeaderRowsAsList = Arrays.asList(crosstabHeaderRows);
	}
	
	/**
	 * 
	 * @param crosstabHeaderRows
	 */
	public void setCrosstabHeaderRows(List<ICrosstabHeaderRow> crosstabHeaderRows){
		this.crosstabHeaderRowsAsList = crosstabHeaderRows; 
	}
	
	public ICrosstabData getCrosstabData() {
		return crosstabData;
	}

	public void setCrosstabData(ICrosstabData crosstabData) {
		this.crosstabData = crosstabData;
	}
	
	/**
	 * 
	 * @param originalCtGroupingCols
	 * @param originalCtDataCols
	 * @param originalCtHeaderRows
	 * @return
	 * @deprecated
	 */
	protected IGroupColumn[] transformGroupingCrosstabConfigInFlatReportConfig(	
			IGroupColumn[] originalCtGroupingCols, 
			IDataColumn[] originalCtDataCols, 
			ICrosstabHeaderRow[] originalCtHeaderRows){
		
		List<IGroupColumn> groupColsAsList = originalCtGroupingCols != null ? Arrays.asList(originalCtGroupingCols): new ArrayList<IGroupColumn>();
		
		return transformGroupingCrosstabConfigInFlatReportConfig(
													groupColsAsList, 
													Arrays.asList(originalCtDataCols), 
													Arrays.asList(originalCtHeaderRows));
	}
	
	/**
	 * transforms the original crosstab columns into a flat report configuration 
	 * 
	 * @param originalCtGroupingCols
	 * @param originalCtDataCols
	 * @param originalCtHeaderRows
	 * @return
	 */
	protected IGroupColumn[] transformGroupingCrosstabConfigInFlatReportConfig(	List<IGroupColumn> originalCtGroupingCols, 
																				List<IDataColumn> originalCtDataCols, 
																				List<ICrosstabHeaderRow> originalCtHeaderRows){
		int originalGroupColsLength = originalCtGroupingCols != null ? originalCtGroupingCols.size(): 0;
		int originalDataColsLength = originalCtDataCols != null ? originalCtDataCols.size() : 0 ; 
		
		if(logger.isDebugEnabled()){
			logger.debug(	"transforming grouping crosstab config into flat intermediary report: origCtGroupingCols="+originalGroupColsLength+
							", originalCtDataRows="+originalCtDataCols+
							", originalCtHeaderRows="+originalCtHeaderRows);
		}
		
		int intermedGroupColsLength = originalGroupColsLength + originalDataColsLength + originalCtHeaderRows.size() -1;
		IGroupColumn[] result = new IGroupColumn[intermedGroupColsLength];
		
		//from 0 to original groupCols.length we copy the original group columns
		if(originalGroupColsLength > 0){
			//System.arraycopy(originalCtGroupingCols, 0, result, 0, originalCtGroupingCols.size());
			for(int i=0; i<originalCtGroupingCols.size(); i++){
				result[i] = originalCtGroupingCols.get(i);
			}
		}
		
		//from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
		for(int i=0; i < originalDataColsLength; i++){
			result[originalGroupColsLength+i] = new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i);
		}
		
		//then we copy the header rows (of course transformed as groupCols)
		//we don't need any grouping for the last header row (that's why we have headerRows.length-1 below
		for (int i = 0; i < originalCtHeaderRows.size()-1; i++) {
			
			result[originalGroupColsLength+originalDataColsLength+i] = new IntermGroupColFromHeaderRow(
																				originalCtHeaderRows.get(i), 
																				originalGroupColsLength+originalDataColsLength+i);
		}
		
		return result; 
	}
	
	protected static class IntermDataColumnFromCrosstabData implements IDataColumn{
		
		private ICrosstabData crosstabData;
		
		public IntermDataColumnFromCrosstabData(ICrosstabData crosstabData){
			this.crosstabData = crosstabData; 
		}
		
		public String getHeader() {
			return "CrosstabDataColumn"; //normally this should never be called (except when debugging reports)
		}

		public String getFormattedValue(Object value) {
			return crosstabData.getFormattedValue(value);
		}

		public Object getValue(NewRowEvent newRowEvent) {
			return crosstabData.getValue(newRowEvent);
		}

		public ICalculatorsFactory getCalculator() {
			return crosstabData.getCalculator();
		}

		public HorizontalAlign getHorizAlign() {
			return crosstabData.getHorizAlign(); 
		}
	}
	
	protected static class IntermGroupColFromCtDataCol implements IGroupColumn{
		
		private IDataColumn dataColumn; 
		private int groupingLevel; 
		
		public IntermGroupColFromCtDataCol(IDataColumn dataColumn, int groupLevel){
			this.dataColumn = dataColumn; 
			this.groupingLevel = groupLevel; 
		}
		
		public String getFormattedValue(Object object) {
			return dataColumn.getFormattedValue(object); 
		}

		public int getGroupingLevel() {
			return groupingLevel;
		}

		public String getHeader() {
			return dataColumn.getHeader(); 
		}

		public Object getValue(NewRowEvent newRowEvent) {
			return dataColumn.getValue(newRowEvent);
		}

		public HorizontalAlign getHorizAlign() {
			return dataColumn.getHorizAlign(); 
		}
		
	}
	
	protected static class IntermGroupColFromHeaderRow implements IGroupColumn {

		private ICrosstabHeaderRow headerRow; 
	
		private int groupingLevel = -1; 
	
		public IntermGroupColFromHeaderRow(ICrosstabHeaderRow headerRow, int groupingLevel){
			this.headerRow = headerRow; 
			this.groupingLevel = groupingLevel;
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.IGroupColumn#getHeader()
		 */
		public String getHeader() {
			return "not used";
		}
	
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.IGroupColumn#getGroupingLevel()
		 */
		public int getGroupingLevel() {
			return groupingLevel;
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.IGroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
		 */
		public Object getValue(NewRowEvent newRowEvent) {
			return headerRow.getValue(newRowEvent);
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.IGroupColumn#getFormattedValue(java.lang.Object)
		 */
		public String getFormattedValue(Object object) {
			String result = "";
			if(object != null){
				result = object.toString();
				//TODO: come back here and return a formatted value
			}
			return result; 
		}

		public HorizontalAlign getHorizAlign() {
			return HorizontalAlign.CENTER; //TODO: check if this is used
		}

}

}
