/**
 * 
 */
package net.sf.reportengine;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.algorithm.LoopThroughReportInputAlgo;
import net.sf.reportengine.core.algorithm.MultiStepAlgo;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.steps.CloseReportIOExitStep;
import net.sf.reportengine.core.steps.EndReportExitStep;
import net.sf.reportengine.core.steps.FlatReportExtractTotalsDataInitStep;
import net.sf.reportengine.core.steps.GroupLevelDetectorStep;
import net.sf.reportengine.core.steps.OpenReportIOInitStep;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StartReportInitStep;
import net.sf.reportengine.core.steps.TotalsCalculatorStep;
import net.sf.reportengine.core.steps.crosstab.DistinctValuesDetectorStep;
import net.sf.reportengine.core.steps.crosstab.IntermedRowMangerStep;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is for internal use only.
 * 
 *  The original crosstab configuration is used to create a flat intermediate report:
 *  
 *  1. all initial group + data + header columns will be used as group column in this intermediate report
 *  2. only the initial crosstab-data will be used as data column in this intermediate report
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 * @deprecated
 */
class IntermediateCrosstabReport extends AbstractMultiStepAlgoColumnBasedReport {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(IntermediateCrosstabReport.class);
	
	/**
	 * 
	 */
	private CrosstabData crosstabData; //TODO: try to remove the crosstab data from here
	
	/**
	 * 
	 */
	private List<? extends CrosstabHeaderRow> crosstabHeaderRowsAsList; //TODO: remove the header rows and crosstabData from here ( they belong only to Crosstabreport)
	
	/**
	 * the initial group columns count
	 */
	private int originalGroupColsCount;
	
	/**
	 * the initial data columns count
	 */
	private int originalDataColsCount ;
	
