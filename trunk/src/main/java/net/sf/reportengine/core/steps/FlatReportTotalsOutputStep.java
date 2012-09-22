/*
 * Created on 24.09.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.config.IGroupingColumn;
import net.sf.reportengine.config.SubtotalsInfo;
import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.ReportConstants;
import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.IReportOutput;

import org.apache.log4j.Logger;


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
	private static final Logger logger = Logger.getLogger(FlatReportTotalsOutputStep.class);
	
	
	public static final String TOTAL_STRING = "Total";
	public static final String GRAND_TOTAL_STRING = "Grand Total";
   
    
    /**
     * previous data row - used mainly to display the totals 
     */
    //private Object[] previousDataRow; 
    
    
    private IGroupingColumn[] groupCols; 
    
    private IDataColumn[] dataCols;
    
    private int[] distribOfCalculatorsInDataColsArray; 
    
    /**
     * 
     */
    public FlatReportTotalsOutputStep(){
    	//empty constructor
    }
    
    /**
     * the init of this step 
     */
    public void init(IAlgorithmContext reportContext){
    	super.init(reportContext);
    	
    	groupCols = getGroupingColumns();
    	dataCols = getDataColumns(); 
    	distribOfCalculatorsInDataColsArray = getCalculatorDistributionInColumnDataArray(); 
    	
    	if(logger.isTraceEnabled()){
    		logger.trace("The FlatReportTotalsOutputStep has been initialized. The distribution array is null "+(distribOfCalculatorsInDataColsArray == null));
    	}
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
        	logger.trace("not displaying totals because current level is "+aggLevel);
        }
     }

    /**
     * exit displays the last totals in the calculator matrix buffer and the grand total
     */
    public void exit() {
        ICalculator[][] calculators = getCalculatorMatrix();
        
        if(groupCols != null){
        	//calculators.length-2 because for levelCalculators.lenght-1 is a separate call
        	//(a call for Grand total see below)
        	outputTotalRowsFromTo(0, calculators.length-2);
        }
        
        //now the grand total
        if(getShowGrandTotal()){
        	//outputTotalsRow("Grand Total", calculators[calculators.length-1]);
        	outputTotalsRow(SubtotalsInfo.GRAND_TOTAL_GROUPING_LEVEL, 
        					calculators[calculators.length-1]
        					);	
        }
        
        super.exit();
    }
    
    /**
     * outputs the totals existing on the specified rows starting with rowStart
     * and ending with rowEnnd (inclusive)
     * 
     * @param rowStart		the first row to start outputting 
     * @param rowEnd  		the last row to output
     */
    private void outputTotalRowsFromTo(int rowStart, int rowEnd){
    	logger.trace("output totals from "+rowStart+" to "+rowEnd);
    	ICalculator[][] calculators = getCalculatorMatrix();
        for(int row = rowStart; row <= rowEnd ; row++){
        	//based on the row we can compute the aggregation level so that we can determine the 
        	// column to use from the previous data row
        	int aggLevel = computeAggLevelForCalcRowNumber(row);
        	
        	outputTotalsRow(aggLevel,
        					calculators[row]
        					);
        	        	
        }
    }
    
    
    private void outputTotalsRow(	int groupLevel, 
    								ICalculator[] calcForCurrentGroupingLevel){
    	if(distribOfCalculatorsInDataColsArray.length != dataCols.length){
    		//TODO: improve
    		throw new IllegalArgumentException("dataRows and distributionOfCalculators arrays should have the same length"); 
    	}
    	
    	IReportOutput output = getOutput();
    	output.startRow();
    	
    	if(groupCols != null && groupCols.length > 0){
    		//prepare and output the Total column
    		String totalString = getTotalStringForGroupingLevel(groupLevel);
    		CellProps totalCellProps = new CellProps(totalString);
    		output.output(totalCellProps);
    		
    		if(groupCols.length > 1){
    			//for all others grouping columns put whitespaces 
    			//( groupColumns.length-1 colspan because the first column was already 
    			//filled with the word "Total xxxx"
    			output.output(new CellProps(IReportOutput.WHITESPACE, groupCols.length-1, 1, ReportContent.CONTENT_DATA));
    		}
        }
        
        //then iterate over data columns to display the totals for those having calculators
    	for (int i = 0; i < dataCols.length; i++) {
			IDataColumn column = dataCols[i];
			if(column.getCalculator() != null){
				int calculatorIndex = distribOfCalculatorsInDataColsArray[i];
				Object calculatorResult = calcForCurrentGroupingLevel[calculatorIndex].getResult(); 
				output.output(new CellProps(calculatorResult));
			}else{
				output.output(new CellProps(IReportOutput.WHITESPACE));
			}
		}
    	output.endRow();
    }
   
    
 
    
//    public String getTotalStringForGroupingLevel(int groupingLevel, Object[] prevDataRow) {
//    	String result;
//		
//		if(groupingLevel != SubtotalsInfo.GRAND_TOTAL_GROUPING_LEVEL){
//			String prevValueForGropingLevel = prevDataRow[groupingLevel].toString();
//			result = TOTAL_STRING + " " + prevValueForGropingLevel;
//		}else{
//			result = GRAND_TOTAL_STRING;
//		}
//		return result;
//    }
}
