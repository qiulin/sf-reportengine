/**
 * 
 */
package net.sf.reportengine.core.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestDetectSortableDataStep {
	
	
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.DetectSortableDataStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	@Test
	public void testInit() {
		ReportContext mockContext = new DefaultReportContext(); 
		mockContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		mockContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		
		DetectSortableDataStep classUnderTest = new DetectSortableDataStep(); 
		classUnderTest.init(mockContext);
		
		Object sortableDataColsIndexesAsObject = mockContext.get(ContextKeys.SORTABLE_DATA_COLS_INDEXES); 
		assertNotNull(sortableDataColsIndexesAsObject);
		assertTrue(sortableDataColsIndexesAsObject instanceof ArrayList);
		
		ArrayList sortableDataColsIndexes = (ArrayList)sortableDataColsIndexesAsObject; 
		assertEquals(0, sortableDataColsIndexes.size());
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.DetectSortableDataStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	@Test
	public void testExecute() {
		ReportContext mockContext = new DefaultReportContext(); 
		mockContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		mockContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		
		DetectSortableDataStep classUnderTest = new DetectSortableDataStep(); 
		classUnderTest.init(mockContext);
		
		classUnderTest.execute(new NewRowEvent(Scenario1.ROW_OF_DATA_1));
		
		Object arrangedForSort = mockContext.get(ContextKeys.ARRANGED_FOR_SORT_ROW); 
		assertNotNull(arrangedForSort); 
		
	}
}
