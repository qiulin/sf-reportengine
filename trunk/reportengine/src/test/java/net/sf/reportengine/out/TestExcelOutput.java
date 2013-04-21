/**
 * 
 */
package net.sf.reportengine.out;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

import net.sf.reportengine.core.ReportContent;
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
	public void testOutputEmptyConstructor() {
		classUnderTest = new ExcelOutput();
		classUnderTest.open();
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0));
		classUnderTest.output(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endRow();
		classUnderTest.close();
		
		OutputStream os = classUnderTest.getOutputStream();
		assertNotNull(os); 
		
		assertTrue(os instanceof ByteArrayOutputStream); 
		ByteArrayOutputStream baos = (ByteArrayOutputStream)os; 
		String result = baos.toString();
		assertNotNull(result); 
		assertTrue(result.indexOf("value here") > 0);
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.ExcelOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputOneCell() {
		classUnderTest = new ExcelOutput("./target/OneCell.xls");
		classUnderTest.open();
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0));
		classUnderTest.output(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endRow();
		classUnderTest.close();
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.out.ExcelOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputTitleAndOneCell() {
		classUnderTest = new ExcelOutput("./target/TitleAndOneCell.xls");
		classUnderTest.open();
		classUnderTest.outputTitle(new TitleProps("this is the title", 1)); 
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0));
		classUnderTest.output(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endRow();
		classUnderTest.close();
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.ExcelOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputTitleAndMultipleColHeadRows() {
		classUnderTest = new ExcelOutput("./target/TitleAndMultipleColHeadRows.xls");
		classUnderTest.open();
		classUnderTest.outputTitle(new TitleProps("this is the title", 1)); 
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 0));
		classUnderTest.output(new CellProps.Builder("colum header row 1").contentType(ReportContent.COLUMN_HEADER).rowNumber(0).build());
		classUnderTest.endRow();
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 1));
		classUnderTest.output(new CellProps.Builder("colum header row 2").contentType(ReportContent.COLUMN_HEADER).rowNumber(1).build());
		classUnderTest.endRow();
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 2));
		classUnderTest.output(new CellProps.Builder("colum header row 3").contentType(ReportContent.COLUMN_HEADER).rowNumber(1).build());
		classUnderTest.endRow();
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0));
		classUnderTest.output(new CellProps.Builder("value here").rowNumber(0).build());
		classUnderTest.endRow();
		
		classUnderTest.close();
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.ExcelOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputManyRowsNoTitle() {
		classUnderTest = new ExcelOutput("./target/ManyRowsWithColHeadAndNoTitle.xls");
		classUnderTest.open();
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 0));
		classUnderTest.output(new CellProps.Builder("column header")
										.contentType(ReportContent.COLUMN_HEADER)
										.rowNumber(0)
										.build());
		classUnderTest.endRow();
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0));
		classUnderTest.output(new CellProps.Builder("1st row with data")
											.contentType(ReportContent.DATA)
											.rowNumber(0)
											.build());
		classUnderTest.endRow();
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 1));
		classUnderTest.output(new CellProps.Builder("2nd row with data")
											.contentType(ReportContent.DATA)
											.rowNumber(1)
											.build());
		classUnderTest.endRow();		
		classUnderTest.close();
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.out.ExcelOutput#output(net.sf.reportengine.out.CellProps)}.
	 */
	public void testOutputManyRowsWithTitle() {
		classUnderTest = new ExcelOutput("./target/ManyRowsWithColHeadAndTitle.xls");
		classUnderTest.open();
		
		classUnderTest.outputTitle(new TitleProps("this is a long title", 1)); 
		
		
		classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER, 0));
		classUnderTest.output(new CellProps.Builder("column header")
										.contentType(ReportContent.COLUMN_HEADER)
										.rowNumber(0)
										.build());
		classUnderTest.endRow();
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0));
		classUnderTest.output(new CellProps.Builder("1st row with data")
											.contentType(ReportContent.DATA)
											.rowNumber(0)
											.build());
		classUnderTest.endRow();
		
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 1));
		classUnderTest.output(new CellProps.Builder("2nd row with data")
											.contentType(ReportContent.DATA)
											.rowNumber(1)
											.build());
		classUnderTest.endRow();
		
		classUnderTest.close();
	}
	
	
	public void testOutputUtf8(){
		classUnderTest = new ExcelOutput(createTestOutputFile("Utf8.xls"));
		classUnderTest.open();
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 0));
		classUnderTest.output(new CellProps.Builder("А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я").build());
		classUnderTest.endRow();
		classUnderTest.startRow(new RowProps(ReportContent.DATA, 1)); 
		classUnderTest.output(new CellProps.Builder("ά έ ή ί ό ύ ώ").build()); 
		classUnderTest.endRow(); 
		classUnderTest.close();
	}

}
