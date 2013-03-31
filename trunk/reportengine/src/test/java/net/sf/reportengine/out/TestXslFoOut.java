/**
 * 
 */
package net.sf.reportengine.out;

import org.apache.xmlgraphics.util.MimeConstants;

import net.sf.reportengine.config.HorizAlign;
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
            
            classUnderTest.startRow(new RowProps(ReportContent.REPORT_TITLE)); 
            classUnderTest.output(new CellProps.Builder("report title").contentType(ReportContent.REPORT_TITLE).build());
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.COLUMN_HEADER).build());
            classUnderTest.endRow();
           
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("center").horizAlign(HorizAlign.CENTER).build());
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("l align").horizAlign(HorizAlign.LEFT).build());
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("r align").horizAlign(HorizAlign.RIGHT).build());
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
            
            classUnderTest.startRow(new RowProps(ReportContent.REPORT_TITLE)); 
            classUnderTest.output(new CellProps.Builder("Τὴ γλῶσσα μοῦ ἔδωσαν ἑλληνικὴ").contentType(ReportContent.REPORT_TITLE).build());
            classUnderTest.endRow(); 
            
            classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.COLUMN_HEADER).build());
            classUnderTest.endRow();
            
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("τὸ σπίτι φτωχικὸ στὶς ἀμμουδιὲς τοῦ Ὁμήρου").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("ich sih in grâwen tägelîch als er wil tagen").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("На берегу пустынных волн").build());
            classUnderTest.endRow();
            classUnderTest.close();  
        }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
        }
    }
	
	public void testPNGOutput(){
        try{
            XslFoOutput classUnderTest= new XslFoOutput("target/testXslFoOut.png", MimeConstants.MIME_PNG);
            classUnderTest.open();
            
            classUnderTest.startRow(new RowProps(ReportContent.REPORT_TITLE)); 
            classUnderTest.output(new CellProps.Builder("report title").contentType(ReportContent.REPORT_TITLE).build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.COLUMN_HEADER).build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
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
            XslFoOutput classUnderTest= new XslFoOutput(); 
            classUnderTest.setFilePath("target/testXslFoOut.ps");
            classUnderTest.setMimeType(MimeConstants.MIME_POSTSCRIPT);
            
            classUnderTest.open();
            
            classUnderTest.startRow(new RowProps(ReportContent.REPORT_TITLE)); 
            classUnderTest.output(new CellProps.Builder("report title").contentType(ReportContent.REPORT_TITLE).build());
            classUnderTest.endRow(); 
            classUnderTest.startRow(new RowProps(ReportContent.COLUMN_HEADER)); 
            classUnderTest.output(new CellProps.Builder("column1 ").contentType(ReportContent.COLUMN_HEADER).build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endRow();
            classUnderTest.startRow(new RowProps(ReportContent.DATA));
            classUnderTest.output(new CellProps.Builder("row 3 col 1").build());
            classUnderTest.endRow();
            classUnderTest.close();  
       }catch(Throwable exc){
            exc.printStackTrace();
            fail(exc.getMessage());
       }
	}	
}
