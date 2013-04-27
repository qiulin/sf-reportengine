/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileWriter;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestFoOutput {


	/**
	 * Test method for {@link net.sf.reportengine.out.FoOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutput() throws Exception{
		FoOutput classUnderTest;
		classUnderTest = new FoOutput(new FileWriter("./target/foOutput.fo"));
	
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps()); 
		
		classUnderTest.outputTitle(new TitleProps("report title", 3));
		
		classUnderTest.startHeaderRow(new RowProps(0)); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header1").rowNumber(0).build()); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header2").rowNumber(0).build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header3").rowNumber(0).build());
		classUnderTest.endHeaderRow(); 
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("first cell").rowNumber(0).build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("second cell").rowNumber(0).build());
		classUnderTest.outputDataCell(new CellProps.Builder("third cell").rowNumber(0).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(1)); 
		classUnderTest.outputDataCell(new CellProps.Builder("this cell has a colspan of 3").rowNumber(1).colspan(3).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(2)); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 1").rowNumber(2).build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 2").rowNumber(2).build());
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 3").rowNumber(2).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}

}
