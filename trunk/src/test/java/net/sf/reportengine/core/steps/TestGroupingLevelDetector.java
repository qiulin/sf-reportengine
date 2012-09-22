package net.sf.reportengine.core.steps;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.FlatReport;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1;

public class TestGroupingLevelDetector extends ReportAlgorithmStepTC {
	
	
	private GroupingLevelDetectorStep classUnderTest;
	
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new GroupingLevelDetectorStep(); 
	}
	
	
	public void testExecuteScenario1() {
		IAlgorithmContext testReportContext = getTestContext();
		
		testReportContext.set(FlatReport.CONTEXT_KEY_GROUPING_COLUMNS, Scenario1.GROUPING_COLUMNS);
		classUnderTest.init(testReportContext);
		
		for(int i=0; i<Scenario1.RAW_DATA.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.RAW_DATA[i]);
			classUnderTest.execute(dataRowEvent);
			assertEquals(testReportContext.get(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL), Scenario1.AGG_LEVEL[i]);
			
			testReportContext.set(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[i]);
		}
	}
	
	
	public void testExecuteScenario2() {
		IAlgorithmContext testReportContext = getTestContext();
		
		testReportContext.set(FlatReport.CONTEXT_KEY_GROUPING_COLUMNS, Scenario2.GROUPING_COLUMNS);
		classUnderTest.init(testReportContext);
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
			classUnderTest.execute(dataRowEvent);
			assertEquals(testReportContext.get(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL), Scenario2.AGG_LEVEL[i]);
			
			testReportContext.set(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES, Scenario2.PREVIOUS_GROUP_VALUES[i]);
		}
	}

	
	
	public void testExecuteCalculatedColumnsScenario() {
		IAlgorithmContext testReportContext = getTestContext();
		
		testReportContext.set(AbstractReport.CONTEXT_KEY_DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
		testReportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
		
		classUnderTest.init(testReportContext);
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
			testReportContext.set(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES, CalculatedColumnsScenario.COMPUTED_VALUES[i]);
			classUnderTest.execute(dataRowEvent);
			assertEquals(testReportContext.get(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL), CalculatedColumnsScenario.AGG_LEVEL[i]);
			
			testReportContext.set(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES, CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i]);
		}
	}
	
	
	
}
