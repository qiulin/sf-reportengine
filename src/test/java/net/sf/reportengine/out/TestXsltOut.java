/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileOutputStream;
import java.io.StringWriter;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.test.ReportengineTC;

/**
 * @author dragos
 *
 */
public class TestXsltOut extends ReportengineTC {

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
	 * Test method for {@link net.sf.reportengine.out.XmlDOMReportOutput#open()}.
	 */
	public void testXSLT(){
        try{
            XsltOutput xsltOutTest= new XsltOutput("target/testXSLTOut.html");
            xsltOutTest.open();
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE));
            xsltOutTest.output(new CellProps.Builder("report title").build());
            xsltOutTest.endRow();
            
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS));
            xsltOutTest.output(new CellProps.Builder("col header").build());
            xsltOutTest.endRow();
            
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xsltOutTest.output(new CellProps.Builder("test value").build());
            xsltOutTest.endRow();
            
            xsltOutTest.close();            
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage()); 
        }
    }
	
	/**
	 * Test method for {@link net.sf.reportengine.out.XmlDOMReportOutput#open()}.
	 */
	public void testUtf8Output(){
        try{
        	StringWriter writer = new StringWriter(); 
            XsltOutput xsltOutTest= new XsltOutput();
            xsltOutTest.setOutputWriter(writer); 
            
            xsltOutTest.open();
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE));
            xsltOutTest.output(new CellProps.Builder("Βιβλία").build());
            xsltOutTest.endRow();
            
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS));
            xsltOutTest.output(new CellProps.Builder("col header").build());
            xsltOutTest.endRow();
            
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xsltOutTest.output(new CellProps.Builder("и канализация са от").build());
            xsltOutTest.endRow();
            
            xsltOutTest.close();            
            
            StringBuffer result = writer.getBuffer(); 
            assertNotNull(result); 
            assertTrue(result.indexOf("Βιβλία") >0);
            assertTrue(result.indexOf("col header") > 0);
            assertTrue(result.indexOf("и канализация са от") > 0);
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage()); 
        }
    }
	
	/**
	 * Test method for {@link net.sf.reportengine.out.XmlDOMReportOutput#open()}.
	 */
	public void testUtf8OutputToFile(){
        try{
            XsltOutput xsltOutTest= new XsltOutput();
            xsltOutTest.setFilePath("target/testXsltUtf8.html"); 
            
            xsltOutTest.open();
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE));
            xsltOutTest.output(new CellProps.Builder("Report title").build());
            xsltOutTest.endRow();
            
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS));
            xsltOutTest.output(new CellProps.Builder("Βιβλία").build());
            xsltOutTest.endRow();
            
            xsltOutTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            xsltOutTest.output(new CellProps.Builder("и канализация са от").build());
            xsltOutTest.endRow();
            
            xsltOutTest.close();            
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage()); 
        }
    }
	
}
