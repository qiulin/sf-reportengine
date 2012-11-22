/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.MatrixUtils;


/**
 * @author dragos
 *
 */
public class TestTotalsOutputStep extends ReportAlgorithmStepTC {
	
	/**
	 * the class under test
	 */
	private FlatReportTotalsOutputStep classUnderTest;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new FlatReportTotalsOutputStep();
		
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.FlatReportTotalsOutputStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteScenario1() {
		IReportContext reportContext = getTestContext(); 
		
		//simulate the level detector
		reportContext.set(ContextKeys.CONTEXT_KEY_GROUPING_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		reportContext.set(ContextKeys.CONTEXT_KEY_DATA_COLUMNS, Scenario1.DATA_COLUMNS);
        reportContext.set(ContextKeys.CONTEXT_KEY_SHOW_GRAND_TOTAL, Scenario1.SHOW_GRAND_TOTAL);
        reportContext.set(ContextKeys.CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS, Scenario1.DISTRIBUTION_OF_CALCULATOR_IN_DATA_ROW_ARRAY); 
        
		getTestContext().setInput(Scenario1.INPUT);
		classUnderTest.init(reportContext);
		
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		
    	//simulate the level detector
    	setAggLevel(Scenario1.ROW_1_AGG_LEVEL);
    	setCalculatorMatrix(null);
    	setComputedInputValues(Scenario1.ROW_OF_DATA_1);
    	classUnderTest.execute(dataRowEvent);
    	setPreviousGroupValues(Scenario1.PREVIOUS_GROUP_VALUES[0]);
		
    	dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		setAggLevel(Scenario1.ROW_2_AGG_LEVEL);
		setCalculatorMatrix(Scenario1.ROW_1_CALCULATORS_RESULTS);
		setComputedInputValues(Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent);
		setPreviousGroupValues(Scenario1.PREVIOUS_GROUP_VALUES[1]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		setAggLevel(Scenario1.ROW_3_AGG_LEVEL);
		setCalculatorMatrix(Scenario1.ROW_2_CALCULATORS_RESULTS);
		setComputedInputValues(Scenario1.ROW_OF_DATA_3);
		classUnderTest.execute(dataRowEvent);
		setPreviousGroupValues(Scenario1.PREVIOUS_GROUP_VALUES[2]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		setAggLevel(Scenario1.ROW_4_AGG_LEVEL);
		setCalculatorMatrix(Scenario1.ROW_3_CALCULATORS_RESULTS);
		setComputedInputValues(Scenario1.ROW_OF_DATA_4);
		classUnderTest.execute(dataRowEvent);
		setPreviousGroupValues(Scenario1.PREVIOUS_GROUP_VALUES[3]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		setAggLevel(Scenario1.ROW_5_AGG_LEVEL);
		setCalculatorMatrix(Scenario1.ROW_4_CALCULATORS_RESULTS);
		setComputedInputValues(Scenario1.ROW_OF_DATA_5);
		classUnderTest.execute(dataRowEvent);
		setPreviousGroupValues(Scenario1.PREVIOUS_GROUP_VALUES[4]);
		
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		setAggLevel(Scenario1.ROW_6_AGG_LEVEL);
		setCalculatorMatrix(Scenario1.ROW_5_CALCULATORS_RESULTS);
		setComputedInputValues(Scenario1.ROW_OF_DATA_6);
		classUnderTest.execute(dataRowEvent);
		setPreviousGroupValues(Scenario1.PREVIOUS_GROUP_VALUES[5]);
		
		setCalculatorMatrix(Scenario1.ROW_6_CALCULATORS_RESULTS);
		classUnderTest.exit();
		
		CellProps[][] resultCellMatrix = getTestOutput().getCellMatrix();
		MatrixUtils.logMatrix(resultCellMatrix); 
		assertTrue(MatrixUtils.compareMatrices(Scenario1.OUTPUT_TOTALS, resultCellMatrix));
	}
	
	//TODO
	public void testExecuteComputedColumnsScenario(){
//		IAlgorithmContext reportContext = getTestContext(); 
//		
//		//simulate the level detector
//		reportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS); 
//		reportContext.set(FlatReport.CONTEXT_KEY_DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
//        reportContext.set(FlatReport.CONTEXT_KEY_SHOW_GRAND_TOTAL, true);
//        reportContext.set(FlatReportExtractDataInitStep.CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS, 
//        		CalculatedColumnsScenario.DISTRIBUTION_OF_CALCULATOR_IN_DATA_ROW_ARRAY);
//        
//		getTestContext().setInput(CalculatedColumnsScenario.INPUT);
//		classUnderTest.init(getTestContext());
//		
//		for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
//			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
//		
//			//simulate the preceeding steps
//			setAggLevel(CalculatedColumnsScenario.AGG_LEVEL[i]);
//			setCalculatorMatrix(CalculatedColumnsScenario.CALCULATORS_RESULTS);
//			setComputedInputValues(CalculatedColumnsScenario.COMPUTED_VALUES[i]);
//			classUnderTest.execute(dataRowEvent);
//			setPreviousGroupValues(CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i]);
//		}
	}

}
