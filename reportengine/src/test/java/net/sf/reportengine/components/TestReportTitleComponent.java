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
package net.sf.reportengine.components;

import static org.junit.Assert.assertEquals;

import java.io.FileWriter;
import java.io.IOException;

import net.sf.reportengine.out.neo.AbstractReportOutput;
import net.sf.reportengine.out.neo.AbstractFreemarkerReportOutput;
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
