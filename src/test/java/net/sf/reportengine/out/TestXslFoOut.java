/**
 * 
 */
package net.sf.reportengine.out;

import org.apache.xmlgraphics.util.MimeConstants;

import net.sf.reportengine.test.ReportengineTC;

/**
 * @author dragos
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
            XslFoOutput xsltOutTest= new XslFoOutput(createTestOutputFile("testXslFoOut.pdf"));
            xsltOutTest.open();
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("row 1 col 1"));
            xsltOutTest.endRow();
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("row 2 col 1"));
            xsltOutTest.endRow();
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("row 3 col 1"));
            xsltOutTest.endRow();
            xsltOutTest.close();  
        }catch(Throwable exc){
            exc.printStackTrace();
            assertTrue(false);
        }
    }
	
	
	public void testJPGOutput(){
        try{
            XslFoOutput xsltOutTest= new XslFoOutput(createTestOutputFile("testXslFoOut.png"), MimeConstants.MIME_PNG);
            xsltOutTest.open();
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("row 1 col 1"));
            xsltOutTest.endRow();
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("row 2 col 1"));
            xsltOutTest.endRow();
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("row 3 col 1"));
            xsltOutTest.endRow();
            xsltOutTest.close();  
        }catch(Throwable exc){
            exc.printStackTrace();
            assertTrue(false);
        }
    }
	
	
}
