/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;
import net.sf.reportengine.util.IOKeys;

/**
 * <p>
 *  Output step used mainly on Flat reports
 * </p>
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class DataRowsOutputStep extends AbstractReportStep {
    
	private int finalReportGroupCount = -1;
	private List<GroupColumn> groupCols = null;
	private List<DataColumn> dataColumns = null; 	
	
	
	/**
	 * this step's init method
	 */
	public void executeInit(){
		groupCols = getGroupColumns();
		dataColumns = getDataColumns();
		finalReportGroupCount = getGroupColumnsCount(); 
	}
    
	/**
     * Constructs a cell for each value and sends it to output
     */
    public void execute(NewRowEvent newRowEvent) {
    	Object[] previousRowGrpValues = getPreviousRowOfGroupValues();
		
		//start the row
    	getReportOutput().startDataRow(new RowProps(getDataRowCount()));
		
		CellProps.Builder cellPropsBuilder = null;
		
		//handle the grouping columns first
		GroupColumn currentGrpCol = null; 
		for(int i=0; i<finalReportGroupCount; i++){
			currentGrpCol = groupCols.get(i);
			Object valueForCurrentColumn = currentGrpCol.getValue(newRowEvent);
			
			if(	currentGrpCol.showDuplicates() 
				|| previousRowGrpValues == null    //it's too early and we don't have prevGroupValues set
				|| getGroupingLevel() > -1 			//immediately after a total row
				|| !valueForCurrentColumn.equals(previousRowGrpValues[i])//if this value is different from the prev
				){
				cellPropsBuilder = new CellProps.Builder(
						currentGrpCol.getFormattedValue(valueForCurrentColumn));
			}else{
				cellPropsBuilder = new CellProps.Builder(ReportOutput.WHITESPACE);
			}
			cellPropsBuilder.horizAlign(currentGrpCol.getHorizAlign())
							.vertAlign(currentGrpCol.getVertAlign())
							.rowNumber(getDataRowCount()); 
			getReportOutput().outputDataCell(cellPropsBuilder.build()); 
		}
		
		//then handle the data columns
		for(DataColumn dataColumn : dataColumns){
			Object valueForCurrentColumn = dataColumn.getValue(newRowEvent);
			cellPropsBuilder = new CellProps.Builder(dataColumn.getFormattedValue(valueForCurrentColumn))
				.horizAlign(dataColumn.getHorizAlign())
				.vertAlign(dataColumn.getVertAlign()); 
			getReportOutput().outputDataCell(cellPropsBuilder.build()); 
		}
    	
		//end row
		getReportOutput().endDataRow();
		
		incrementDataRowNbr();
    }
}
