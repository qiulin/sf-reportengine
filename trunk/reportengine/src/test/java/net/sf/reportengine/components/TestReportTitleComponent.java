package net.sf.reportengine.components;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.out.neo.DefaultReportOutput;
import net.sf.reportengine.out.neo.NewReportOutput;

import org.junit.Test;
import org.junit.internal.runners.statements.Fail;

public class TestReportTitleComponent {
	
	private ReportTitle componentUnderTest; 
	
	@Test
	public void testOutputNewReportOutput() throws IOException {
		NewReportOutput testOut;
		testOut = new DefaultReportOutput(new FileWriter("./testTitleReportComponent"));
	
		componentUnderTest = new ReportTitle("unit test title");
		componentUnderTest.output(testOut);
	}

}
