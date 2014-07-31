/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.ReportUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the manager of the intermediate crosstab row. 
 * The intermediate crosstab row holds CrosstabData (and some other useful info) until
 * the grouping level is changed. At this moment the intermediate manager 
 * is resetting the intermediate row. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class IntermedRowMangerStep extends AbstractCrosstabStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IntermedRowMangerStep.class);
	
	/**
	 * this is an intermediate line containing values ( plus values meta-data like position of the value relative to headerrows values). 
	 * and totals ( plus totals metadata)
	 * The array is refreshed only when the grouping level is changed
	 *
	 */
	private IntermediateReportRow intermediateRow = new IntermediateReportRow(); 
	
	
	/**
	 * 
	 */
	@Override
	protected void executeInit(){
		getAlgoContext().set(ContextKeys.INTERMEDIATE_ROW, intermediateRow);
	}
	
//	@Override public ReportOutput getReportOutput(){
//		return (ReportOutput)getAlgoContext().get(ContextKeys.INTERMEDIATE_OUTPUT); 
//	}
	
	private int getIntermGroupColsLength(){
		return ((List<GroupColumn>)getAlgoContext().get(ContextKeys.INTERNAL_GROUP_COLS)).size(); 
	}
	
	private int getIntermDataColsLength(){
		return ((List<DataColumn>)getAlgoContext().get(ContextKeys.INTERNAL_DATA_COLS)).size(); 
	}
	
	/**
     * computes the row number (from the calculators matrix) where the totals are for the given level
     * @param level		the aggregation level
     * @return
     */
    @Override public int computeCalcRowNumberForAggLevel(int level){
    	return getIntermGroupColsLength() - level -1;
    }
    
    /**
     * computes the aggregation level for the given row of the calculators matrix
     * @param calcRowNumber
     * @return
     */
    @Override public int computeAggLevelForCalcRowNumber(int calcRowNumber){
    	return getIntermGroupColsLength() - calcRowNumber - 1;
    }
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public void execute(NewRowEvent rowEvent) {
		//TODO: try to simplify this class (it is pretty complex as compared to the other step classes)
		int groupingLevel = getGroupingLevel(); 
		
		if(groupingLevel >= 0){
			//if grouping level changed
			
			CalcIntermResult[][] calcResults = getCalcIntermResultsMatrix(); 
			int originalGroupColsLength = getGroupColumnsCount();//getOriginalCrosstabGroupingColsLength();  
			int originalDataColsLength = getDataColumnsLength(); //getOriginalCrosstabDataColsLength();
			
			if(groupingLevel < originalGroupColsLength + originalDataColsLength){
				//this is a change in the original group so...
				
				//First we update all remaining totals (if the report contains totals)
				if(getShowTotals() || getShowGrandTotal()){
					//we don't need all totals. From the groupingColumns we take only the first one
					updateIntermediateTotals(	originalGroupColsLength+originalDataColsLength-1, 
												getIntermGroupColsLength(), //getGroupColumnsLength() , 
												calcResults);
				}
				//Second: we display the intermediate row
				addOriginalGroupAndDataColumnsInfoToIntermRow(intermediateRow);
				writeIntermediateRow(intermediateRow);
				
				//Third: reset the array
				intermediateRow.emptyRow(); 
			}else{
				//if grouping level changed for the crosstabHeaderRows 
				
				if(getShowTotals() || getShowGrandTotal()){
					updateIntermediateTotals(	groupingLevel, 				//from the current grouping level 
												getIntermGroupColsLength(), //to the last intermediate grouping col
												calcResults);
				}
			}
		}
		//and finally for each row we add new constructed data 
		intermediateRow.addIntermComputedData(getIntermediateCrosstabDataInfo());
	}
	
	
	/**
	 * 
	 */
	public void exit(){
		if(getShowTotals() || getShowGrandTotal()){
			int originalGroupingColsLength = getGroupColumnsCount(); //getOriginalCrosstabGroupingColsLength();  
			int originalDataColsLength = getDataColumnsLength(); //getOriginalCrosstabDataColsLength(); 
			updateIntermediateTotals(	originalGroupingColsLength + originalDataColsLength-1, //from the last original grouping col
										getIntermGroupColsLength(),//getGroupColumnsLength() ,  //to the last intermediate grouping col (containing also the headers)
										getCalcIntermResultsMatrix());
		}
		
		intermediateRow.setLast(true); 
		addOriginalGroupAndDataColumnsInfoToIntermRow(intermediateRow);
		writeIntermediateRow(intermediateRow); 
		intermediateRow = null;//clean up
	}
	
	
	private void updateIntermediateTotals(	int levelFrom, 
											int levelTo, 
											CalcIntermResult[][] calcResults){
		int calculatorMatrixRow = -1;
		Object calculatorResult = null; 
		//int tmpLevelFrom = getOriginalCrosstabGroupingColsLength()+ getOriginalCrosstabDataColsLength();
		int tmpLevelFrom = getGroupColumnsCount()+ getDataColumnsLength(); 
		
		for (int tempGrpLevel = levelFrom; tempGrpLevel < levelTo; tempGrpLevel++) {
			calculatorMatrixRow = computeCalcRowNumberForAggLevel(tempGrpLevel); //getGroupingColumnsLength() - tempGrpLevel -1; 
			Object[] totalStrings = getTotalStringForGroupingLevelAndPredecessors(tmpLevelFrom, tempGrpLevel);
			int[] position = getPositionOfTotal(tmpLevelFrom, tempGrpLevel);
			
			//our intermediate report has only one column containing calculators 
			//(the column containing crosstab data) therefore we have only one column 
			//in the calculator matrix (see the zero below)
			calculatorResult = calcResults[calculatorMatrixRow][0].getResult(); 
			intermediateRow.addIntermTotalsInfo(new IntermediateTotalInfo(	calculatorResult, 
																			position, 
																			totalStrings));
		}
	}
	
	
	private void addOriginalGroupAndDataColumnsInfoToIntermRow(IntermediateReportRow intermediateRow){
		if(ReportUtils.DEBUG){
			getReportOutput().startDataRow(new RowProps());
			getReportOutput().outputDataCell(new CellProps.Builder("Intermediate row:").build());
		}
		
		Integer originalGroupingValuesLength = getGroupColumnsCount(); //getOriginalCrosstabGroupingColsLength();
		Integer originalDataValuesLength = getDataColumnsLength(); //getOriginalCrosstabDataColsLength(); 
		Object[] previousGroupValues = getPreviousRowOfGroupValues(); 
		
		LOGGER.debug("first: adding {} grouping values to intermediate row ", originalGroupingValuesLength); 
		//although we have more values in the previous grouping values we display only the original ones
		//because they are further needed in the second iteration
		for (int i=0; i<originalGroupingValuesLength; i++) {
			if(ReportUtils.DEBUG){
				getReportOutput().outputDataCell(new CellProps.Builder(previousGroupValues[i]).build());
			}
			intermediateRow.addOrigGroupValue(previousGroupValues[i]);
		}
		LOGGER.debug("second: adding {} data values to intermediate row", originalDataValuesLength);
		for(int i=0; i<originalDataValuesLength; i++){
			Object prevValue = previousGroupValues[originalGroupingValuesLength+i];
			intermediateRow.addOrigDataColValue(prevValue);
		}
		
		if(ReportUtils.DEBUG){
			for (IntermediateDataInfo element : intermediateRow.getIntermComputedDataList().getDataList()) {
				getReportOutput().outputDataCell(new CellProps.Builder(element.toString()).build());
			}
		
			for (IntermediateTotalInfo totalInfo : intermediateRow.getIntermComputedTotalsList().getTotalsDataList()) {
				getReportOutput().outputDataCell(new CellProps.Builder(totalInfo.toString()).build());
			}
			getReportOutput().endDataRow(); 
		}
	}
	
	
	private void writeIntermediateRow(IntermediateReportRow intermediateRow){
		ReportOutput output = getReportOutput(); 
		output.startDataRow(new RowProps()); 
		output.outputDataCell(new CellProps.Builder(intermediateRow)
							.colspan(4) /*this is not taken into account except when debug*/
							.build()); 
		output.endDataRow(); 
	}
	
	
}
