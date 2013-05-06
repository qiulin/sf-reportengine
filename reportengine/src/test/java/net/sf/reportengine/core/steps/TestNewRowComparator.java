package net.sf.reportengine.core.steps;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.SortScenario;

import org.junit.Test;

public class TestNewRowComparator {

	@Test
	public void testCompare() {
		//first 3 cols are group cols, last 3 are data cols
		//for data columns only the last two columns need ordering 
		//the last one has a higher order priority than one before last
		NewRowComparator classUnderTest = new NewRowComparator(
													SortScenario.GROUPING_COLUMNS, 
													SortScenario.DATA_COLUMNS);
		
		//same grouping columns but, the third data column has a diff value (3 vs 6)
		NewRowEvent row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","3", "4","5","6"})); 
		NewRowEvent row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","3", "3","3","3"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//same grouping columns but, the second data column has a diff value (3 vs 5)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","5","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","3","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//same grouping columns but, the third data column has a diff value (1 vs 2)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","1","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","3","1"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//the third grouping column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","3", "4","5","6"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//first group column is different (1 vs 2)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"2","2","2", "4","5","6"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		//same values on the rows that matter ( the first data row doesn't matter)
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "1000","2","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2000","2","2"})); 
		assertEquals(0, classUnderTest.compare(row1, row2));
		
		
		//the third group column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","1", "2","2","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
		
		//the second group column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","2","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","2","2", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
		
		//the second data column is different
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","1","2"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "2","2","2"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
		
		//
		row1 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","1","1"})); 
		row2 = new NewRowEvent(Arrays.asList(new Object[]{"1","1","1", "1","1","7"})); 
		assertTrue(classUnderTest.compare(row1, row2) < 0);
	}
}
