/**
 * 
 */
package net.sf.reportengine.out;

import java.io.Writer;

import net.sf.reportengine.test.ReportengineTC;

/**
 * @author balan
 *
 */
public class TestHtmlOutput extends ReportengineTC {

	private HtmlOutput classUnderTest; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		Writer testWriter = createTestWriter("testHtmlOutput.html");
		classUnderTest = new HtmlOutput(testWriter);
	}

	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputUtf8() {
		classUnderTest.open();
		
		classUnderTest.startRow(); 
		classUnderTest.output(new CellProps("от Субсахарска")); 
		classUnderTest.output(new CellProps("Африка"));
		classUnderTest.output(new CellProps("постига"));
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(); 
		classUnderTest.output(new CellProps("устойчиви резултати", 3));
		classUnderTest.endRow(); 
		
		classUnderTest.close(); 
	}
}
