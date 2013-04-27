/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.ContextKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This is the manager of the intermediate crosstab row. 
 * The intermediate crosstab row holds CrosstabData (and some other useful info) until
 * the grouping level is changed. At this moment the intermediate manager 
 * is resetting the intermediate row. 
 * 
 * @author dragos balan (dragos dot bala at gmail dot com)
 * @since 0.4
 */
public class IntermediateCrosstabRowMangerStep extends AbstractCrosstabStep {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(IntermediateCrosstabRowMangerStep.class);
	
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
	public void init(ReportContext context){
		super.init(context);
		context.set(ContextKeys.INTERMEDIATE_ROW, intermediateRow);
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.AbstractReportStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	@Override
	public void execute(NewRowEvent rowEvent) {
		IntermediateDataInfo currentCrosstabDataInfo = getIntermediateCrosstabDataInfo(); 
		int groupingLevel = getGroupingLevel(); 
		int originalGroupColsLength = getOriginalCrosstabGroupingColsLength();
		int originalDataColsLength = getOriginalCrosstabDataColsLength(); 
		
		if(groupingLevel >= 0){
			//if grouping level changed
			
			Calculator[][] calculatorMatrix = getCalculatorMatrix(); 
			
			if(groupingLevel < originalGroupColsLength + originalDataColsLength){
				//this is a change in the original group so
				
				//First we update all remaining totals (if the report contains totals)
				if(getShowTotals() || getShowGrandTotal()){
					//we don't need all totals. From the groupingColumns we take only the first one
					updateIntermediateTotals(	originalGroupColsLength+originalDataColsLength-1, 
												getGroupingColumnsLength() , 
												calculatorMatrix);
				}
				//Second: we display the intermediate row
				addOriginalGroupAndDataColumnsInfoToIntermRow(intermediateRow);
				writeIntermediateRow(intermediateRow);
				
				//Third: reset the array
				intermediateRow.emptyRow(); 
			}else{
				//if grouping level changed for the crosstabHeaderRows 
				
				if(getShowTotals()||getShowGrandTotal()){
					updateIntermediateTotals(	groupingLevel, 				//from the current grouping level 
												getGroupingColumnsLength(), //to the last intermediate grouping col
												calculatorMatrix);
				}
			}
		}
		
		//and finally for each row we add new conctructed data 
		intermediateRow.addIntermComputedData(currentCrosstabDataInfo);
	}
	
	
	/**
	 * 
	 */
	public void exit(ReportContext context){
		if(getShowTotals() || getShowGrandTotal()){
			int originalGroupingColsLength = getOriginalCrosstabGroupingColsLength();  
			int originalDataColsLength = getOriginalCrosstabDataColsLength(); 
			updateIntermediateTotals(	originalGroupingColsLength + originalDataColsLength-1, //from the last original grouping col
										getGroupingColumnsLength() ,  //to the last intermediate grouping col (containing also the headers)
										getCalculatorMatrix());
		}
		
		intermediateRow.setLast(true); 
		addOriginalGroupAndDataColumnsInfoToIntermRow(intermediateRow);
		writeIntermediateRow(intermediateRow); 
		//the cleanup
		intermediateRow = null;
		//flushObjectOutputStream(); 
		super.exit(context);
	}
	
	
	private void updateIntermediateTotals(	int levelFrom, 
											int levelTo, 
											Calculator[][] calculatorMatrix){
		int calculatorMatrixRow = -1;
		Object calculatorResult = null; 
		int tmpLevelFrom = getOriginalCrosstabGroupingColsLength()+ getOriginalCrosstabDataColsLength(); 
		for (int tempGrpLevel = levelFrom; tempGrpLevel < levelTo; tempGrpLevel++) {
			calculatorMatrixRow = computeCalcRowNumberForAggLevel(tempGrpLevel); //getGroupingColumnsLength() - tempGrpLevel -1; 
			Object[] totalStrings = getTotalStringForGroupingLevelAndPredecessors(tmpLevelFrom, tempGrpLevel);
			int[] position = getPositionOfTotal(tmpLevelFrom, tempGrpLevel);
			
			//our intermediate report has only one column containing calculators 
			//(the column containing crosstab data) therefore we have only one column 
			//in the calculator matrix (see the zero below)
			calculatorResult = calculatorMatrix[calculatorMatrixRow][0].getResult(); 
			intermediateRow.addIntermTotalsInfo(new IntermediateTotalInfo(	calculatorResult, 
																			position, 
																			totalStrings));
		}
	}
	
	
	private void addOriginalGroupAndDataColumnsInfoToIntermRow(IntermediateReportRow intermediateRow){
		//only for debug ReportOutput output = getOutput(); 
		//only for debug output.startRow();
		//only for debug output.output(new CellProps("Intermediate row:"));
		Integer originalGroupingValuesLength = getOriginalCrosstabGroupingColsLength();
		Integer originalDataValuesLength = getOriginalCrosstabDataColsLength(); 
		Object[] previousGroupValues = getPreviousRowOfGroupingValues(); 
		
		LOGGER.debug("first: adding {} grouping values to intermediate row ", originalGroupingValuesLength); 
		//although we have more values in the previous grouping values we display only the original ones
		//because they are further needed in the second iteration
		for (int i=0; i<originalGroupingValuesLength; i++) {
			//only for debug output.output(new CellProps(previousGroupValues[i]));
			intermediateRow.addOrigGroupValue(previousGroupValues[i]);
		}
		LOGGER.debug("second: adding {} data values to intermediate row", originalDataValuesLength);
		for(int i=0; i<originalDataValuesLength; i++){
			Object prevValue = previousGroupValues[originalGroupingValuesLength+i];
			intermediateRow.addOrigDataColValue(prevValue);
		}
		
		/*only for debug
		for (IntermediateDataInfo element : intermediateRow.getIntermediateDataRow().getDataList()) {
			output.output(new CellProps(element.toString()));
		}
		
		for (IntermediateTotalInfo totalInfo : intermediateRow.getIntermediateTotals().getTotalsDataList()) {
			output.output(new CellProps(totalInfo.toString()));
		}
		output.endRow(); 
		*/
	}
	
//	private void writeIntermediateRow(IntermediateReportRow intermediateRow){
//		//serialize
//		try {
//			if(logger.isDebugEnabled()){
//				logger.debug("writting object to intermediate object stream "+intermediateRow);
//			}
//			objectOutputStream.writeObject(intermediateRow);
//			objectOutputStream.reset(); 
//		} catch (IOException e) {
//			throw new ReportEngineRuntimeException(e);
//		}
//	}
	
	private void writeIntermediateRow(IntermediateReportRow intermediateRow){
		ReportOutput output = getOutput(); 
		output.startDataRow(new RowProps()); 
		output.outputDataCell(new CellProps.Builder(intermediateRow)
							.colspan(4) /*this is not taken into account except when debug*/
							.build()); 
		output.endDataRow(); 
	}
	
//	private void flushObjectOutputStream(){
//		try {
//			objectOutputStream.flush();
//			objectOutputStream.close(); 
//		} catch (IOException e) {
//			throw new ReportEngineRuntimeException(e); 
//		} 
//	}
}
