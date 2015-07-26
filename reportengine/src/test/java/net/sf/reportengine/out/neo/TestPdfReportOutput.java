/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
