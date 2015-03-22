/*
 * Created on 02.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.util.CalculatorIntermResultsMatrix;
import net.sf.reportengine.util.ContextKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * this step is responsible for :
 * 	1. initializing / reinitializing the totals
 * 	2. computing the totals
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class TotalsCalculatorStep extends AbstractReportStep<CalcIntermResult[][], String, String>{
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TotalsCalculatorStep.class);
	
	/**
     * the calculators matrix
     */
    private CalculatorIntermResultsMatrix calculatorMatrix;
    
    /**
     * 
     */
    private int groupColsCnt = -1;
    
    
    /**
     * init
     */
    public StepResult<CalcIntermResult[][]> init(StepInput stepInput){
        groupColsCnt = getGroupColumnsCount(stepInput); 
        
        if(groupColsCnt == 0){
        	LOGGER.warn("Dangerous setting: TotalsCalculatorStep was set in a report not having group columns");
        }
        
        //groupColsCnt +1 (for Grand Total)
        calculatorMatrix = new CalculatorIntermResultsMatrix(groupColsCnt + 1, getDataColumns(stepInput));
        calculatorMatrix.initAll();
        
        //getAlgoContext().set(ContextKeys.CALC_INTERM_RESULTS, calculatorMatrix.getIntermResultsMatrix());
        return new StepResult(ContextKeys.CALC_INTERM_RESULTS, calculatorMatrix.getIntermResultsMatrix()); 
    }
    
    /**
     * execute
     */
    public StepResult<String> execute(NewRowEvent newRowEvent, StepInput stepInput){
		int aggLevel = getGroupingLevel(stepInput);
		if(aggLevel >= 0){
			//if the current row is not a simple data row but contains a change in the grouping level 
			int rowsToBeReinitialized = groupColsCnt - aggLevel ;
			LOGGER.trace(	"reinitializing totals for {} rows in the totals table (aggLevel = {}",
							rowsToBeReinitialized, aggLevel);
			calculatorMatrix.initFirstXRows(rowsToBeReinitialized);
		}    		
		//add values to all calculators
		calculatorMatrix.addValuesToEachRow(newRowEvent);
		return StepResult.NO_RESULT; 
    } 
    
    public StepResult<String> exit(StepInput stepInput){
    	return StepResult.NO_RESULT; 
    }
}
