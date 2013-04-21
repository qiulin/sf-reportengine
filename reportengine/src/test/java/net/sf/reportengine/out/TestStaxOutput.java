/**
 * 
 */
package net.sf.reportengine.out;

import java.io.Writer;

import net.sf.reportengine.core.ReportContent;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestStaxOutput{
	
	@Test
	public void testStaxOutput() throws Exception{
        StaxReportOutput xmlOutTest = new StaxReportOutput("target/staxWithTitleAndManyRows.xml");
        xmlOutTest.open();
        
        xmlOutTest.outputTitle(new TitleProps("this is the title", 1));
        
        xmlOutTest.startRow(new RowProps(ReportContent.COLUMN_HEADER));
        xmlOutTest.output(new CellProps.Builder("column 1").contentType(ReportContent.COLUMN_HEADER).build());
        xmlOutTest.endRow(); 
        
        xmlOutTest.startRow(new RowProps(ReportContent.DATA, 0));
        xmlOutTest.output(new CellProps.Builder("value row 1 col 1").rowNumber(0).build());
        xmlOutTest.endRow();
        
        xmlOutTest.startRow(new RowProps(ReportContent.DATA, 1));
        xmlOutTest.output(new CellProps.Builder("value row 2 col 1").rowNumber(1).build());
        xmlOutTest.endRow();
        
        xmlOutTest.startRow(new RowProps(ReportContent.DATA, 2));
        xmlOutTest.output(new CellProps.Builder("value row 3 col 1").rowNumber(2).build());
        xmlOutTest.endRow();
        xmlOutTest.close();            
    }
	
	@Test
	public void testStaxUTF8Output() throws Exception{
    	Writer writer = ReportIoUtils.createWriterFromPath("target/staxUtf8.xml");
        StaxReportOutput xmlOutTest = new StaxReportOutput(writer);
        
        xmlOutTest.open();
        
        xmlOutTest.startRow(new RowProps(ReportContent.DATA));
        xmlOutTest.output(new CellProps.Builder("Πρωτόκολλο Αζήτητων").rowNumber(0).build());
        xmlOutTest.endRow();
        
        xmlOutTest.startRow(new RowProps(ReportContent.DATA));
        xmlOutTest.output(new CellProps.Builder("Βιβλία").rowNumber(1).build());
        xmlOutTest.endRow();
        
        xmlOutTest.startRow(new RowProps(ReportContent.DATA));
        xmlOutTest.output(new CellProps.Builder("и канализация са от").rowNumber(2).build());
        xmlOutTest.endRow();
        
        xmlOutTest.close();  
    }
}
