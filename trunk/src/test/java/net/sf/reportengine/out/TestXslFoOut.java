/**
 * 
 */
package net.sf.reportengine.out;

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

	
	
	public void testOutput(){
        try{
            XslFoReportOutput xsltOutTest= new XslFoReportOutput(createTestOutputFile("testXslFoOut.pdf"));
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
