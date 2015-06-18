/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine.out.neo;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Before;
import org.junit.Test;

/**
 * @author balan
 *
 */
public class TestHtmlReportOutput {

	/**
	 * @throws java.lang.Exception
	 */
	@Before
	public void setUp() throws Exception {
	}

	/**
	 * Test method for {@link net.sf.reportengine.out.neo.AbstractFreemarkerReportOutput#close()}.
	 */
	@Test
	public void testCloseWriterException() {
		try {
			FileWriter fileWriter = new FileWriter("./target/TestClosingWriterException.html"); 
			HtmlReportOutput classUnderTest = new HtmlReportOutput(fileWriter, true);
			classUnderTest.open(); 
			classUnderTest.output("emptyLine.ftl");
			classUnderTest.close(); 
			
			//at this point the writer should be already closed
			fileWriter.write("if you see this the test has failed");
			fail("An IOException should have been thrown at this point"); 
		} catch (IOException e) {
			assertEquals("Stream closed", e.getMessage()); 
		} 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.neo.AbstractFreemarkerReportOutput#close()}.
	 */
	@Test
	public void testNonClosedWriter() throws IOException{
		final String OUTPUT_PATH = "./target/TestClosingWriter.html"; 
		FileWriter fileWriter = new FileWriter(OUTPUT_PATH); 
		HtmlReportOutput classUnderTest = new HtmlReportOutput(fileWriter, false);
		classUnderTest.open(); 
		classUnderTest.output("emptyLine.ftl");
		classUnderTest.close(); 
		
		//at this point the writer should be already closed
		fileWriter.write("\nthis text has been added after HtmlReportOutput has been closed");
		fileWriter.flush(); 
		fileWriter.close(); 
		
		List<String> lines = IOUtils.readLines(new FileReader(OUTPUT_PATH)); 
		assertEquals(3, lines.size()); 
		assertEquals("<br/>", lines.get(0));
		assertEquals("<br/>", lines.get(1));
		assertEquals("this text has been added after HtmlReportOutput has been closed", lines.get(2)); 
	}
}
