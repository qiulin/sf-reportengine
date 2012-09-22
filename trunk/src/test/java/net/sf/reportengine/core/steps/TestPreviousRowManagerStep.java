/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Arrays;

import net.sf.reportengine.FlatReport;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.util.MatrixUtils;


/**
 * @author Administrator
 *
 */
public class TestPreviousRowManagerStep extends ReportAlgorithmStepTC {
	
	private PreviousRowManagerStep classUnderTest = null; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		classUnderTest = new PreviousRowManagerStep();
	}
	
	
	public void testExecuteScenario1() {
		IAlgorithmContext testReportContext = getTestContext();
		
		testReportContext.set(FlatReport.CONTEXT_KEY_GROUPING_COLUMNS, Scenario1.GROUPING_COLUMNS);
		classUnderTest.init(testReportContext);
		
		
		//first we check that previous data row is null
		assertNull(testReportContext.get(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES));
		
		for(int i=0; i<Scenario1.RAW_DATA.length; i++){
			
			//first simulate the grouping level detector
			testReportContext.set(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL, Scenario1.AGG_LEVEL[i]);
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.RAW_DATA[i]);
			classUnderTest.execute(dataRowEvent);
			
			Object[] prevValues = (Object[])testReportContext.get(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES);
			assertNotNull(prevValues);
			assertEquals(Scenario1.PREVIOUS_GROUP_VALUES[i].length, prevValues.length); 
			
			assertTrue(Arrays.equals(Scenario1.PREVIOUS_GROUP_VALUES[i], prevValues));
		}
	}
	
	
	public void testExecuteScenario2() {
		IAlgorithmContext testReportContext = getTestContext();
		
		testReportContext.set(FlatReport.CONTEXT_KEY_GROUPING_COLUMNS, Scenario2.GROUPING_COLUMNS);
		classUnderTest.init(testReportContext);
		
		
		//first we check that previous data row is null
		assertNull(testReportContext.get(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES));
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			
			//first simulate the grouping level detector
			testReportContext.set(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL, Scenario2.AGG_LEVEL[i]);
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
			classUnderTest.execute(dataRowEvent);
			
			Object[] prevValues = (Object[])testReportContext.get(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES);
			assertNotNull(prevValues);
			assertEquals(Scenario2.PREVIOUS_GROUP_VALUES[i].length, prevValues.length); 
			
			assertTrue(MatrixUtils.compareMatricesAsStrings(new Object[][]{Scenario2.PREVIOUS_GROUP_VALUES[i]}, new Object[][]{prevValues}));
		}
	}

	
	
	public void testExecuteCalculatedColumnsScenario() {
		IAlgorithmContext testReportContext = getTestContext();
		
		testReportContext.set(FlatReport.CONTEXT_KEY_GROUPING_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
		classUnderTest.init(testReportContext);
		
		
		//first we check that previous data row is null
		assertNull(testReportContext.get(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES));
		
		for(int i=0; i<CalculatedColumnsScenario.RAW_DATA.length; i++){
			
			//first simulate the grouping level detector
			testReportContext.set(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL, CalculatedColumnsScenario.AGG_LEVEL[i]);
			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
			classUnderTest.execute(dataRowEvent);
			
			Object[] prevValues = (Object[])testReportContext.get(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES);
			assertNotNull(prevValues);
			assertEquals(CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i].length, prevValues.length); 
			
			System.out.println("expected : "+Arrays.toString(CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i]));
			System.out.println(" get : "+Arrays.toString(prevValues));
			
			assertTrue(Arrays.equals(CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i], prevValues));
		}
	}

}
