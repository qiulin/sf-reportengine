/*
 * Created on 02.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.AbstractReportStep;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalculatorException;
import net.sf.reportengine.util.CalculatorMatrix;

import org.apache.log4j.Logger;

/**
 * this step is responsible for :
 * 	1. initializing / reinitializing the totals
 * 	2. calculation of the totals
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class TotalsCalculatorStep extends AbstractReportStep{
    
	/**
	 * the one and only logger
	 */
	private static final Logger logger = Logger.getLogger(TotalsCalculatorStep.class);
	
	/**
	 * the context key for the calculator matrix
	 */
    public static final String CONTEXT_KEY_CALCULATORS = "net.sf.reportengine.calculators";
    
    
    
    /**
     * the calculators matrix
     */
    private CalculatorMatrix calculatorMatrix;
    
    
    int groupColsCnt = -1;
    
    /**
     * 
     * @param columns
     */
    public TotalsCalculatorStep(){
    	
    }
    
      
    
    
    /**
     * init
     */
    public void init(IAlgorithmContext reportContext){
        super.init(reportContext);
        
        //ICalculator[] prototypesCalc = extractCalculators(getDataColumns());
        
        groupColsCnt = getGroupingColumns() != null ? getGroupingColumns().length : 0;
        
        if(groupColsCnt == 0){
        	logger.warn("Dangerous setting: TotalsCalculatorStep was set in a report not having group columns");
        }
        
        //groupColsCnt +1 (for Grand Total)
        calculatorMatrix = new CalculatorMatrix(groupColsCnt + 1, getDataColumns());
        calculatorMatrix.initAllCalculators();
        
        reportContext.set(CONTEXT_KEY_CALCULATORS, calculatorMatrix.getCalculators());
    }
    
    /**
     * execute
     */
    public void execute(NewRowEvent newRowEvent){
		int aggLevel = getGroupingLevel();
		if(aggLevel >= 0){
			//if the current row is not a simple data row but contains a change in the grouping level 
			int rowsToBeReinitialized = groupColsCnt - aggLevel ;
			logger.trace("reinitializing totals for "+rowsToBeReinitialized+" rows in the totals table (aggLevel = "+aggLevel+")");
			calculatorMatrix.initFirstXRows(rowsToBeReinitialized);
		}    		
		//add values to all calculators
		calculatorMatrix.addValuesToEachRow(newRowEvent);    		
    } 
}
