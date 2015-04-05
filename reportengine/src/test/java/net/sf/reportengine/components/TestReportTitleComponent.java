package net.sf.reportengine.components;

import static org.junit.Assert.assertEquals;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.out.neo.DefaultReportOutput;
import net.sf.reportengine.out.neo.NewReportOutput;
import net.sf.reportengine.out.neo.TestImplForReportOutput;

import org.junit.Test;

public class TestReportTitleComponent {
	
	private ReportTitle componentUnderTest; 
	
	@Test
	public void testOutputNewReportOutput() {
		TestImplForReportOutput testOutput = new TestImplForReportOutput(); 
		componentUnderTest = new ReportTitle("unit test title");
		testOutput.open();
		componentUnderTest.output(testOutput);
		testOutput.close();
		assertEquals("title", testOutput.getBuffer()); 
	}
	
	
	@Test
	public void testOutputToDefaultOutput() throws IOException {
		NewReportOutput testOutput = new DefaultReportOutput(new FileWriter("./target/TestReportTitleComponent.txt")); 
		componentUnderTest = new ReportTitle("unit test title");
		testOutput.open();
		componentUnderTest.output(testOutput);
		testOutput.close();
	}
}
