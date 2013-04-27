/**
 * 
 */
package net.sf.reportengine.out;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.StringWriter;

import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestXsltOut {


	@Test
	public void testXSLT() throws Exception{
        XsltOutput xsltOutTest= new XsltOutput("target/testXSLTOut.html");
        xsltOutTest.open();
        xsltOutTest.startReport(new ReportProps());
        xsltOutTest.outputTitle(new TitleProps("report title", 1));
        
        xsltOutTest.startHeaderRow(new RowProps(0));
        xsltOutTest.outputHeaderCell(new CellProps.Builder("col header").build());
        xsltOutTest.endHeaderRow();
        
        xsltOutTest.startDataRow(new RowProps(0));
        xsltOutTest.outputDataCell(new CellProps.Builder("test value").build());
        xsltOutTest.endDataRow();
        xsltOutTest.endReport(); 
        
        xsltOutTest.close();            
    }
	
	@Test
	public void testUtf8Output() throws Exception{
    	StringWriter writer = new StringWriter(); 
        XsltOutput xsltOutTest= new XsltOutput();
        xsltOutTest.setOutputWriter(writer); 
        
        xsltOutTest.open();
        xsltOutTest.startReport(new ReportProps()); 
        xsltOutTest.outputTitle(new TitleProps("Βιβλία", 1));
        
        xsltOutTest.startHeaderRow(new RowProps(0));
        xsltOutTest.outputHeaderCell(new CellProps.Builder("col header").build());
        xsltOutTest.endHeaderRow();
        
        xsltOutTest.startDataRow(new RowProps());
        xsltOutTest.outputDataCell(new CellProps.Builder("и канализация са от").build());
        xsltOutTest.endDataRow();
        
        xsltOutTest.endReport(); 
        xsltOutTest.close();            
        
        StringBuffer result = writer.getBuffer(); 
        assertNotNull(result); 
        assertTrue(result.indexOf("Βιβλία") >0);
        assertTrue(result.indexOf("col header") > 0);
        assertTrue(result.indexOf("и канализация са от") > 0);
    }
	
	@Test
	public void testUtf8OutputToFile() throws Exception{
        XsltOutput xsltOutTest= new XsltOutput();
        xsltOutTest.setFilePath("target/testXsltUtf8.html"); 
        
        xsltOutTest.open();
        xsltOutTest.startReport(new ReportProps()); 
        xsltOutTest.outputTitle(new TitleProps("report title", 1));
        
        xsltOutTest.startHeaderRow(new RowProps(0));
        xsltOutTest.outputHeaderCell(new CellProps.Builder("Βιβλία").build());
        xsltOutTest.endHeaderRow();
        
        xsltOutTest.startDataRow(new RowProps(0));
        xsltOutTest.outputDataCell(new CellProps.Builder("и канализация са от").build());
        xsltOutTest.endDataRow();
        
        xsltOutTest.endReport(); 
        xsltOutTest.close();            
    }
	
}
