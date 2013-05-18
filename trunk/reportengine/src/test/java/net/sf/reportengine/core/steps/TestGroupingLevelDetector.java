package net.sf.reportengine.core.steps;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoInput;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;

public class TestGroupingLevelDetector extends ReportAlgorithmStepTC {
	
	
	private GroupingLevelDetectorStep classUnderTest;
	
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new GroupingLevelDetectorStep(); 
	}
	
	
	public void testExecuteScenario1() {
		ReportContext testReportContext = getTestContext();
		Map<InputKeys, Object> mockAlgoInput = new EnumMap<InputKeys, Object>(InputKeys.class);
		
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
		mockAlgoInput.put(InputKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS);
		
		classUnderTest.init(mockAlgoInput, testReportContext);
		
		for(int i=0; i<Scenario1.RAW_DATA.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.RAW_DATA[i]);
			classUnderTest.execute(dataRowEvent);
			assertEquals(testReportContext.get(ContextKeys.NEW_GROUPING_LEVEL), Scenario1.AGG_LEVEL[i]);
			
			testReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[i]);
		}
	}
	
	
	public void testExecuteScenario2() {
		ReportContext testReportContext = getTestContext();
		Map<InputKeys, Object> mockAlgoInput = new EnumMap<InputKeys, Object>(InputKeys.class);
		
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, Scenario2.GROUPING_COLUMNS);
		mockAlgoInput.put(InputKeys.GROUP_COLS, Scenario2.GROUPING_COLUMNS);
		classUnderTest.init(mockAlgoInput, testReportContext);
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
			classUnderTest.execute(dataRowEvent);
			assertEquals(testReportContext.get(ContextKeys.NEW_GROUPING_LEVEL), Scenario2.AGG_LEVEL[i]);
			
			testReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario2.PREVIOUS_GROUP_VALUES[i]);
		}
	}

	
	
	public void testExecuteCalculatedColumnsScenario() {
		ReportContext testReportContext = getTestContext();
		Map<InputKeys, Object> mockAlgoInput = new EnumMap<InputKeys, Object>(InputKeys.class); 
		
		mockAlgoInput.put(InputKeys.GROUP_COLS, CalculatedColumnsScenario.GROUP_COLUMNS);
		mockAlgoInput.put(InputKeys.DATA_COLS, CalculatedColumnsScenario.DATA_COLUMNS);
		
		//testReportContext.set(ContextKeys.DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
		
		classUnderTest.init(mockAlgoInput, testReportContext);
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
			testReportContext.set(ContextKeys.COMPUTED_CELL_VALUES, CalculatedColumnsScenario.COMPUTED_VALUES[i]);
			classUnderTest.execute(dataRowEvent);
			assertEquals(testReportContext.get(ContextKeys.NEW_GROUPING_LEVEL), CalculatedColumnsScenario.AGG_LEVEL[i]);
			
			testReportContext.set(ContextKeys.LAST_GROUPING_VALUES, CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i]);
		}
	}
}
