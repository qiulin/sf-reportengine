package net.sf.reportengine.components;

import static org.junit.Assert.assertEquals;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.out.neo.AbstractReportOutput;
import net.sf.reportengine.out.neo.FreemarkerReportOutput;
import net.sf.reportengine.out.neo.MockReportOutput;

import org.apache.commons.lang.SystemUtils;
import org.junit.Test;

public class TestReportTitleComponent {
	
	private ReportTitle componentUnderTest; 
	
	@Test
	public void testOutputNewReportOutput() {
		MockReportOutput testOutput = new MockReportOutput(); 
		componentUnderTest = new ReportTitle("unit test title");
		testOutput.open();
		componentUnderTest.output(testOutput);
		testOutput.close();
		assertEquals("title"+SystemUtils.LINE_SEPARATOR, testOutput.getBuffer()); 
	}
}
