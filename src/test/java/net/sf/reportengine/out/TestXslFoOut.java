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
            classUnderTest.output(new CellProps.Builder("report title").contentType(ReportContent.CONTENT_REPORT_TITLE).build());
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.CONTENT_COLUMN_HEADERS).build());
            classUnderTest.endRow();
            
           
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 3 col 1").build());
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
            classUnderTest.output(new CellProps.Builder("Действията").contentType(ReportContent.CONTENT_REPORT_TITLE).build());
            classUnderTest.endRow(); 
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.CONTENT_COLUMN_HEADERS).build());
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("Πρωτόκολλο Αζήτητων").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("Βιβλία").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("Ανασυσκευασία").build());
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
            classUnderTest.output(new CellProps.Builder("report title").contentType(ReportContent.CONTENT_REPORT_TITLE).build());
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.CONTENT_COLUMN_HEADERS).build());
            classUnderTest.endRow();
            
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 3 col 1").build());
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
            classUnderTest.output(new CellProps.Builder("report title").contentType(ReportContent.CONTENT_REPORT_TITLE).build());
            classUnderTest.endRow(); 
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_COLUMN_HEADERS)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.CONTENT_COLUMN_HEADERS).build());
            classUnderTest.endRow();
            
            
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.CONTENT_DATA));
            classUnderTest.output(new CellProps.Builder("row 3 col 1").build());
            classUnderTest.endRow();
            classUnderTest.close();  
       }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
       }
	}	
}
