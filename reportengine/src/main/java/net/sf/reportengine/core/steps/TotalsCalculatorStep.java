/*
 * Created on 02.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.Map;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.util.CalculatorMatrix;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

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
public class TotalsCalculatorStep extends AbstractReportStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TotalsCalculatorStep.class);
	
	/**
     * the calculators matrix
     */
    private CalculatorMatrix calculatorMatrix;
    
    /**
     * 
     */
    int groupColsCnt = -1;
    
    
    /**
     * init
     */
    @Override public void init(Map<IOKeys, Object> algoInput, AlgoContext reportContext){
        super.init(algoInput, reportContext);
        
        //Calculator[] prototypesCalc = extractCalculators(getDataColumns());
        
        groupColsCnt = getGroupColumnsLength(); 
        
        if(groupColsCnt == 0){
        	LOGGER.warn("Dangerous setting: TotalsCalculatorStep was set in a report not having group columns");
        }
        
        //groupColsCnt +1 (for Grand Total)
        calculatorMatrix = new CalculatorMatrix(groupColsCnt + 1, getDataColumns());
        calculatorMatrix.initAllCalculators();
        
        reportContext.set(ContextKeys.CALCULATORS, calculatorMatrix.getCalculators());
    }
    
    /**
     * execute
     */
    @Override public void execute(NewRowEvent newRowEvent){
		int aggLevel = getGroupingLevel();
		if(aggLevel >= 0){
			//if the current row is not a simple data row but contains a change in the grouping level 
			int rowsToBeReinitialized = groupColsCnt - aggLevel ;
			LOGGER.trace(	"reinitializing totals for {} rows in the totals table (aggLevel = {}",
							rowsToBeReinitialized, aggLevel);
			calculatorMatrix.initFirstXRows(rowsToBeReinitialized);
		}    		
		//add values to all calculators
		calculatorMatrix.addValuesToEachRow(newRowEvent);    		
    } 
}
