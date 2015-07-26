package net.sf.reportengine.out.neo;

import java.io.IOException;

import net.sf.reportengine.out.ReportProps;
import net.sf.reportengine.out.TitleProps;
import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Before;
import org.junit.Test;

public class TestPdfReportOutput {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testUtf8Characters() throws IOException {
		String OUTPUT_PATH = "target/TestPdfReportOutputUtf8.pdf";
		PdfReportOutput testOutput = new PdfReportOutput(
				ReportIoUtils.createOutputStreamFromPath(OUTPUT_PATH)); 
		testOutput.open();
		
		//calling startreport.ftl and endreport.ftl is important for pdf because 
		//the resulting xml does not have a root and a well-defined namespace
		testOutput.output("startReport.ftl", new ReportProps(new PdfOutputFormat()));
		testOutput.output("title.ftl", new TitleProps("На берегу пустынных волн")); 
		testOutput.output("title.ftl", new TitleProps("Τη γλώσσα μου έδωσαν ελληνική"));
		testOutput.output("endReport.ftl");
		testOutput.close(); 	
	}

}
