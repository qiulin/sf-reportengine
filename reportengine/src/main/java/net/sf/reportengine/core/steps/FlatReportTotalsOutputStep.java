/*
 * Created on 24.09.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.calc.Calculator;
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
	
	public static final String TOTAL_STRING = "Total";
	public static final String GRAND_TOTAL_STRING = "Grand Total";
   
    
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
     * 
     */
    public FlatReportTotalsOutputStep(){
    	//empty constructor
    }
    
    /**
     * init method 
     */
    public void init(ReportContext reportContext){
    	super.init(reportContext);
    	
    	groupCols = getGroupingColumns();
    	dataCols = getDataColumns(); 
    	distribOfCalculatorsInDataColsArray = getCalculatorDistributionInColumnDataArray(); 
    	
    	LOGGER.trace("The FlatReportTotalsOutputStep has been initialized. The distribution array is null {}", (distribOfCalculatorsInDataColsArray == null));
    }
    
    /**
     * execute 
     */
    public void execute(NewRowEvent rowEvent) {
        int aggLevel = getGroupingLevel();
        
        //when non simple data row 
        if( aggLevel >= 0){
        	int totalRowEnd = computeCalcRowNumberForAggLevel(aggLevel);
        	//TODO: this operation is the opposite of computeAggLevelForCalcRowNumber which is 
        	//called inside outputTotalRowsFromTo. One of them should be deleted.
        	
        	//output totals from level 0 to current grouping level
        	outputTotalRowsFromTo(0, totalRowEnd);            
        }else{
        	LOGGER.trace("not displaying totals because current level is {}", aggLevel);
        }
     }

    /**
     * exit displays the last totals in the calculator matrix buffer and the grand total
     */
    public void exit(ReportContext context) {
        Calculator[][] calculators = getCalculatorMatrix();
        
        if(groupCols != null){
        	//calculators.length-2 because for levelCalculators.lenght-1 is a separate call
        	//(a call for Grand total see below)
        	outputTotalRowsFromTo(0, calculators.length-2);
        }
        
        //now the grand total
        if(getShowGrandTotal()){
        	//outputTotalsRow("Grand Total", calculators[calculators.length-1]);
        	outputTotalsRow(GRAND_TOTAL_GROUPING_LEVEL, 
        					calculators[calculators.length-1]
        					);	
        }
        
        super.exit(context);
    }
    
    /**
     * outputs the totals existing on the specified rows starting with rowStart
     * and ending with rowEnnd (inclusive)
     * 
     * @param rowStart		the first row to start outputting 
     * @param rowEnd  		the last row to output
     */
    private void outputTotalRowsFromTo(int rowStart, int rowEnd){
    	LOGGER.trace("output totals from {} to ", rowStart, rowEnd);
    	Calculator[][] calculators = getCalculatorMatrix();
        for(int row = rowStart; row <= rowEnd ; row++){
        	//based on the row we can compute the aggregation level so that we can determine the 
        	// column to use from the previous data row
        	int aggLevel = computeAggLevelForCalcRowNumber(row);
        	
        	outputTotalsRow(aggLevel, calculators[row]);
        	        	
        }
    }
    
    /**
     * 
     * @param groupLevel
     * @param calcForCurrentGroupingLevel
     */
    private void outputTotalsRow(	int groupLevel, 
    								Calculator[] calcForCurrentGroupingLevel){
    	if(distribOfCalculatorsInDataColsArray.length != dataCols.size()){
    		//TODO: improve
    		throw new IllegalArgumentException("dataRows and distributionOfCalculators arrays should have the same length"); 
    	}
    	
    	ReportOutput output = getOutput();
    	Integer dataRowNumber = getDataRowCount(); 
    	output.startDataRow(new RowProps(dataRowNumber));
    	
    	if(groupCols != null && groupCols.size() > 0){
    		//prepare and output the Total column
    		String totalString = getTotalStringForGroupingLevel(groupLevel);
    		output.outputDataCell(new CellProps.Builder(totalString)
    							.horizAlign(HorizAlign.LEFT)
    							.rowNumber(dataRowNumber)
    							.build());
    		
    		if(groupCols.size() > 1){
    			//for all others grouping columns put whitespaces 
    			//(groupColumns.length-1 colspan because the first column was already 
    			//filled with the word "Total xxxx"
    			
    			//if you want a single cell spanning multiple rows decomment this
    			//output.output(new CellProps.Builder(ReportOutput.WHITESPACE)
    			//							.colspan(groupCols.size()-1) 
    			//							.build());
    			
    			//this is to display an empty cell for every remaining group column
    			for(int i=1; i<groupCols.size(); i++){
    				output.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE).rowNumber(dataRowNumber).build()); 
    			}
    		}
        }
        
    	String formattedResult = null; 
    	
        //then iterate over data columns to display the totals for those having calculators
    	for (int i = 0; i < dataCols.size(); i++) {
			DataColumn column = dataCols.get(i);
			if(column.getCalculator() != null){
				int calculatorIndex = distribOfCalculatorsInDataColsArray[i];
				Object calculatorResult = calcForCurrentGroupingLevel[calculatorIndex].getResult();
				
				//format the computed value 
				formattedResult = dataCols.get(i).getFormattedValue(calculatorResult); 
				
				output.outputDataCell(new CellProps.Builder(formattedResult)
									.horizAlign(dataCols.get(i).getHorizAlign())
									.rowNumber(dataRowNumber)
									.build());
			}else{
				//if the column doesn't have a calculator asociated 
				//then display an empty value (whitespace) with colspan 1
				output.outputDataCell(new CellProps.Builder(ReportOutput.WHITESPACE).rowNumber(dataRowNumber).build());
			}
		}
    	output.endDataRow();
    	incrementDataRowNbr(); 
    }
}
