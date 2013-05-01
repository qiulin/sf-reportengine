/**
 * 
 */
package net.sf.reportengine.core.steps;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.MatrixUtils;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestInMemorySortStep {
	
	private static Object[][] EXPECTED_RESULT = new Object[][]{
			Scenario1.ROW_OF_DATA_4,
			Scenario1.ROW_OF_DATA_5, 
			Scenario1.ROW_OF_DATA_3, 
			Scenario1.ROW_OF_DATA_2, 
			Scenario1.ROW_OF_DATA_1,
			Scenario1.ROW_OF_DATA_6
	};
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.InMemorySortStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	@Test
	public void testExecute() {
		InMemorySortStep classUnderTest = new InMemorySortStep(); 
		
		ReportContext mockContext = new DefaultReportContext(); 
		mockContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		mockContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS); 
		
		classUnderTest.init(mockContext); 
		
		mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_6); 
		classUnderTest.execute(new NewRowEvent(null)); 
		
		mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_1); 
		classUnderTest.execute(new NewRowEvent(null)); 
		
		mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_2); 
		classUnderTest.execute(new NewRowEvent(null)); 
		
		mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_3); 
		classUnderTest.execute(new NewRowEvent(null)); 
		
		mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_4); 
		classUnderTest.execute(new NewRowEvent(null)); 
		
		mockContext.set(ContextKeys.ARRANGED_FOR_SORT_ROW, Scenario1.ROW_OF_DATA_5); 
		classUnderTest.execute(new NewRowEvent(null)); 
		
		
		//
		classUnderTest.exit(mockContext); 
		
		Object[][] result = (Object[][])mockContext.get(ContextKeys.IN_MEM_SORTED_RESULT); 
		assertNotNull(result); 
		assertEquals(6, result.length);
		
		MatrixUtils.logMatrix(result); 
		assertTrue(MatrixUtils.compareMatrices(EXPECTED_RESULT, result));
	}
}
