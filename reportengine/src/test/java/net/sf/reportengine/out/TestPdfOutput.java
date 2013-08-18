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
	 * Test method for {@link net.sf.reportengine.out.PdfOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutput() throws Exception {
		PdfOutput classUnderTest = new PdfOutput(new FileOutputStream("./target/pdfFromFoOutput.pdf"));
		
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.outputTitle(new TitleProps("report title", 3));
		
		classUnderTest.startHeaderRow(new RowProps(0)); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header1").build()); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header2").build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header3").build());
		classUnderTest.endHeaderRow(); 
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("first cell").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("second cell").build());
		classUnderTest.outputDataCell(new CellProps.Builder("third cell").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(1)); 
		classUnderTest.outputDataCell(new CellProps.Builder("this cell has a colspan of 3").colspan(3).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(2)); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 1").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 2").build());
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 3").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
	
	
	@Test
	public void testUtf8Output() throws Exception {
		PdfOutput classUnderTest = new PdfOutput(new FileOutputStream("./target/pdfOutputUtf8.pdf"));
		
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.outputTitle(new TitleProps("Τη γλώσσα μου έδωσαν ελληνική", 3));
		
		classUnderTest.startHeaderRow(new RowProps(0)); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header1").build()); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header2").build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header3").build());
		classUnderTest.endHeaderRow(); 
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("Действията").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("във връзка с").build());
		classUnderTest.outputDataCell(new CellProps.Builder("подобряване на").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
}
