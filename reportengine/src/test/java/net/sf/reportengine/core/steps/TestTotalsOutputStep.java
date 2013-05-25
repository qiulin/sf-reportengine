/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;
import net.sf.reportengine.util.MatrixUtils;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author dragos
 *
 */
public class TestTotalsOutputStep  {
	

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.FlatReportTotalsOutputStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	@Test
	public void testExecuteScenario1() {
		FlatReportTotalsOutputStep classUnderTest = new FlatReportTotalsOutputStep();
		AlgoContext mockReportContext = new DefaultAlgorithmContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		CellPropsArrayOutput mockOutput = new CellPropsArrayOutput(); 
		
		mockAlgoInput.put(IOKeys.REPORT_OUTPUT, mockOutput);
        mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
        mockAlgoInput.put(IOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
        mockAlgoInput.put(IOKeys.SHOW_GRAND_TOTAL, Scenario1.SHOW_GRAND_TOTAL); 
        mockAlgoInput.put(IOKeys.SHOW_TOTALS, true); 
        
        mockReportContext.set(ContextKeys.DISTRIBUTION_OF_CALCULATORS, Scenario1.DISTRIBUTION_OF_CALCULATOR_IN_DATA_ROW_ARRAY); 
        mockReportContext.set(ContextKeys.DATA_ROW_COUNT, 0); 
        
        mockAlgoInput.put(IOKeys.REPORT_INPUT, Scenario1.INPUT); 
		classUnderTest.init(mockAlgoInput, mockReportContext);
		
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		
    	//simulate the level detector
		mockReportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_1_AGG_LEVEL);
		mockReportContext.set(ContextKeys.CALCULATORS, null);
    	classUnderTest.execute(dataRowEvent);
    	mockReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[0]);
		
    	dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
    	mockReportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_2_AGG_LEVEL);
    	mockReportContext.set(ContextKeys.CALCULATORS, Scenario1.ROW_1_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent);
		mockReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[1]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		mockReportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_3_AGG_LEVEL);
		mockReportContext.set(ContextKeys.CALCULATORS, Scenario1.ROW_2_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent);
		mockReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[2]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		mockReportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_4_AGG_LEVEL);
		mockReportContext.set(ContextKeys.CALCULATORS, Scenario1.ROW_3_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent);
		mockReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[3]);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		mockReportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_5_AGG_LEVEL);
		mockReportContext.set(ContextKeys.CALCULATORS, Scenario1.ROW_4_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent);
		mockReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[4]);
		
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		mockReportContext.set(ContextKeys.NEW_GROUPING_LEVEL, Scenario1.ROW_6_AGG_LEVEL);
		mockReportContext.set(ContextKeys.CALCULATORS, Scenario1.ROW_5_CALCULATORS_RESULTS);
		classUnderTest.execute(dataRowEvent);
		mockReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[5]);
		
		mockReportContext.set(ContextKeys.CALCULATORS, Scenario1.ROW_6_CALCULATORS_RESULTS);
		classUnderTest.exit(mockAlgoInput, mockReportContext);
		
		CellProps[][] resultCellMatrix = mockOutput.getDataCellMatrix();
		Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.OUTPUT_TOTALS, resultCellMatrix));
	}
	
	//TODO
	public void testExecuteComputedColumnsScenario(){
//		IAlgorithmContext reportContext = getTestContext(); 
//		
//		//simulate the level detector
//		reportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS); 
//		reportContext.set(FlatReport.CONTEXT_KEY_DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
//        reportContext.set(FlatReport.CONTEXT_KEY_SHOW_GRAND_TOTAL, true);
//        reportContext.set(FlatReportExtractTotalsDataInitStep.CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS, 
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
