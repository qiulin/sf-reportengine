/**
 * 
 */
package net.sf.reportengine.in;

import net.sf.reportengine.util.ReportEngineArrayUtils;
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
	
	private MemoryReportInput classUnderTest = null;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		classUnderTest = new MemoryReportInput(DATA);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.MemoryReportInput#hasMoreRows()}.
	 */
	public void testHasMoreRows() {
		classUnderTest.open();
		assertTrue(classUnderTest.hasMoreRows());
		Object[] row = classUnderTest.nextRow();
		assertTrue(ReportEngineArrayUtils.equalArrays(row, DATA[0]));
		
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow();
		assertTrue(ReportEngineArrayUtils.equalArrays(row, DATA[1]));
		
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow();
		assertTrue(ReportEngineArrayUtils.equalArrays(row, DATA[2]));
		
		assertFalse(classUnderTest.hasMoreRows());
	}

	/**
	 * Test method for {@link net.sf.reportengine.in.MemoryReportInput#getColumnsCount()}.
	 */
//	public void testGetColumnsCount() {
//		classUnderTest.open();
//		assertEquals(4,classUnderTest.getColumnsCount());
//	}

}
