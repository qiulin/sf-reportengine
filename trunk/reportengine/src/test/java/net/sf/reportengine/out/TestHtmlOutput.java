/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Test;
import org.junit.Assert; 


/**
 * @author balan
 *
 */
public class TestHtmlOutput  {

	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutputWithDefaultCss() {
		HtmlOutput classUnderTest = new HtmlOutput("target/cssInsideAndTitleAndMultipleColHead.html");
		
		classUnderTest.open();
		
		classUnderTest.outputTitle(new TitleProps("report title", 3));
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 0)); 
		classUnderTest.output(new CellProps.Builder("header11").build()); 
		classUnderTest.output(new CellProps.Builder("header12").build());
		classUnderTest.output(new CellProps.Builder("header13").build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 1)); 
		classUnderTest.output(new CellProps.Builder("header21").build()); 
		classUnderTest.output(new CellProps.Builder("header22").build());
		classUnderTest.output(new CellProps.Builder("header23").build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0)); 
		classUnderTest.output(new CellProps.Builder("first cell").build()); 
		classUnderTest.output(new CellProps.Builder("second cell").build());
		classUnderTest.output(new CellProps.Builder("third cell").build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 1)); 
		classUnderTest.output(new CellProps.Builder("this cell has a colspan of 3").colspan(3).build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 2)); 
		classUnderTest.output(new CellProps.Builder("row 3 value 1").build()); 
		classUnderTest.output(new CellProps.Builder("row 3 value 2").build());
		classUnderTest.output(new CellProps.Builder("row 3 value 3").build());
		classUnderTest.endRow(); 
		
		classUnderTest.close(); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutputWithExternalCss() throws Exception {
		HtmlOutput classUnderTest = new HtmlOutput();
		classUnderTest.setOutputWriter(new FileWriter("target/testHtmlOutHavingExternalCss.html"));
		classUnderTest.setCssPath("testReport.css");
		
		classUnderTest.open();
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA)); 
		classUnderTest.output(new CellProps.Builder("first cell").build()); 
		classUnderTest.output(new CellProps.Builder("second cell").build());
		classUnderTest.output(new CellProps.Builder("third cell").build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA)); 
		classUnderTest.output(new CellProps.Builder("this cell has a colspan of 3").colspan(3).build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA)); 
		classUnderTest.output(new CellProps.Builder("row 3 value 1").build()); 
		classUnderTest.output(new CellProps.Builder("row 3 value 2").build());
		classUnderTest.output(new CellProps.Builder("row 3 value 3").build());
		classUnderTest.endRow(); 
		
		classUnderTest.close(); 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutputUtf8() {
		Writer writer = ReportIoUtils.createWriterFromPath("target/testHtmlOutStreamUtf8.html"); 
			
		HtmlOutput classUnderTest = new HtmlOutput();
		classUnderTest.setOutputWriter(writer); 
		classUnderTest.open();
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA)); 
		classUnderTest.output(new CellProps.Builder("от Субсахарска").build()); 
		classUnderTest.output(new CellProps.Builder("Африка").build());
		classUnderTest.output(new CellProps.Builder("постига").build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA)); 
		classUnderTest.output(new CellProps.Builder("устойчиви резултати").colspan(3).build());
		classUnderTest.endRow(); 
		
		classUnderTest.close(); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testMultipleUseWithDifferentWriters() {
		StringWriter firstWriter = new StringWriter(); 
		HtmlOutput classUnderTest = new HtmlOutput(firstWriter);
		classUnderTest.open();
		classUnderTest.startRow(new RowProps(ReportContent.DATA)); 
		classUnderTest.output(new CellProps.Builder("от Субсахарска").build()); 
		classUnderTest.output(new CellProps.Builder("Африка").build());
		classUnderTest.output(new CellProps.Builder("постига").build());
		classUnderTest.endRow(); 
		classUnderTest.startRow(new RowProps(ReportContent.DATA)); 
		classUnderTest.output(new CellProps.Builder("устойчиви резултати").colspan(3).build());
		classUnderTest.endRow(); 
		classUnderTest.close(); 
		
		StringBuffer result = firstWriter.getBuffer(); 
		Assert.assertNotNull(result); 
		Assert.assertTrue(result.indexOf("от Субсахарска") >0);
		Assert.assertTrue(result.indexOf("Африка") > 0);
		Assert.assertTrue(result.indexOf("постига") >0);
		Assert.assertTrue(result.indexOf("устойчиви резултати") >0);
		
		//second use
		StringWriter newWriter = new StringWriter(); 
		classUnderTest.setOutputWriter(newWriter); 
		classUnderTest.open(); 
		classUnderTest.startRow(new RowProps(ReportContent.DATA));
		classUnderTest.output(new CellProps.Builder("test value").build());
		classUnderTest.endRow(); 
		classUnderTest.close(); 
		
		result = newWriter.getBuffer(); 
		Assert.assertNotNull(result);
		Assert.assertTrue(result.indexOf("test value") > 0);
	}	
}
