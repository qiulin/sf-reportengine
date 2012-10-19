/**
 * 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.test.ReportengineTC;

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
            StaxReportOutput xmlOutTest = new StaxReportOutput(createTestOutputFile("testStaxOutput.xml"));
            xmlOutTest.open();
            xmlOutTest.startRow();
            xmlOutTest.output(new CellProps("value row 1 col 1"));
            xmlOutTest.endRow();
            
            xmlOutTest.startRow();
            xmlOutTest.output(new CellProps("value row 2 col 1"));
            xmlOutTest.endRow();
            
            xmlOutTest.startRow();
            xmlOutTest.output(new CellProps("value row 3 col 1"));
            xmlOutTest.endRow();
            xmlOutTest.close();            
        }catch(Throwable exc){
            exc.printStackTrace();
            assertTrue(false);
        }
    }
	
	
	public void testStaxUTF8Output(){
        try{
            StaxReportOutput xmlOutTest = new StaxReportOutput(createTestOutputFile("testStaxUtf8Out.xml"), "UTF-8");
            xmlOutTest.open();
            xmlOutTest.startRow();
            xmlOutTest.output(new CellProps("Πρωτόκολλο Αζήτητων"));
            xmlOutTest.endRow();
            
            xmlOutTest.startRow();
            xmlOutTest.output(new CellProps("Βιβλία"));
            xmlOutTest.endRow();
            
            xmlOutTest.startRow();
            xmlOutTest.output(new CellProps("и канализация са от"));
            xmlOutTest.endRow();
            xmlOutTest.close();            
        }catch(Throwable exc){
            exc.printStackTrace();
            assertTrue(false);
        }
    }
}
