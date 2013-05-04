/**
 * 
 */
package net.sf.reportengine.in;

import static org.junit.Assert.*;

import net.sf.reportengine.util.ReportIoUtils;

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
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile.tmp")); 
		
		assertFalse(classUnderTest.isEmpty());
		
		assertNotNull(classUnderTest.pop()); 
		assertNotNull(classUnderTest.pop()); 
		assertNotNull(classUnderTest.pop()); 
		
		assertTrue(classUnderTest.isEmpty());
		assertTrue(classUnderTest.isEmpty());
		
		classUnderTest.close();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#close()}.
	 */
	@Test
	public void testClose() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile.tmp")); 
		
		assertNotNull(classUnderTest.pop()); 
		assertNotNull(classUnderTest.pop()); 
		
		//closing while still elements are in the queue
		classUnderTest.close();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#peek()}.
	 */
	@Test
	public void testPeek() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile.tmp")); 
		
		assertFalse(classUnderTest.isEmpty());
		assertNotNull(classUnderTest.peek()); 
		assertNotNull(classUnderTest.peek()); 
		assertNotNull(classUnderTest.peek()); 
		assertNotNull(classUnderTest.peek()); 
		assertNotNull(classUnderTest.peek()); 
		
		assertNotNull(classUnderTest.pop()); 
		assertNotNull(classUnderTest.pop());
		assertNotNull(classUnderTest.pop());
		
		assertNull(classUnderTest.peek());
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.RowsDataFileBuffer#pop()}.
	 */
	@Test
	public void testPop() {
		RowsDataFileBuffer classUnderTest = new RowsDataFileBuffer(ReportIoUtils.createInputStreamFromClassPath("ExternalSortedAndSerializedFile.tmp")); 
		
		assertNotNull(classUnderTest.pop()); 
		assertNotNull(classUnderTest.pop());
		assertNotNull(classUnderTest.pop());
		
		classUnderTest.close(); 
	}

}
