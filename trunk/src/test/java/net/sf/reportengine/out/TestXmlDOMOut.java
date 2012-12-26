/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.test.ReportengineTC;

public class TestXmlDOMOut extends ReportengineTC {

    protected void setUp() throws Exception {
        super.setUp();
    }
    
    public void testXML(){
        try{
            XmlDOMReportOutput xmlOutTest = new XmlDOMReportOutput("target/testXMLOut.xml");
            xmlOutTest.open();
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
}