	/**
	 * 
	 * @param originalGroupColsCount
	 * @param originalDataColsCount
	 */
	public IntermediateCrosstabReport(int originalGroupColsCount, int originalDataColsCount){
		super(new LoopThroughReportInputAlgo()); 
		LOGGER.debug(	"constructing intermediate report algorithm origGrpCols={}, origDataColsCnt{}",
						originalGroupColsCount, originalDataColsCount);
		this.originalDataColsCount = originalDataColsCount; 
		this.originalGroupColsCount = originalGroupColsCount;
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.AbstractReport#configAlgorithmSteps()
	 */
	@Override protected void config() {
		LOGGER.trace("configuring the intermediate crosstab report"); 
		MultiStepAlgo algorithm = getAlgorithm();
    	//AlgoContext context = algorithm.getContext();
    	
    	//all initial group + data + header columns will be used as group column in this intermediate report
		List<GroupColumn> intermediateGroupCols = null;
//			transformGroupingCrosstabConfigInFlatReportConfig(	getGroupColumns(),
//																getDataColumns(), 
//																getCrosstabHeaderRows());
		//only the initial crosstab-data will be used as data column in this intermediate report
		List<DataColumn> intermediateDataCols = null; 
//				new ArrayList<DataColumn>(1);
//		intermediateDataCols.add(new IntermDataColumnFromCrosstabData(crosstabData)); 
		
		//TODO: come back here. We need to take the last in the groupingLevel order not in the current order
		/*new FlatDataColumnFromHeaderRow(crosstabHeaderRows[crosstabHeaderRows.length-1]), */ 
		//new IntermDataColumnFromCrosstabData(crosstabData)};
				
		LOGGER.debug("configuring intermediate report algorithm: ");
		LOGGER.debug("intermediateGroupCols = {}", intermediateGroupCols);
		LOGGER.debug("intermediateDataCols= {}", intermediateDataCols); 
				
		//setting the input/output
		algorithm.addIn(IOKeys.REPORT_INPUT, getIn());
		algorithm.addIn(IOKeys.REPORT_OUTPUT, getOut());
		
		//context keys specific to a flat report
		algorithm.addIn(IOKeys.DATA_COLS, intermediateDataCols); 
		algorithm.addIn(IOKeys.GROUP_COLS, intermediateGroupCols); 
		algorithm.addIn(IOKeys.SHOW_TOTALS, Boolean.valueOf(getShowTotals())); 
		algorithm.addIn(IOKeys.SHOW_GRAND_TOTAL, Boolean.valueOf(getShowGrandTotal()));
		//algorithm.addIn(IOKeys.ORIGINAL_CT_DATA_COLS_COUNT, originalDataColsCount); 
    	//algorithm.addIn(IOKeys.ORIGINAL_CT_GROUP_COLS_COUNT, originalGroupColsCount); 
		algorithm.addIn(IOKeys.CROSSTAB_HEADER_ROWS, getCrosstabHeaderRows()); 
		algorithm.addIn(IOKeys.CROSSTAB_DATA, crosstabData); 
		
		//initial steps 
		algorithm.addInitStep(new OpenReportIOInitStep()); 
		algorithm.addInitStep(new FlatReportExtractTotalsDataInitStep());
		algorithm.addInitStep(new StartReportInitStep()); 
    	//only for debug
    	//algorithm.addInitStep(new ColumnHeaderOutputInitStep("Intermediate report"));
        
    	//main steps
    	algorithm.addMainStep(new DistinctValuesDetectorStep());
    	//algorithm.addMainStep(new ComputeColumnValuesStep());
    	algorithm.addMainStep(new GroupLevelDetectorStep());
    	
    	//only for debug if( getShowTotals() || getShowGrandTotal()) algorithm.addMainStep(new FlatReportTotalsOutputStep());
    	
    	algorithm.addMainStep(new IntermedRowMangerStep());
    	
    	if(getShowTotals() || getShowGrandTotal()){
    		algorithm.addMainStep(new TotalsCalculatorStep());
    	}
    	
    	//only for debug algorithm.addMainStep(new DataRowsOutputStep());
    	
    	if( intermediateGroupCols.size() > 0){
    		algorithm.addMainStep(new PreviousRowManagerStep());
    	}
    	
    	algorithm.addExitStep(new EndReportExitStep()); 
    	algorithm.addExitStep(new CloseReportIOExitStep()); 
	}

	public List<? extends CrosstabHeaderRow> getCrosstabHeaderRows() {
		return crosstabHeaderRowsAsList;
	}
	
	/**
	 * 
	 * @param crosstabHeaderRows
	 * @deprecated
	 */
//	public void setCrosstabHeaderRows(CrosstabHeaderRow[] crosstabHeaderRows) {
//		this.crosstabHeaderRowsAsList = Arrays.asList(crosstabHeaderRows);
//	}
	
	/**
	 * 
	 * @param crosstabHeaderRows
	 */
	public void setCrosstabHeaderRows(List<? extends CrosstabHeaderRow> crosstabHeaderRows){
		this.crosstabHeaderRowsAsList = crosstabHeaderRows; 
	}
	
	public CrosstabData getCrosstabData() {
		return crosstabData;
	}

	public void setCrosstabData(CrosstabData crosstabData) {
		this.crosstabData = crosstabData;
	}
	
	
//	protected List<GroupColumn> transformGroupingCrosstabConfigInFlatReportConfig(	List<? extends GroupColumn> originalCtGroupingCols, 
//																					List<? extends DataColumn> originalCtDataCols, 
//																					List<? extends CrosstabHeaderRow> originalCtHeaderRows){
//		
//		int originalGroupColsLength = originalCtGroupingCols != null ? originalCtGroupingCols.size(): 0;
//		int originalDataColsLength = originalCtDataCols != null ? originalCtDataCols.size() : 0 ; 
//		
//		LOGGER.debug("transforming grouping crosstab config into flat intermediary report: ");
//		LOGGER.debug("origCtGroupingCols={}", originalCtGroupingCols);
//		LOGGER.debug("originalCtDataRows={}", originalCtDataCols);
//		LOGGER.debug("originalCtHeaderRows={}",originalCtHeaderRows);
//		
//		int intermedGroupColsLength = originalGroupColsLength + originalDataColsLength + originalCtHeaderRows.size() -1;
//		List<GroupColumn> result = new ArrayList<GroupColumn>(intermedGroupColsLength);
//		
//		//from 0 to original groupCols.length we copy the original group columns
//		if(originalGroupColsLength > 0){
//			//System.arraycopy(originalCtGroupingCols, 0, result, 0, originalCtGroupingCols.size());
//			for(int i=0; i<originalCtGroupingCols.size(); i++){
//				//result[i] = originalCtGroupingCols.get(i);
//				result.add(originalCtGroupingCols.get(i));
//			}
//		}
//		
//		//from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
//		for(int i=0; i < originalDataColsLength; i++){
//			//result[originalGroupColsLength+i] = new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i);
//			result.add(new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i));
//		}
//		
//		//then we copy the header rows (of course transformed as groupCols)
//		//we don't need any grouping for the last header row (that's why we have headerRows.length-1 below
//		for (int i = 0; i < originalCtHeaderRows.size()-1; i++) {
//			//result[originalGroupColsLength+originalDataColsLength+i] =
//			result.add(
//					new IntermGroupColFromHeaderRow(	originalCtHeaderRows.get(i), 
//														originalGroupColsLength+originalDataColsLength+i));
//		}
//		
//		return result; 
//	}
	
//	protected static class IntermDataColumnFromCrosstabData implements DataColumn{
//		
//		private CrosstabData crosstabData;
//		
//		public IntermDataColumnFromCrosstabData(CrosstabData crosstabData){
//			this.crosstabData = crosstabData; 
//		}
//		
//		public String getHeader() {
//			return "CrosstabDataColumn"; //normally this should never be called (except when debugging reports)
//		}
//
//		public String getFormattedValue(Object value) {
//			return crosstabData.getFormattedValue(value);
//		}
//
//		public Object getValue(NewRowEvent newRowEvent) {
//			return crosstabData.getValue(newRowEvent);
//		}
//
//		public Calculator getCalculator() {
//			return crosstabData.getCalculator();
//		}
//
//		public HorizAlign getHorizAlign() {
//			return crosstabData.getHorizAlign(); 
//		}
//
//		public int getOrderLevel() {
//			return -1; 
//		}
//
//		public int getOrderType() {
//			return -1; 
//		}
//	}
//	
//	protected static class IntermGroupColFromCtDataCol implements GroupColumn{
//		
//		private DataColumn dataColumn; 
//		private int groupingLevel; 
//		
//		public IntermGroupColFromCtDataCol(DataColumn dataColumn, int groupLevel){
//			this.dataColumn = dataColumn; 
//			this.groupingLevel = groupLevel; 
//		}
//		
//		public String getFormattedValue(Object object) {
//			return dataColumn.getFormattedValue(object); 
//		}
//
//		public int getGroupingLevel() {
//			return groupingLevel;
//		}
//
//		public String getHeader() {
//			return dataColumn.getHeader(); 
//		}
//
//		public Object getValue(NewRowEvent newRowEvent) {
//			return dataColumn.getValue(newRowEvent);
//		}
//
//		public HorizAlign getHorizAlign() {
//			return dataColumn.getHorizAlign(); 
//		}
//		
//		public boolean showDuplicates(){
//			return false; //TODO: check if this is used
//		}
//		
//	}
//	
//	protected static class IntermGroupColFromHeaderRow implements GroupColumn {
//
//		private CrosstabHeaderRow headerRow; 
//	
//		private int groupingLevel = -1; 
//	
//		public IntermGroupColFromHeaderRow(CrosstabHeaderRow headerRow, int groupingLevel){
//			this.headerRow = headerRow; 
//			this.groupingLevel = groupingLevel;
//		}
//	
//		/* (non-Javadoc)
//		 * @see net.sf.reportengine.config.GroupColumn#getHeader()
//		 */
//		public String getHeader() {
//			return "not used";
//		}
//	
//	
//		/* (non-Javadoc)
//		 * @see net.sf.reportengine.config.GroupColumn#getGroupingLevel()
//		 */
//		public int getGroupingLevel() {
//			return groupingLevel;
//		}
//	
//		/* (non-Javadoc)
//		 * @see net.sf.reportengine.config.GroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
//		 */
//		public Object getValue(NewRowEvent newRowEvent) {
//			return headerRow.getValue(newRowEvent);
//		}
//	
//		/* (non-Javadoc)
//		 * @see net.sf.reportengine.config.GroupColumn#getFormattedValue(java.lang.Object)
//		 */
//		public String getFormattedValue(Object object) {
//			String result = "";
//			if(object != null){
//				result = object.toString();
//				//TODO: come back here and return a formatted value
//			}
//			return result; 
//		}
//
//		public HorizAlign getHorizAlign() {
//			return HorizAlign.CENTER; //TODO: check if this is used
//		}
//		
//		public boolean showDuplicates(){
//			return false; //this is not used
//		}
//
//}

}
