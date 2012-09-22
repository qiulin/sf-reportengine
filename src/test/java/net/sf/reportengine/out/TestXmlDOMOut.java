/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.test.ReportengineTC;

public class TestXmlDOMOut extends ReportengineTC {

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testXML(){
        try{
            XmlDOMReportOutput xmlOutTest = new XmlDOMReportOutput(createTestOutputFile("testXMLOut.xml"));
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
    
    
    
    

}
