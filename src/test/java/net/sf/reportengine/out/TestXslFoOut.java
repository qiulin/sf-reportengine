/**
 * 
 */
package net.sf.reportengine.out;

import org.apache.xmlgraphics.util.MimeConstants;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.test.ReportengineTC;

/**
 * @author dragos balan
 *
 */
public class TestXslFoOut extends ReportengineTC {
	
	
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
	
	
	public void testPDFOutput(){
        try{
            XslFoOutput classUnderTest= new XslFoOutput(createTestOutputFile("testXslFoOut.pdf"));
            classUnderTest.open();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE)); 
            classUnderTest.output(new CellProps("report title", 1, ReportContent.CONTENT_REPORT_TITLE));
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps("column1 ", 1, ReportContent.CONTENT_COLUMN_HEADERS));
            classUnderTest.endRow();
            
           
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 1 col 1"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 2 col 1"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 3 col 1"));
            classUnderTest.endRow();
            classUnderTest.close();  
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
        }
    }
	
	
	public void testPdfUtf8Output(){
        try{
            XslFoOutput classUnderTest= new XslFoOutput("./target/testUtf8Out.pdf");
            classUnderTest.open();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE)); 
            classUnderTest.output(new CellProps("Действията", 1, ReportContent.CONTENT_REPORT_TITLE));
            classUnderTest.endRow(); 
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps("column1 ", 1, ReportContent.CONTENT_COLUMN_HEADERS));
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("Πρωτόκολλο Αζήτητων"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("Βιβλία"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("Ανασυσκευασία"));
            classUnderTest.endRow();
            classUnderTest.close();  
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
        }
    }
	
	public void testPNGOutput(){
        try{
            XslFoOutput classUnderTest= new XslFoOutput(createTestOutputFile("testXslFoOut.png"), MimeConstants.MIME_PNG);
            classUnderTest.open();
            
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE)); 
            classUnderTest.output(new CellProps("report title", 1, ReportContent.CONTENT_REPORT_TITLE));
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps("column1 ", 1, ReportContent.CONTENT_COLUMN_HEADERS));
            classUnderTest.endRow();
            
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 1 col 1"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 2 col 1"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 3 col 1"));
            classUnderTest.endRow();
            classUnderTest.close();  
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
        }
    }
	
	public void testPostScriptOutput(){
		 try{
            XslFoOutput classUnderTest= new XslFoOutput(
            		createTestOutputFile("testXslFoOut.ps"), 
            							MimeConstants.MIME_POSTSCRIPT);
            classUnderTest.open();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_REPORT_TITLE)); 
            classUnderTest.output(new CellProps("report title", 1, ReportContent.CONTENT_REPORT_TITLE));
            classUnderTest.endRow(); 
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps("column1 ", 1, ReportContent.CONTENT_COLUMN_HEADERS));
            classUnderTest.endRow();
            
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 1 col 1"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 2 col 1"));
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps("row 3 col 1"));
            classUnderTest.endRow();
            classUnderTest.close();  
       }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
       }
	}	
}
