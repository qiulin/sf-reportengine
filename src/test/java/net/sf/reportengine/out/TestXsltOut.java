/**
 * 
 */
package net.sf.reportengine.out;

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
            XsltReportOutput xsltOutTest= new XsltReportOutput(createTestOutputFile("testXSLTOut.html"));
            xsltOutTest.open();
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("value row 1"));
            xsltOutTest.endRow();
            
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("value row 2"));
            xsltOutTest.endRow();
            
            xsltOutTest.startRow();
            xsltOutTest.output(new CellProps("value row 3"));
            xsltOutTest.endRow();
            
            xsltOutTest.close();            
        }catch(Throwable exc){
            exc.printStackTrace();
            assertTrue(false);
        }
    }

}
