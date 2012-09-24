/**
 * 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.test.ReportengineTC;

/**
 * @author dragos
 *
 */
public class TestExcelOutput extends ReportengineTC {
	
	private ExcelOutput classUnderTest ;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new ExcelOutput(createTestOutputFile("testExcel.xls"));
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.out.ExcelOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutput() {
		classUnderTest.open();
		classUnderTest.startRow();
		classUnderTest.output(new CellProps("value here"));
		classUnderTest.endRow();
		classUnderTest.close();
	}

}
