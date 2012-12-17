package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;
import junit.framework.TestCase;

public class TestFreemarkerOutput extends TestCase {
	
	private FreemarkerOutput classUnderTest;
	
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new FreemarkerOutput("target/reportOutput.html"); 
	}

	protected void tearDown() throws Exception {
		super.tearDown();
	}

	public void testOpen() {
		classUnderTest.open();
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE, 0)); 
		classUnderTest.output(new CellProps.Builder("test value").colspan(2).build());
		classUnderTest.output(new CellProps.Builder(" span 1").colspan(1).build()); 
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS, 1));
		classUnderTest.output(new CellProps.Builder(" cell 1,0").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 1,1").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 1,2").colspan(1).build());
		classUnderTest.endRow(); 
		
		classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA, 2));
		classUnderTest.output(new CellProps.Builder(" cell 2,0").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 2,1").colspan(1).build());
		classUnderTest.output(new CellProps.Builder(" cell 2,2").colspan(1).build());
		classUnderTest.endRow();
		
		classUnderTest.close(); 
	}

}
