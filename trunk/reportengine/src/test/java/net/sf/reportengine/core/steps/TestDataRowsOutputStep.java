/**
 * 
 */
package net.sf.reportengine.core.steps;

import junit.framework.Assert;
import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.MatrixUtils;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestDataRowsOutputStep {
	
	@Test
	public void testExecuteScenario1() {
		DataRowsOutputStep classUnderTest = new DataRowsOutputStep();
		
		ReportContext reportContext = new DefaultReportContext(); 
		
		reportContext.setInput(Scenario1.INPUT);
		reportContext.setOutput(Scenario1.OUTPUT); 
		reportContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		reportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
		reportContext.set(ContextKeys.DATA_ROW_COUNT, 0); 
		classUnderTest.init(reportContext); 
		
		Assert.assertNotNull(reportContext.get(ContextKeys.DATA_ROW_COUNT));
		Assert.assertEquals(0, reportContext.get(ContextKeys.DATA_ROW_COUNT));
		
		
		NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_1);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_1);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_1);
		classUnderTest.execute(dataRowEvent);
		
		Assert.assertNotNull(reportContext.get(ContextKeys.DATA_ROW_COUNT));
		Assert.assertEquals(1, reportContext.get(ContextKeys.DATA_ROW_COUNT));
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_2);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_2);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_2);
		classUnderTest.execute(dataRowEvent);
		
		Assert.assertNotNull(reportContext.get(ContextKeys.DATA_ROW_COUNT));
		Assert.assertEquals(2, reportContext.get(ContextKeys.DATA_ROW_COUNT));
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_3);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_3);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_3);
		classUnderTest.execute(dataRowEvent);
		
		Assert.assertNotNull(reportContext.get(ContextKeys.DATA_ROW_COUNT));
		Assert.assertEquals(3, reportContext.get(ContextKeys.DATA_ROW_COUNT));
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_4);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_4);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_4);
		classUnderTest.execute(dataRowEvent);
		
		Assert.assertNotNull(reportContext.get(ContextKeys.DATA_ROW_COUNT));
		Assert.assertEquals(4, reportContext.get(ContextKeys.DATA_ROW_COUNT));
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_5);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_5);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_5);
		classUnderTest.execute(dataRowEvent);
		
		Assert.assertNotNull(reportContext.get(ContextKeys.DATA_ROW_COUNT));
		Assert.assertEquals(5, reportContext.get(ContextKeys.DATA_ROW_COUNT));
		
		dataRowEvent = new NewRowEvent(Scenario1.ROW_OF_DATA_6);
		reportContext.set(ContextKeys.COMPUTED_CELL_VALUES, Scenario1.ROW_OF_DATA_6);
		reportContext.set(ContextKeys.FORMATTED_CELL_VALUES, Scenario1.ROW_OF_DATA_6);
		classUnderTest.execute(dataRowEvent);
		
		Assert.assertNotNull(reportContext.get(ContextKeys.DATA_ROW_COUNT));
		Assert.assertEquals(6, reportContext.get(ContextKeys.DATA_ROW_COUNT));
		
		CellProps[][] resultCellMatrix = Scenario1.OUTPUT.getCellMatrix();
		Assert.assertTrue(MatrixUtils.compareMatrices(Scenario1.OUTPUT_DATA, resultCellMatrix));
	}

}
