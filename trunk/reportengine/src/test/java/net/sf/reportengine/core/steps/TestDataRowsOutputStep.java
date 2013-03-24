/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.MatrixUtils;

/**
 * @author dragos
 *
 */
public class TestDataRowsOutputStep extends ReportAlgorithmStepTC {
	
	private DataRowsOutputStep classUnderTest = null;
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.impl.ReportAlgorithmStepTestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		classUnderTest = new DataRowsOutputStep();
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.DataRowsOutputStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteScenario1() {
		ReportContext reportContext = getTestContext();
		
		reportContext.setInput(Scenario1.INPUT);
		reportContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		reportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
		classUnderTest.init(reportContext); 

		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_1);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_1);
		classUnderTest.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_2);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_3);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_3);
		classUnderTest.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_4);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_4);
		classUnderTest.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_5);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_5);
		classUnderTest.execute(dataRowEvent);
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_6);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_6);
		classUnderTest.execute(dataRowEvent);
		
		CellProps[][] resultCellMatrix = getTestOutput().getCellMatrix();
		assertTrue(MatrixUtils.compareMatrices(Scenario1.OUTPUT_DATA, resultCellMatrix));
	}

}
