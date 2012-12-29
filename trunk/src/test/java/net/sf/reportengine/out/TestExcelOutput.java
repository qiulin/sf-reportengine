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
		classUnderTest.startRow(new RowProps(ReportContent.DATA));
		classUnderTest.output(new CellProps.Builder("value here").build());
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
	public void testOutput() {
		classUnderTest = new ExcelOutput(createTestOutputFile("testExcel.xls"));
		classUnderTest.open();
		classUnderTest.startRow(new RowProps(ReportContent.DATA));
		classUnderTest.output(new CellProps.Builder("value here").build());
		classUnderTest.endRow();
		classUnderTest.close();
	}
	
	public void testOutputUtf8(){
		classUnderTest = new ExcelOutput(createTestOutputFile("testExcelUtf8.xls"));
		classUnderTest.open();
		classUnderTest.startRow(new RowProps(ReportContent.DATA));
		classUnderTest.output(new CellProps.Builder("А Б В Г Д Е Ё Ж З И Й К Л М Н О П Р С Т У Ф Х Ц Ч Ш Щ Ъ Ы Ь Э Ю Я").build());
		classUnderTest.endRow();
		classUnderTest.startRow(new RowProps()); 
		classUnderTest.output(new CellProps.Builder("ά έ ή ί ό ύ ώ").build()); 
		classUnderTest.endRow(); 
		classUnderTest.close();
	}

}
