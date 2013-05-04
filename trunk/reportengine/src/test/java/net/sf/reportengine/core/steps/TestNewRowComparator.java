package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.Scenario1;

import org.junit.Test;
import static org.junit.Assert.*; 

public class TestNewRowComparator {

	@Test
	public void testCompare() {
		NewRowComparator classUnderTest = new NewRowComparator(Scenario1.GROUPING_COLUMNS, Scenario1.DATA_COLUMNS);
		
		//first 3 cols are group cols, last 3 are data cols
		NewRowEvent row1 = new NewRowEvent(new String[]{"1","2","3", "4","5","6"}); 
		NewRowEvent row2 = new NewRowEvent(new String[]{"1","2","3", "3","3","3"}); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		row1 = new NewRowEvent(new String[]{"1","2","3", "4","5","6"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		row1 = new NewRowEvent(new String[]{"1","2","2", "2","3","1"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		row1 = new NewRowEvent(new String[]{"2","2","2", "4","5","6"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		row1 = new NewRowEvent(new String[]{"1","2","2", "2","2","7"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertTrue(classUnderTest.compare(row1, row2) > 0);
		
		row1 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertEquals(0, classUnderTest.compare(row1, row2));
		
		row1 = new NewRowEvent(new String[]{"1","2","1", "2","2","2"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertEquals(-1, classUnderTest.compare(row1, row2));
		
		row1 = new NewRowEvent(new String[]{"1","1","1", "2","2","2"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertEquals(-1, classUnderTest.compare(row1, row2));
		
		row1 = new NewRowEvent(new String[]{"0","2","2", "2","2","2"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertEquals(-1, classUnderTest.compare(row1, row2));
		
		row1 = new NewRowEvent(new String[]{"1","2","2", "1","2","2"}); 
		row2 = new NewRowEvent(new String[]{"1","2","2", "2","2","2"}); 
		assertEquals(-1, classUnderTest.compare(row1, row2));
	}
}
