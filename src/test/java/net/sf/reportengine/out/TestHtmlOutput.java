/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;

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
		
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputWithDefaultCss() {
		try {
			classUnderTest = new HtmlOutput(new FileOutputStream("target/testHtmlOutHavingCssInside.html"), "UTF-8");
		
			classUnderTest.open();
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
			classUnderTest.output(new CellProps.Builder("header1").build()); 
			classUnderTest.output(new CellProps.Builder("header2").build());
			classUnderTest.output(new CellProps.Builder("header3").build());
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
			classUnderTest.output(new CellProps.Builder("first cell").build()); 
			classUnderTest.output(new CellProps.Builder("second cell").build());
			classUnderTest.output(new CellProps.Builder("third cell").build());
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
			classUnderTest.output(new CellProps.Builder("this cell has a colspan of 3").colspan(3).build());
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
			classUnderTest.output(new CellProps.Builder("row 3 value 1").build()); 
			classUnderTest.output(new CellProps.Builder("row 3 value 2").build());
			classUnderTest.output(new CellProps.Builder("row 3 value 3").build());
			classUnderTest.endRow(); 
			
			classUnderTest.close(); 
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputWithExternalCss() {
		try {
			classUnderTest = new HtmlOutput();
			classUnderTest.setWriter(new FileWriter("target/testHtmlOutHavingExternalCss.html"));
			classUnderTest.setCssPath("testReport.css");
			
			classUnderTest.open();
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
			classUnderTest.output(new CellProps.Builder("first cell").build()); 
			classUnderTest.output(new CellProps.Builder("second cell").build());
			classUnderTest.output(new CellProps.Builder("third cell").build());
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
			classUnderTest.output(new CellProps.Builder("this cell has a colspan of 3").colspan(3).build());
			classUnderTest.endRow(); 
			
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA)); 
			classUnderTest.output(new CellProps.Builder("row 3 value 1").build()); 
			classUnderTest.output(new CellProps.Builder("row 3 value 2").build());
			classUnderTest.output(new CellProps.Builder("row 3 value 3").build());
			classUnderTest.endRow(); 
			
			classUnderTest.close(); 
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		} catch(IOException ioEx){
			fail(ioEx.getMessage());
		}
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputUtf8() {
		try{
			classUnderTest = new HtmlOutput(new FileOutputStream("target/testHtmlOutStreamUtf8.html"), "UTF-8");
			
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
		} catch (FileNotFoundException e) {
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testMultipleUseWithDifferentWriters() {
			StringWriter firstWriter = new StringWriter(); 
			classUnderTest = new HtmlOutput(firstWriter);
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
			
			StringBuffer result = firstWriter.getBuffer(); 
			assertNotNull(result); 
			assertTrue(result.indexOf("от Субсахарска") >0);
			assertTrue(result.indexOf("Африка") > 0);
			assertTrue(result.indexOf("постига") >0);
			assertTrue(result.indexOf("устойчиви резултати") >0);
			
			//second use
			StringWriter newWriter = new StringWriter(); 
			classUnderTest.setWriter(newWriter); 
			classUnderTest.open(); 
			classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
			classUnderTest.output(new CellProps.Builder("test value").build());
			classUnderTest.endRow(); 
			classUnderTest.close(); 
			
			result = newWriter.getBuffer(); 
			assertNotNull(result);
			assertTrue(result.indexOf("test value") > 0);
	}	
}
