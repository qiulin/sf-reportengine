/**
 * 
 */
package net.sf.reportengine.out;

import java.io.Writer;

import net.sf.reportengine.core.ReportContent;
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
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
		classUnderTest.output(new CellProps.Builder("от Субсахарска").build()); 
		classUnderTest.output(new CellProps.Builder("Африка").build());
		classUnderTest.output(new CellProps.Builder("постига").build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
		classUnderTest.output(new CellProps.Builder("устойчиви резултати").colspan(3).build());
		classUnderTest.endRow(); 
		
		classUnderTest.close(); 
	}
}
