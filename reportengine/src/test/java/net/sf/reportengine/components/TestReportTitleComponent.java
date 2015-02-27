package net.sf.reportengine.components;

import java.io.IOException;

import net.sf.reportengine.out.neo.TestImplForReportOutput;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class TestReportTitleComponent {
	
	private ReportTitle componentUnderTest; 
	
	@Test
	public void testOutputNewReportOutput() throws IOException {
		TestImplForReportOutput testOutput = new TestImplForReportOutput(); 
		componentUnderTest = new ReportTitle("unit test title");
		componentUnderTest.output(testOutput);
		
		testOutput.close();
		
		assertEquals("title", testOutput.getString()); 
	}

}
