/**
 * 
 */
package net.sf.reportengine.core.steps.intermed;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.CrosstabData;
import net.sf.reportengine.config.CrosstabHeaderRow;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.steps.AbstractReportInitStep;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class ConfigIntermedColsInitStep extends AbstractReportInitStep{
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigIntermedColsInitStep.class);
	
	
	@Override protected void executeInit() {
		List<DataColumn> originalCtDataCols = getDataColumns(); 
		List<GroupColumn> originalCtGroupingCols = getGroupColumns(); 
		List<CrosstabHeaderRow> originalCtHeaderRows = 
				(List<CrosstabHeaderRow>)getAlgoInput().get(IOKeys.CROSSTAB_HEADER_ROWS);
		
		getAlgoContext().set(ContextKeys.INTERNAL_GROUP_COLS, 
				transformGroupingCrosstabConfigInFlatReportConfig(
											originalCtGroupingCols, 
											originalCtDataCols, 
											originalCtHeaderRows));		
		
		CrosstabData originalCtData = (CrosstabData)getAlgoInput().get(IOKeys.CROSSTAB_DATA); 
		getAlgoContext().set(ContextKeys.INTERNAL_DATA_COLS, 
				transformCrosstabDataIntoDataColumns(originalCtData));
	}
	
	/**
	 * 
	 * @param crosstabData
	 * @return
	 */
	private List<DataColumn> transformCrosstabDataIntoDataColumns(CrosstabData crosstabData){
		List<DataColumn> intermediateDataCols = new ArrayList<DataColumn>(1);
		intermediateDataCols.add(new IntermDataColumnFromCrosstabData(crosstabData)); 
		return intermediateDataCols; 
	}
	
	/**
	 * transforms the original grouping + data + header columns into intermediate group columns
	 * 
	 * 1. from 0 to original groupCols.length we copy the original group columns
	 * 2. from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
	 * 3. then we copy the header rows (of course transformed as groupCols)
	 * 
	 * @param originalCtGroupingCols
	 * @param originalCtDataCols
	 * @param originalCtHeaderRows
	 * @return
	 */
	List<GroupColumn> transformGroupingCrosstabConfigInFlatReportConfig(	List<? extends GroupColumn> originalCtGroupingCols, 
																			List<? extends DataColumn> originalCtDataCols, 
																			List<? extends CrosstabHeaderRow> originalCtHeaderRows){
		
		int originalGroupColsLength = originalCtGroupingCols != null ? originalCtGroupingCols.size(): 0;
		int originalDataColsLength = originalCtDataCols != null ? originalCtDataCols.size() : 0 ; 
		
		LOGGER.debug("transforming grouping crosstab config into flat intermediary report: ");
		LOGGER.debug("origCtGroupingCols={}", originalCtGroupingCols);
		LOGGER.debug("originalCtDataRows={}", originalCtDataCols);
		LOGGER.debug("originalCtHeaderRows={}",originalCtHeaderRows);
		
		int intermedGroupColsLength = originalGroupColsLength + originalDataColsLength + originalCtHeaderRows.size() -1;
		List<GroupColumn> result = new ArrayList<GroupColumn>(intermedGroupColsLength);
		
		//from 0 to original groupCols.length we copy the original group columns
		if(originalGroupColsLength > 0){
			//System.arraycopy(originalCtGroupingCols, 0, result, 0, originalCtGroupingCols.size());
			for(int i=0; i<originalCtGroupingCols.size(); i++){
				//result[i] = originalCtGroupingCols.get(i);
				result.add(originalCtGroupingCols.get(i));
			}
		}
		
		//from groupCols.length to groupCols.lenght + dataCols.length we copy construct group columns from data columns
		for(int i=0; i < originalDataColsLength; i++){
			//result[originalGroupColsLength+i] = new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i);
			result.add(new IntermGroupColFromCtDataCol(originalCtDataCols.get(i), originalGroupColsLength+i));
		}
		
		//then we copy the header rows (of course transformed as groupCols)
		//we don't need any grouping for the last header row (that's why we have headerRows.length-1 below
		for (int i = 0; i < originalCtHeaderRows.size()-1; i++) {
			//result[originalGroupColsLength+originalDataColsLength+i] =
			result.add(
					new IntermGroupColFromHeaderRow(	originalCtHeaderRows.get(i), 
														originalGroupColsLength+originalDataColsLength+i));
		}
		
		return result; 
	}
	
	static class IntermDataColumnFromCrosstabData implements DataColumn{
		
		private CrosstabData crosstabData;
		
		public IntermDataColumnFromCrosstabData(CrosstabData crosstabData){
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

		public Calculator getCalculator() {
			return crosstabData.getCalculator();
		}

		public HorizAlign getHorizAlign() {
			return crosstabData.getHorizAlign(); 
		}

		public int getOrderLevel() {
			return -1; 
		}

		public int getOrderType() {
			return -1; 
		}
	}
	
	static class IntermGroupColFromCtDataCol implements GroupColumn{
		
		private DataColumn dataColumn; 
		private int groupingLevel; 
		
		public IntermGroupColFromCtDataCol(DataColumn dataColumn, int groupLevel){
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

		public HorizAlign getHorizAlign() {
			return dataColumn.getHorizAlign(); 
		}
		
		public boolean showDuplicates(){
			return false; //TODO: check if this is used
		}
		
	}
	
	static class IntermGroupColFromHeaderRow implements GroupColumn {

		private CrosstabHeaderRow headerRow; 
	
		private int groupingLevel = -1; 
	
		public IntermGroupColFromHeaderRow(CrosstabHeaderRow headerRow, int groupingLevel){
			this.headerRow = headerRow; 
			this.groupingLevel = groupingLevel;
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getHeader()
		 */
		public String getHeader() {
			return "not used";
		}
	
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getGroupingLevel()
		 */
		public int getGroupingLevel() {
			return groupingLevel;
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
		 */
		public Object getValue(NewRowEvent newRowEvent) {
			return headerRow.getValue(newRowEvent);
		}
	
		/* (non-Javadoc)
		 * @see net.sf.reportengine.config.GroupColumn#getFormattedValue(java.lang.Object)
		 */
		public String getFormattedValue(Object object) {
			String result = "";
			if(object != null){
				result = object.toString();
				//TODO: come back here and return a formatted value
			}
			return result; 
		}

		public HorizAlign getHorizAlign() {
			return HorizAlign.CENTER; //TODO: check if this is used
		}
		
		public boolean showDuplicates(){
			return false; //this is not used
		}

}
	
}
