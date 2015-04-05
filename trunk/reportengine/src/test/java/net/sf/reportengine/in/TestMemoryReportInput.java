/**
 * 
 */
package net.sf.reportengine.in;

import java.util.Arrays;

import junit.framework.TestCase;

/**
 * @author dragos
 *
 */
public class TestMemoryReportInput extends TestCase {
	
	private Object[][] DATA = new Object[][]{
			new String[]{"a","b","c","d"},
			new String[]{"1","2","3","4"},
			new String[]{"x","y","z","t"}
	};
	
	private ArrayTableInput classUnderTest = null;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		classUnderTest = new ArrayTableInput(DATA);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.ArrayTableInput#hasMoreRows()}.
	 */
	public void testHasMoreRows() {
		classUnderTest.open();
		assertTrue(classUnderTest.hasMoreRows());
		
		assertTrue(Arrays.asList(DATA[0]).equals(classUnderTest.nextRow()));
		
		assertTrue(classUnderTest.hasMoreRows());
		assertTrue(Arrays.asList(DATA[1]).equals(classUnderTest.nextRow()));
		
		assertTrue(classUnderTest.hasMoreRows());
		assertTrue(Arrays.asList(DATA[2]).equals(classUnderTest.nextRow()));
		
		assertFalse(classUnderTest.hasMoreRows());
	}
}
