/*
 * Created on 29.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan (dragos.balan@gmail.com)
 *
 * used to test the changes made in any of the below classes :
 * ThirdCalculatorStep, CalculatorsPack, GroupCalculator
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
    	AlgoContext reportContext = getTestContext();
    	Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	mockAlgoInput.put(IOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
    	mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
    	
    	reportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.AGG_COLUMNS_INDEX);
        
    	StepResult<CalcIntermResult[][]> initStepResult = classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
    	reportContext.set(ContextKeys.CALC_INTERM_RESULTS, initStepResult.getValue());
    	   	
    	//simulate the level detector
    	setAggLevel(Scenario1.ROW_1_AGG_LEVEL);
    	
    	NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
    	//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_1);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		CalcIntermResult[][] calcResults = initStepResult.getValue(); //(CalcIntermResult[][])reportContext.get(ContextKeys.CALC_INTERM_RESULTS);
		assertNotNull(calcResults);
		assertEquals(calcResults.length, Scenario1.AGG_COLUMNS_INDEX.length + 1 /* for Grand total*/);
		
		assertEqualsCalculatorResults(Scenario1.ROW_1_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_2_AGG_LEVEL);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_2_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_3_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_3);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_3_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_4_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_4);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_4_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_5_AGG_LEVEL);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_5);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_5_CALCULATORS_RESULTS);
		
		//simulate level detector
		setAggLevel(Scenario1.ROW_6_AGG_LEVEL);
		//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_6);
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
		
		assertEqualsCalculatorResults(Scenario1.ROW_6_CALCULATORS_RESULTS);
    }   
    
    public void testExecuteScenario2(){
    	AlgoContext reportContext = getTestContext();
    	Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	//reportContext.set(ContextKeys.DATA_COLUMNS, Scenario2.DATA_COLUMNS);
    	//reportContext.set(ContextKeys.GROUP_COLUMNS, Scenario2.GROUPING_COLUMNS);
    	mockAlgoInput.put(IOKeys.DATA_COLS, Scenario2.DATA_COLUMNS); 
    	mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario2.GROUPING_COLUMNS);
    	
    	StepResult<CalcIntermResult[][]> initStepResult = classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
    	reportContext.set(ContextKeys.CALC_INTERM_RESULTS, initStepResult.getValue());
    	 
        for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
        	//simulate the level detector and all other preceeding steps
        	setAggLevel(Scenario2.AGG_LEVEL[i]);
        	//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario2.COMPUTED_INPUT[i]);
        	
        	//call execute
        	NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
    		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
        }
        
        assertEqualsCalculatorResults(Scenario2.CALCULATORS_RESULTS);
    }
    
    public void testExecuteCalculatedColumnsScenario(){
    	AlgoContext reportContext = getTestContext();
    	Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	
    	classUnderTest = new TotalsCalculatorStep();
         
        //simulate the level detector
    	mockAlgoInput.put(IOKeys.DATA_COLS, CalculatedColumnsScenario.DATA_COLUMNS); 
    	mockAlgoInput.put(IOKeys.GROUP_COLS, CalculatedColumnsScenario.GROUP_COLUMNS);
    	
    	StepResult<CalcIntermResult[][]> initStepResult = classUnderTest.init(new StepInput(mockAlgoInput, reportContext)); 
    	reportContext.set(ContextKeys.CALC_INTERM_RESULTS, initStepResult.getValue());     
    	 
        for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
        	//simulate the level detector and all other preceeding steps
        	setAggLevel(CalculatedColumnsScenario.AGG_LEVEL[i]);
        	//reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, CalculatedColumnsScenario.COMPUTED_VALUES[i]);
        	
        	//call execute
        	NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
    		classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, reportContext));
        }
        
        assertEqualsCalculatorResults(CalculatedColumnsScenario.CALCULATORS_RESULTS);
    }
}
