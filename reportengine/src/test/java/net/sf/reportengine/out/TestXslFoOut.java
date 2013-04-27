/**
 * 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.util.ReportIoUtils;

import org.apache.xmlgraphics.util.MimeConstants;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestXslFoOut {
	
	@Test
	public void testPDFOutput() throws Exception{
            XslFoOutput classUnderTest= new XslFoOutput(ReportIoUtils.createOutputStreamFromPath("./target/testXslFoOut.pdf"));
            classUnderTest.open();
            classUnderTest.startReport(new ReportProps());
            
            classUnderTest.outputTitle(new TitleProps("report title", 3));
            
            classUnderTest.startHeaderRow(new RowProps(0)); 
            classUnderTest.outputHeaderCell(new CellProps.Builder("column1 ").build());
            classUnderTest.endHeaderRow();
           
            classUnderTest.startDataRow(new RowProps(0));
            classUnderTest.outputDataCell(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(1));
            classUnderTest.outputDataCell(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(2));
            classUnderTest.outputDataCell(new CellProps.Builder("center").horizAlign(HorizAlign.CENTER).build());
            classUnderTest.endDataRow();
            
            classUnderTest.startDataRow(new RowProps(3));
            classUnderTest.outputDataCell(new CellProps.Builder("l align").horizAlign(HorizAlign.LEFT).build());
            classUnderTest.endDataRow();
            
            classUnderTest.startDataRow(new RowProps(4));
            classUnderTest.outputDataCell(new CellProps.Builder("r align").horizAlign(HorizAlign.RIGHT).build());
            classUnderTest.endDataRow();
            
            classUnderTest.endReport(); 
            classUnderTest.close();  
        
    }
	
	@Ignore
	public void testPdfUtf8Output() throws Exception{
            XslFoOutput classUnderTest= new XslFoOutput("./target/testUtf8Out.pdf");
            classUnderTest.open();
            classUnderTest.startReport(new ReportProps());
             
            classUnderTest.outputTitle(new TitleProps("Τὴ γλῶσσα μοῦ ἔδωσαν ἑλληνικὴ", 1));
            
            classUnderTest.startHeaderRow(new RowProps(0)); 
            classUnderTest.outputHeaderCell(new CellProps.Builder("column1 ").build());
            classUnderTest.endHeaderRow();
            
            classUnderTest.startDataRow(new RowProps(0));
            classUnderTest.outputDataCell(new CellProps.Builder("τὸ σπίτι φτωχικὸ στὶς ἀμμουδιὲς τοῦ Ὁμήρου").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(1));
            classUnderTest.outputDataCell(new CellProps.Builder("ich sih in grâwen tägelîch als er wil tagen").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(2));
            classUnderTest.outputDataCell(new CellProps.Builder("На берегу пустынных волн").build());
            classUnderTest.endDataRow();
            
            classUnderTest.endReport(); 
            classUnderTest.close();  
    }
	
	@Ignore
	public void testPNGOutput() throws Exception{
            XslFoOutput classUnderTest= new XslFoOutput("target/testXslFoOut.png", MimeConstants.MIME_PNG);
            classUnderTest.open();
            classUnderTest.startReport(new ReportProps());
            
            classUnderTest.outputTitle(new TitleProps("report title", 1));
            classUnderTest.startHeaderRow(new RowProps(0)); 
            classUnderTest.outputHeaderCell(new CellProps.Builder("column1 ").build());
            classUnderTest.endHeaderRow();
            
            classUnderTest.startDataRow(new RowProps(0));
            classUnderTest.outputDataCell(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(1));
            classUnderTest.outputDataCell(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(3));
            classUnderTest.outputDataCell(new CellProps.Builder("row 3 col 1").build());
            classUnderTest.endDataRow();
            
            classUnderTest.endReport(); 
            classUnderTest.close();  
    }
	
	@Ignore
	public void testPostScriptOutput() throws Exception{
            XslFoOutput classUnderTest= new XslFoOutput(); 
            classUnderTest.setFilePath("target/testXslFoOut.ps");
            classUnderTest.setMimeType(MimeConstants.MIME_POSTSCRIPT);
            
            classUnderTest.open();
            classUnderTest.startReport(new ReportProps());
            
            classUnderTest.outputTitle(new TitleProps("report title", 3));
            
            classUnderTest.startHeaderRow(new RowProps(0)); 
            classUnderTest.outputHeaderCell(new CellProps.Builder("column1 ").build());
            classUnderTest.endHeaderRow();
            
            classUnderTest.startDataRow(new RowProps(0));
            classUnderTest.outputDataCell(new CellProps.Builder("row 1 col 1").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(1));
            classUnderTest.outputDataCell(new CellProps.Builder("row 2 col 1").build());
            classUnderTest.endDataRow();
            classUnderTest.startDataRow(new RowProps(2));
            classUnderTest.outputDataCell(new CellProps.Builder("row 3 col 1").build());
            classUnderTest.endDataRow();
            
            classUnderTest.endReport(); 
            classUnderTest.close();  
	}	
}
