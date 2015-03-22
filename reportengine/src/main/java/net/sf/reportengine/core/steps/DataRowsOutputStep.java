/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.ContextKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 *  Output step used mainly on Flat reports
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class DataRowsOutputStep extends AbstractReportStep<String,Integer,String> {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(DataRowsOutputStep.class);
	
    /**
     * 
     */
	private int finalReportGroupCount = -1;
	private List<GroupColumn> groupCols = null;
	private List<DataColumn> dataColumns = null; 	
	
	
	/**
	 * this step's init method
	 */
	public StepResult<String> init(StepInput stepInput){
		groupCols = getGroupColumns(stepInput);
		dataColumns = getDataColumns(stepInput);
		finalReportGroupCount = getGroupColumnsCount(stepInput);
		return StepResult.NO_RESULT; 
	}
    
	/**
     * Constructs a cell for each value and sends it to output
     */
    public StepResult<Integer> execute(NewRowEvent newRowEvent, StepInput stepInput) {
    	Object[] previousRowGrpValues = getPreviousRowOfGroupValues(stepInput);
		
		//start the row
    	getReportOutput(stepInput).startDataRow(new RowProps(getDataRowCount(stepInput)));
		
		CellProps.Builder cellPropsBuilder = null;
		
		//handle the grouping columns first
		GroupColumn currentGrpCol = null; 
		for(int i=0; i<finalReportGroupCount; i++){
			currentGrpCol = groupCols.get(i);
			Object valueForCurrentColumn = currentGrpCol.getValue(newRowEvent);
			
			if(	currentGrpCol.showDuplicates() 
				|| previousRowGrpValues == null    //it's too early and we don't have prevGroupValues set
				|| getGroupingLevel(stepInput) > -1 			//immediately after a total row
				|| !valueForCurrentColumn.equals(previousRowGrpValues[i])//if this value is different from the prev
				){
				cellPropsBuilder = new CellProps.Builder(
						currentGrpCol.getFormattedValue(valueForCurrentColumn));
			}else{
				cellPropsBuilder = new CellProps.Builder(ReportOutput.WHITESPACE);
			}
			cellPropsBuilder.horizAlign(currentGrpCol.getHorizAlign())
							.vertAlign(currentGrpCol.getVertAlign())
							.rowNumber(getDataRowCount(stepInput)); 
			getReportOutput(stepInput).outputDataCell(cellPropsBuilder.build()); 
			
		}
		
		//then handle the data columns
		for(DataColumn dataColumn : dataColumns){
			Object valueForCurrentColumn = dataColumn.getValue(newRowEvent);
			cellPropsBuilder = new CellProps.Builder(dataColumn.getFormattedValue(valueForCurrentColumn))
				.horizAlign(dataColumn.getHorizAlign())
				.vertAlign(dataColumn.getVertAlign()); 
			
			getReportOutput(stepInput).outputDataCell(cellPropsBuilder.build()); 
		}
    	
		//end row
		getReportOutput(stepInput).endDataRow();
		
		//incrementDataRowNbr(stepInput);
		return new StepResult(ContextKeys.DATA_ROW_COUNT, getDataRowCount(stepInput)+1); 
    }
    
    public StepResult<String> exit(StepInput stepInput){
    	return StepResult.NO_RESULT; 
    }
}
