/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

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
		AlgoContext reportContext = getTestContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);  
		
		//reportContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		//reportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
		mockAlgoInput.put(IOKeys.DATA_COLS, Scenario1.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS); 
		
		classUnderTest.init(mockAlgoInput, reportContext);
		
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		classUnderTest.execute(dataRowEvent);
		
		Object[] results = (Object[])reportContext.get(ContextKeys.COMPUTED_CELL_VALUES);
		
		assertNotNull(results);
		assertEquals(6, results.length);
		assertTrue(Arrays.equals(results, Scenario1.RAW_DATA[0]));
		
		//row 2
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent);
		
		results = (Object[])reportContext.get(ContextKeys.COMPUTED_CELL_VALUES);
		
		assertNotNull(results);
		assertEquals(6, results.length);
		assertTrue(Arrays.equals(results, Scenario1.RAW_DATA[1]));
	}
	
	public void testExecuteComputedColumns(){
		AlgoContext reportContext = getTestContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);  
		
		//reportContext.set(ContextKeys.DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
		//reportContext.set(ContextKeys.GROUP_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
		
		mockAlgoInput.put(IOKeys.DATA_COLS, CalculatedColumnsScenario.DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.GROUP_COLS, CalculatedColumnsScenario.GROUP_COLUMNS); 
		
		classUnderTest.init(mockAlgoInput, reportContext);
		
		for(int i=0; i< CalculatedColumnsScenario.RAW_DATA.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
			classUnderTest.execute(dataRowEvent);
		
			Object[] results = (Object[])reportContext.get(ContextKeys.COMPUTED_CELL_VALUES);
		
			assertNotNull(results);
			assertEquals(8, results.length);
			assertTrue(Arrays.equals(results, CalculatedColumnsScenario.COMPUTED_VALUES[i]));
		}
	}
}