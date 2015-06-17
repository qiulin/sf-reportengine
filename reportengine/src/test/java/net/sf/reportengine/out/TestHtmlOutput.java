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
/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileWriter;
import java.io.StringWriter;
import java.io.Writer;

import net.sf.reportengine.util.ReportIoUtils;

import org.junit.Assert;
import org.junit.Test;


/**
 * @author balan
 *
 */
public class TestHtmlOutput  {

	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutputWithDefaultCss() {
		HtmlOutput classUnderTest = new HtmlOutput("target/cssInsideAndTitleAndMultipleColHead.html");
		
		classUnderTest.open();
		
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.outputTitle(new TitleProps("report title", 3));
		
		
		classUnderTest.startHeaderRow(new RowProps(0)); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header11").build()); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header12").build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header13").build());
		classUnderTest.endHeaderRow(); 
		
		classUnderTest.startHeaderRow(new RowProps(1)); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header21").build()); 
		classUnderTest.outputHeaderCell(new CellProps.Builder("header22").build());
		classUnderTest.outputHeaderCell(new CellProps.Builder("header23").build());
		classUnderTest.endHeaderRow(); 
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("first cell").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("second cell").build());
		classUnderTest.outputDataCell(new CellProps.Builder("third cell").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(1)); 
		classUnderTest.outputDataCell(new CellProps.Builder("this cell has a colspan of 3").colspan(3).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(2)); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 1").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 2").build());
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 3").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport();
		classUnderTest.close(); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutputWithExternalCss() throws Exception {
		HtmlOutput classUnderTest = new HtmlOutput();
		classUnderTest.setOutputWriter(new FileWriter("target/testHtmlOutHavingExternalCss.html"));
		classUnderTest.setCssPath("testReport.css");
		
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("first cell").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("second cell").build());
		classUnderTest.outputDataCell(new CellProps.Builder("third cell").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(1)); 
		classUnderTest.outputDataCell(new CellProps.Builder("this cell has a colspan of 3").colspan(3).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(2)); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 1").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 2").build());
		classUnderTest.outputDataCell(new CellProps.Builder("row 3 value 3").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOutputUtf8() {
		Writer writer = ReportIoUtils.createWriterFromPath("target/testHtmlOutStreamUtf8.html"); 
			
		HtmlOutput classUnderTest = new HtmlOutput();
		classUnderTest.setOutputWriter(writer); 
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("от Субсахарска").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("Африка").build());
		classUnderTest.outputDataCell(new CellProps.Builder("постига").build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.startDataRow(new RowProps(1)); 
		classUnderTest.outputDataCell(new CellProps.Builder("устойчиви резултати").colspan(3).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.out.HtmlOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testMultipleUseWithDifferentWriters() {
		StringWriter firstWriter = new StringWriter(); 
		HtmlOutput classUnderTest = new HtmlOutput(firstWriter);
		classUnderTest.open();
		classUnderTest.startReport(new ReportProps());
		
		classUnderTest.startDataRow(new RowProps(0)); 
		classUnderTest.outputDataCell(new CellProps.Builder("от Субсахарска").build()); 
		classUnderTest.outputDataCell(new CellProps.Builder("Африка").build());
		classUnderTest.outputDataCell(new CellProps.Builder("постига").build());
		classUnderTest.endDataRow(); 
		classUnderTest.startDataRow(new RowProps(1)); 
		classUnderTest.outputDataCell(new CellProps.Builder("устойчиви резултати").colspan(3).build());
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
		
		StringBuffer result = firstWriter.getBuffer(); 
		Assert.assertNotNull(result); 
		Assert.assertTrue(result.indexOf("от Субсахарска") >0);
		Assert.assertTrue(result.indexOf("Африка") > 0);
		Assert.assertTrue(result.indexOf("постига") >0);
		Assert.assertTrue(result.indexOf("устойчиви резултати") >0);
		
		//second use
		StringWriter newWriter = new StringWriter(); 
		classUnderTest.setOutputWriter(newWriter); 
		classUnderTest.open(); 
		classUnderTest.startDataRow(new RowProps(2));
		classUnderTest.outputDataCell(new CellProps.Builder("test value").build());
		classUnderTest.endDataRow(); 
		classUnderTest.close(); 
		
		result = newWriter.getBuffer(); 
		Assert.assertNotNull(result);
		Assert.assertTrue(result.indexOf("test value") > 0);
	}	
}
