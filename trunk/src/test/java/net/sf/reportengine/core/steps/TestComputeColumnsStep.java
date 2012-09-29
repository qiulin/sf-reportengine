/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.FlatReport;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ReportEngineArrayUtils;

/**
 * @author dragos
 * 
 */
public class TestComputeColumnsStep extends ReportAlgorithmStepTC {
	
	private ComputeColumnValuesStep classUnderTest = null;
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.impl.ReportAlgorithmStepTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new ComputeColumnValuesStep();
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.impl.ReportAlgorithmStepTestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.ComputeColumnValuesStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteScenario1() {
		IAlgorithmContext reportContext = getTestContext(); 
		
		reportContext.set(AbstractReport.CONTEXT_KEY_DATA_COLUMNS, Scenario1.DATA_COLUMNS.toArray());
		reportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, Scenario1.GROUPING_COLUMNS.toArray());
		classUnderTest.init(reportContext);
		
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		classUnderTest.execute(dataRowEvent);
		
		Object[] results = (Object[])reportContext.get(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES);
		
		assertNotNull(results);
		assertEquals(6, results.length);
		assertTrue(ReportEngineArrayUtils.equalArraysAsStrings(results, Scenario1.ROW_OF_DATA_1));
		
		//row 2
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent);
		
		results = (Object[])reportContext.get(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES);
		
		assertNotNull(results);
		assertEquals(6, results.length);
		assertTrue(ReportEngineArrayUtils.equalArraysAsStrings(results, Scenario1.ROW_OF_DATA_2));
	}
	
	public void testExecuteComputedColumns(){
		IAlgorithmContext reportContext = getTestContext(); 
		
		reportContext.set(AbstractReport.CONTEXT_KEY_DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
		reportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
		
		classUnderTest.init(reportContext);
		
		for(int i=0; i< CalculatedColumnsScenario.RAW_DATA.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
			classUnderTest.execute(dataRowEvent);
		
			Object[] results = (Object[])reportContext.get(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES);
		
			assertNotNull(results);
			assertEquals(8, results.length);
			assertTrue(ReportEngineArrayUtils.equalArraysAsStrings(results, CalculatedColumnsScenario.COMPUTED_VALUES[i]));
		}
	}
}