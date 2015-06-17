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
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestTxtOutput {


	/**
	 * Test method for {@link net.sf.reportengine.out.TextOutput#outputDataCell(net.sf.reportengine.out.CellProps)}.
	 */
	@Test
	public void testOpenWhenNoWriterSet() {
		TextOutput classUnderTest = new TextOutput(); 
		classUnderTest.open();
		
		classUnderTest.startReport(new ReportProps()); 
		classUnderTest.startDataRow(new RowProps()); 
		classUnderTest.outputDataCell(new CellProps.Builder("value x").build()); 
		classUnderTest.endDataRow(); 
		
		classUnderTest.endReport(); 
		classUnderTest.close(); 
		
		Writer result = classUnderTest.getOutputWriter(); 
		Assert.assertNotNull(result); 
		Assert.assertTrue(result instanceof StringWriter);
		StringBuffer resultBuffer = ((StringWriter)result).getBuffer();  
		Assert.assertNotNull(resultBuffer); 
		Assert.assertTrue(resultBuffer.indexOf("value x") >= 0);
	}
	
	/**
	 * 
	 */
	@Test(expected = ReportOutputException.class)
	public void testStartRowWithoutOpen(){
		TextOutput classUnderTest = new TextOutput(); 
		classUnderTest.startDataRow(new RowProps()); 
		Assert.fail("an exception should have been thrown"); 
	}
	
	/**
	 * 
	 */
	@Test
	public void testOutputWhenFilenameSet(){
		TextOutput classUnderTest = new TextOutput(); 
		classUnderTest.setFilePath("target/streamOutputHavingFilenameSet.txt");
		
		classUnderTest.open(); 
		classUnderTest.startReport(new ReportProps());
		classUnderTest.startDataRow(new RowProps());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 0").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 1").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endDataRow(); 
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
	
	@Test
	public void testOutputWhenWriterSet() throws Exception{
		TextOutput classUnderTest = new TextOutput(); 
		classUnderTest.setOutputWriter(new FileWriter("target/streamOutputHavingWriterSet.txt"));
		classUnderTest.open(); 
		classUnderTest.startReport(new ReportProps());
		classUnderTest.startDataRow(new RowProps());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 0").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 1").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endDataRow(); 
		classUnderTest.endReport(); 
		classUnderTest.close(); 
	}
	
	@Test
	public void testManyOutputsForDifferentFilenames(){
		TextOutput classUnderTest = new TextOutput(); 
		classUnderTest.setFilePath("target/reusedStreamOutput1.txt");
		
		classUnderTest.open(); 
		classUnderTest.startReport(new ReportProps()); 
		
		classUnderTest.startDataRow(new RowProps());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 0").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endDataRow(); 
		classUnderTest.endReport(); 
		classUnderTest.close();
		
		classUnderTest.setFilePath("target/reusedStreamOutput2.txt");
		classUnderTest.open(); 
		classUnderTest.startReport(new ReportProps());
		classUnderTest.startDataRow(new RowProps());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 0").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endDataRow(); 
		classUnderTest.endReport(); 
		classUnderTest.close();
	}
	
	@Test
	public void testManyOutputsForDifferentWriters(){
		StringWriter writer1 = new StringWriter(); 
		TextOutput classUnderTest = new TextOutput(); 
		classUnderTest.setOutputWriter(writer1);
		
		classUnderTest.open(); 
		classUnderTest.startReport(new ReportProps());
		classUnderTest.startDataRow(new RowProps());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 0").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endDataRow(); 
		classUnderTest.endReport(); 
		classUnderTest.close();
		
		StringBuffer result = writer1.getBuffer(); 
		Assert.assertNotNull(result);
		Assert.assertTrue(result.indexOf("cell 0 0") >= 0);
		Assert.assertTrue(result.indexOf("cell 0 2") > 0);
		
		
		StringWriter writer2 = new StringWriter(); 
		classUnderTest.setOutputWriter(writer2);
		classUnderTest.open(); 
		classUnderTest.startReport(new ReportProps());
		classUnderTest.startDataRow(new RowProps());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 0").build());
		classUnderTest.outputDataCell(new CellProps.Builder("cell 0 2").build());
		classUnderTest.endDataRow(); 
		classUnderTest.endReport();
		classUnderTest.close();
		
		result = writer2.getBuffer(); 
		Assert.assertNotNull(result);
		Assert.assertTrue(result.indexOf("cell 0 0") >= 0);
		Assert.assertTrue(result.indexOf("cell 0 2") > 0);
	}
	
}
