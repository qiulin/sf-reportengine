/*
 * Created on 24.09.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.out.RowProps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * this step is responsible with outputting totals on y axis (normal totals) 
 * 
 * a		b	15	16	17
 * a		b	8	4	3
 * Total b		23	20	20
 * Total a		45	56	78
 * Grand Total	345	456	789
 * 
 * Please keep in mind that this step IS AND SHOULD BE CALLED BEFORE computing the new totals 
 * for the current row
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class FlatReportTotalsOutputStep extends AbstractReportStep {
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(FlatReportTotalsOutputStep.class);
	
	/**
	 * "Grand " label. To be used in front of Total, Average, Count etc. 
	 */
	public static final String GRAND_TOTAL_STRING = "Grand ";
   
    
    /**
     * local copy of the group columns
     */
    private List<GroupColumn> groupCols; 
    
    /**
     * local copy of the data columns
     */
    private List<DataColumn> dataCols;
    
    /**
     * 
     */
    private int[] distribOfCalculatorsInDataColsArray; 
    
    /**
     * the label of the calculators with whitespaces between
     */
    private String calcLabels; 
    
    /**
     * 
     */
    public FlatReportTotalsOutputStep(){
    	//empty constructor
    	
    }
    
    /**
     * init method 
     */
    @Override
    protected void executeInit(){
    	groupCols = getGroupColumns();
    	dataCols = getDataColumns(); 
    	distribOfCalculatorsInDataColsArray = getCalculatorDistributionInColumnDataArray(); 
    	calcLabels = getLabelsForAllCalculators(dataCols); 
    	
    	LOGGER.trace("The FlatReportTotalsOutputStep has been initialized. The distribution array is null {}", (distribOfCalculatorsInDataColsArray == null));
    }
    
    /**
     * execute 
     */
    public void execute(NewRowEvent rowEvent) {
        int groupLevel = getGroupingLevel();
        
        //when non simple data row 
        if( groupLevel >= 0 && getShowTotals()){
        	int totalRowEnd = computeCalcRowNumberForAggLevel(groupLevel);
        	//TODO: this operation is the opposite of computeAggLevelForCalcRowNumber which is 
        	//called inside outputTotalRowsFromTo. One of them should be deleted.
        	
        	//output totals from level 0 to current grouping level
        	outputTotalRowsFromTo(0, totalRowEnd);            
        }else{
        	LOGGER.trace("not displaying totals because current level is {}", groupLevel);
        }
     }

    /**
     * exit displays the last totals in the calculator matrix buffer and the grand total
     */
    @Override
    public void exit() {
        CalcIntermResult[][] calcIntermResults = getCalcIntermResultsMatrix();
        
        if(groupCols != null && getShowTotals()){
        	//calculators.length-2 because for levelCalculators.length-1 is a separate call
        	//(a call for Grand total see below)
        	outputTotalRowsFromTo(0, calcIntermResults.length-2);
        }
        
        //now the grand total
        if(getShowGrandTotal()){
        	//outputTotalsRow("Grand Total", calculators[calculators.length-1]);
        	outputTotalsRow(GRAND_TOTAL_GROUPING_LEVEL, 
        					calcIntermResults[calcIntermResults.length-1]
        					);	
        }
    }
    
    /**
     * outputs the totals existing on the specified rows starting with rowStart
     * and ending with rowEnnd (inclusive)
     * 
     * @param rowStart		the first row to start outputting 
     * @param rowEnd  		the last row to output
     */
    private void outputTotalRowsFromTo(int rowStart, int rowEnd){
    	LOGGER.trace("output totals from {} to {}", rowStart, rowEnd);
    	CalcIntermResult[][] calcResults = getCalcIntermResultsMatrix();
        for(int row = rowStart; row <= rowEnd ; row++){
        	//based on the row we can compute the aggregation level so that we can determine the 
        	// column to use from the previous data row
        	int aggLevel = computeAggLevelForCalcRowNumber(row);
        	
        	outputTotalsRow(aggLevel, calcResults[row]);
        	        	
        }
    }
    
    /**
     * 
     * @param groupLevel
     * @param calcResultForCurrentGrpLevel
     */
    private void outputTotalsRow(	int groupLevel, 
    								CalcIntermResult[] calcResultForCurrentGrpLevel){
    	
    	ReportOutput output = getReportOutput();
    	output.startDataRow(new RowProps(getDataRowCount()));
    	
    	if(	groupCols != null && groupCols.size() > 0){
    		//prepare and output the Total column
    		String totalString = getTotalStringForGroupingLevel(calcLabels, groupLevel);
    		output.outputDataCell(new CellProps.Builder(totalString)
    							.horizAlign(HorizAlign.LEFT)
    							.vertAlign(VertAlign.MIDDLE)
    							.rowNumber(getDataRowCount())
    							.build());
    		
    		if(groupCols.size() > 1){
    			//for all other grouping columns put white spaces 
    			//(groupColumns.length-1 colspan because the first column was already 
    			//filled with the word "Total xxxx"
    			
    			//if you want a single cell spanning multiple rows un-comment below
    			//output.output(new CellProps.Builder(ReportOutput.WHITESPACE)
    			//							.colspan(groupCols.size()-1) 
    			//							.build());
    			
    			//this is to display an empty cell for every remaining group column
    			for(int i=1; i<groupCols.size(); i++){
    				output.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE)
    													.rowNumber(getDataRowCount())
    													.build()); 
    			}
    		}
        }
        
    	String formattedResult = null; 
    	
        //then iterate over data columns to display the totals for those having calculators
    	for (int i = 0; i < dataCols.size(); i++) {
			DataColumn column = dataCols.get(i);
			if(column.getCalculator() != null){
				int calculatorIndex = distribOfCalculatorsInDataColsArray[i];
				Object calculatorResult = calcResultForCurrentGrpLevel[calculatorIndex].getResult();
				
				//format the computed value 
				formattedResult = dataCols.get(i).getFormattedTotal(calculatorResult); 
				
				output.outputDataCell(new CellProps.Builder(formattedResult)
									.horizAlign(dataCols.get(i).getHorizAlign())
									.vertAlign(dataCols.get(i).getVertAlign())
									.rowNumber(getDataRowCount())
									.build());
			}else{
				//if the column doesn't have a calculator associated 
				//then display an empty value (whitespace) with col span 1
				output.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE)
													.rowNumber(getDataRowCount())
													.build());
			}
		}
    	output.endDataRow();
    	incrementDataRowNbr(); 
    }
    
    private String getLabelsForAllCalculators(List<DataColumn> dataColumns){
    	StringBuilder result = new StringBuilder(); 
    	for (DataColumn dataColumn : dataColumns) {
			if(dataColumn.getCalculator() != null){
				result.append(dataColumn.getCalculator().getLabel()).append(" ") ;
			}
		}
    	return result.toString(); 
    }
}
