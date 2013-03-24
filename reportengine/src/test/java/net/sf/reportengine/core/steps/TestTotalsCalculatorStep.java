/*
 * Created on 29.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos balan (dragos.balan@gmail.com)
 *
 * used to test the changes made in any of the below classes :
 * ThirdCalculatorStep, CalculatorsPack, Calculator
 * 
 */
public class TestTotalsCalculatorStep extends ReportAlgorithmStepTC {
    
    private TotalsCalculatorStep classUnderTest;
    
    
    
    /*
     * @see TestCase#setUp()
     */
    protected void setUp() throws Exception {
        super.setUp();
        
        
    }

    public void testExecuteScenario1() {
    	ReportContext reportContext = getTestContext();
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	reportContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
    	reportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
    	reportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.AGG_COLUMNS_INDEX);
        
    	
        classUnderTest.init(reportContext);      
    	   	
    	//simulate the level detector
    	setAggLevel(Scenario1.ROW_1_AGG_LEVEL);
    	
    	NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
    	reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_1);
		classUnderTest.execute(dataRowEvent);
		
		Calculator[][] calcMatrix = (Calculator[][])reportContext.get(ContextKeys.CALCULATORS);
		assertNotNull(calcMatrix);
		assertEquals(calcMatrix.length, Scenario1.AGG_COLUMNS_INDEX.length + 1 /* for Grand total*/);
		
		assertEqualsCalculatorValues(Scenario1.ROW_1_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_2_AGG_LEVEL);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent);
		
		assertEqualsCalculatorValues(Scenario1.ROW_2_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_3_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_3);
		classUnderTest.execute(dataRowEvent);
		
		assertEqualsCalculatorValues(Scenario1.ROW_3_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_4_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_4);
		classUnderTest.execute(dataRowEvent);
		
		assertEqualsCalculatorValues(Scenario1.ROW_4_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_5_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_5);
		classUnderTest.execute(dataRowEvent);
		
		assertEqualsCalculatorValues(Scenario1.ROW_5_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_6_AGG_LEVEL);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_6);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		classUnderTest.execute(dataRowEvent);
		
		assertEqualsCalculatorValues(Scenario1.ROW_6_CALCULATORS_RESULTS);
    }   
    
    public void testExecuteScenario2(){
    	ReportContext reportContext = getTestContext();
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	reportContext.set(ContextKeys.DATA_COLUMNS, Scenario2.DATA_COLUMNS);
    	reportContext.set(ContextKeys.GROUP_COLUMNS, Scenario2.GROUPING_COLUMNS);
    	//reportContext.set(LevelDetectorStep.CONTEXT_KEY_AGG_COLUMNS_INDEX, Scenario2.AGG_COLUMNS_INDEX);
    	//reportContext.set(LevelDetectorStep.CONTEXT_KEY_AGG_LEVEL_COUNT, Scenario2.AGG_COLUMNS_INDEX.length);
         
        classUnderTest.init(reportContext);      
    	 
        for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
        	//simulate the level detector and all other preceeding steps
        	setAggLevel(Scenario2.AGG_LEVEL[i]);
        	reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario2.COMPUTED_INPUT[i]);
        	
        	//call execute
        	NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
    		classUnderTest.execute(dataRowEvent);
        }
        
        assertEqualsCalculatorValues(Scenario2.CALCULATORS_RESULTS);
    }
    
    public void testExecuteCalculatedColumnsScenario(){
    	ReportContext reportContext = getTestContext();
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	reportContext.set(ContextKeys.DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
    	reportContext.set(ContextKeys.GROUP_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
        //reportContext.set(LevelDetectorStep.CONTEXT_KEY_AGG_COLUMNS_INDEX, CalculatedColumnsScenario.AGG_COLUMNS_INDEX);
        //reportContext.set(LevelDetectorStep.CONTEXT_KEY_AGG_LEVEL_COUNT, CalculatedColumnsScenario.AGG_COLUMNS_INDEX.length);
         
        classUnderTest.init(reportContext);      
    	 
        for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
        	//simulate the level detector and all other preceeding steps
        	setAggLevel(CalculatedColumnsScenario.AGG_LEVEL[i]);
        	reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, CalculatedColumnsScenario.COMPUTED_VALUES[i]);
        	
        	//call execute
        	NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
    		classUnderTest.execute(dataRowEvent);
        }
        
        assertEqualsCalculatorValues(CalculatedColumnsScenario.CALCULATORS_RESULTS);
    }
}
