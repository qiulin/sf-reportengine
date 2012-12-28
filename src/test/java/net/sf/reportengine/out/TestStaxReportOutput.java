/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.test.ReportengineTC;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos
 *
 */
public class TestStaxReportOutput extends ReportengineTC {
	
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

	public void testStaxOutput(){
        try{
            StaxReportOutput xmlOutTest = new StaxReportOutput("target/testStaxOutput.xml");
            xmlOutTest.open();
            
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE));
            xmlOutTest.output(new CellProps.Builder("this is the title").contentType(ReportContent.CONTENT_REPORT_TITLE).build());
            xmlOutTest.endRow(); 
            
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS));
            xmlOutTest.output(new CellProps.Builder("column 1").contentType(ReportContent.CONTENT_COLUMN_HEADERS).build());
            xmlOutTest.endRow(); 
            
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xmlOutTest.output(new CellProps.Builder("value row 1 col 1").build());
            xmlOutTest.endRow();
            
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xmlOutTest.output(new CellProps.Builder("value row 2 col 1").build());
            xmlOutTest.endRow();
            
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xmlOutTest.output(new CellProps.Builder("value row 3 col 1").build());
            xmlOutTest.endRow();
            xmlOutTest.close();            
        }catch(Throwable exc){
            exc.printStackTrace();
            assertTrue(false);
        }
    }
	
	
	public void testStaxUTF8Output(){
        try{
        	Writer writer = ReportIoUtils.createWriterFromPath("target/testStaxUtf8Out.xml");
            StaxReportOutput xmlOutTest = new StaxReportOutput(writer);
            xmlOutTest.open();
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xmlOutTest.output(new CellProps.Builder("Πρωτόκολλο Αζήτητων").build());
            xmlOutTest.endRow();
            
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xmlOutTest.output(new CellProps.Builder("Βιβλία").build());
            xmlOutTest.endRow();
            
            xmlOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xmlOutTest.output(new CellProps.Builder("и канализация са от").build());
            xmlOutTest.endRow();
            xmlOutTest.close();            
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
        }
    }
}
