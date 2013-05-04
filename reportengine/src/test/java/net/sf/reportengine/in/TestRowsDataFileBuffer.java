/**
 * 
 */
package net.sf.reportengine.in;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.ObjectInputStream;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.NewRowEventWrapper;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Ignore;
import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestRowsDataFileBuffer {

	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#isEmpty()}.
	 */
	@Test
	public void testIsEmpty() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll()); 
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll()); 
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll()); 
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.poll());
		assertTrue(classUnderTest.isEmpty()); 
		
		assertTrue(classUnderTest.isEmpty()); 
		assertTrue(classUnderTest.isEmpty()); 
		assertTrue(classUnderTest.isEmpty()); 
		
		classUnderTest.close();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#close()}.
	 */
	@Test
	public void testClose() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertNotNull(classUnderTest.poll()); 
		assertNotNull(classUnderTest.poll()); 
		
		//closing while still elements are in the queue
		classUnderTest.close();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#peek()}.
	 */
	@Test
	public void testPeek() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		NewRowEvent newRow = classUnderTest.peek();
		assertNotNull(newRow); 
		assertArrayEquals(new String[]{"1", "1", "1", "1", "1" , "1"}, newRow.getInputDataRow());
		
		classUnderTest.poll(); 
		newRow = classUnderTest.peek(); 
		assertArrayEquals(new String[]{"1", "2", "2", "2", "2", "2"}, newRow.getInputDataRow());
		
		classUnderTest.poll(); 
		newRow = classUnderTest.peek(); 
		assertArrayEquals(new String[]{"1", "2", "3", "3", "3", "3"}, newRow.getInputDataRow());
		
		classUnderTest.poll(); 
		newRow = classUnderTest.peek(); 
		assertArrayEquals(new String[]{"1", "2", "3", "4", "5", "6"}, newRow.getInputDataRow());
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#poll()}.
	 */
	@Test
	public void testPoll1() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertNotNull(classUnderTest.poll()); 
		assertNotNull(classUnderTest.poll());
		assertNotNull(classUnderTest.poll());
		assertNotNull(classUnderTest.poll()); 
		
		assertTrue(classUnderTest.poll() == null); 
		assertTrue(classUnderTest.poll() == null); 
		assertTrue(classUnderTest.poll() == null); 
		assertTrue(classUnderTest.poll() == null); 
		
		classUnderTest.close(); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#poll()}.
	 */
	@Test
	public void testPoll2() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp")); 
		
		assertArrayEquals(Scenario1.ROW_OF_DATA_4, classUnderTest.poll().getInputDataRow());
		assertArrayEquals(Scenario1.ROW_OF_DATA_3, classUnderTest.poll().getInputDataRow());
		assertArrayEquals(Scenario1.ROW_OF_DATA_2, classUnderTest.poll().getInputDataRow());
		assertArrayEquals(Scenario1.ROW_OF_DATA_1, classUnderTest.poll().getInputDataRow());
		
		classUnderTest.close(); 
	}
	
	/**
	 * show the contents of the test file
	 * @throws Exception
	 */
	@Ignore
	public void caca() throws Exception{
		ObjectInputStream is = new ObjectInputStream(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile1.tmp"));
		NewRowEventWrapper obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
		obj = (NewRowEventWrapper)is.readObject();
		System.out.println(obj);
	}

}
