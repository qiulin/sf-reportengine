/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileOutputStream;

import net.sf.reportengine.core.ReportContent;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestPdfOutput {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * @throws java.lang.Exception
	 */
	@After
	public void tearDown() throws Exception {
	}

	/**
	 * Test method for {@link net.sf.reportengine.out.PdfOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutput() throws Exception {
		PdfOutput classUnderTest = new PdfOutput(new FileOutputStream("./target/pdfFromFoOutput.pdf"));
		
		classUnderTest.open();
		
		classUnderTest.startRow(new RowProps(ReportContent.REPORT_TITLE, 0));
		classUnderTest.output(new CellProps.Builder("report title").colspan(3).build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 0)); 
		classUnderTest.output(new CellProps.Builder("header1").build()); 
		classUnderTest.output(new CellProps.Builder("header2").build());
		classUnderTest.output(new CellProps.Builder("header3").build());
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

}
