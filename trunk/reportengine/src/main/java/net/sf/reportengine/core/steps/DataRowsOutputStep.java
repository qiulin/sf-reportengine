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
	public void init(Map<IOKeys, Object> algoInput, AlgoContext context){
		super.init(algoInput, context);
		
		groupCols = getGroupColumns();
		dataColumns = getDataColumns();
		
		finalReportGroupCount = groupCols != null ? groupCols.size() : 0;
	}
    
	/**
     * execute. Constructs a cell for each value and sends it to output
     */
    public void execute(NewRowEvent newRowEvent) {
    	ReportOutput output = getReportOutput();
    	Object[] previousRowGrpValues = getPreviousRowOfGroupingValues();
		Integer dataRowCount = getDataRowCount();
		Object valueForCurrentColumn = null; 
		CellProps.Builder cellPropsBuilder = null; 
		
		//start the row
		output.startDataRow(new RowProps(dataRowCount));
		
		//handle the grouping columns first
		GroupColumn currentGrpCol = null; 
		for(int i=0; i<finalReportGroupCount; i++){
			currentGrpCol = groupCols.get(i);
			valueForCurrentColumn = currentGrpCol.getValue(newRowEvent);
			
			if(	currentGrpCol.showDuplicates() 
				|| previousRowGrpValues == null 
				|| !valueForCurrentColumn.equals(previousRowGrpValues[i])){
				cellPropsBuilder = new CellProps.Builder(currentGrpCol.getFormattedValue(valueForCurrentColumn));
			}else{
				cellPropsBuilder = new CellProps.Builder(ReportOutput.WHITESPACE);
			}
			cellPropsBuilder.horizAlign(currentGrpCol.getHorizAlign());
			cellPropsBuilder.rowNumber(dataRowCount); 
			output.outputDataCell(cellPropsBuilder.build()); 
		}
		
		//then handle the data columns
		for(DataColumn dataColumn : dataColumns){
			valueForCurrentColumn = dataColumn.getValue(newRowEvent);
			cellPropsBuilder = new CellProps.Builder(dataColumn.getFormattedValue(valueForCurrentColumn));
			cellPropsBuilder.horizAlign(dataColumn.getHorizAlign());
			output.outputDataCell(cellPropsBuilder.build()); 
		}
    	
		//end row
		output.endDataRow();
		
		incrementDataRowNbr();
    }
}
