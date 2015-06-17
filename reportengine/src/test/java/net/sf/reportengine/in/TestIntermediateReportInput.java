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
package net.sf.reportengine.in;

import java.io.InputStream;
import java.util.List;

import junit.framework.TestCase;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalDataColsList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * 
 * @author dragos balan
 *
 */
public class TestIntermediateReportInput extends TestCase {
	
	private IntermediateCrosstabReportTableInput classUnderTest = null; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testReadIntermediateReportWhenTotals(){
		
		InputStream intermInput = ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput2x2x1xT.rep");
		
		assertNotNull(intermInput);
		
		classUnderTest = new IntermediateCrosstabReportTableInput(intermInput);
		
		classUnderTest.open(); 
		
		//first line
		assertTrue(classUnderTest.hasMoreRows());
		List<Object> row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.size()); 
		assertTrue(row.get(0) instanceof IntermOriginalGroupValuesList);
		assertTrue(row.get(1) instanceof IntermOriginalDataColsList);
		assertTrue(row.get(2) instanceof IntermComputedDataList);
		assertTrue(row.get(3) instanceof IntermComputedTotalsList);
		
		//second line 
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.size()); 
		assertTrue(row.get(0) instanceof IntermOriginalGroupValuesList);
		assertTrue(row.get(1) instanceof IntermOriginalDataColsList);
		assertTrue(row.get(2) instanceof IntermComputedDataList);
		assertTrue(row.get(3) instanceof IntermComputedTotalsList);
		
		//third line 
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertEquals(4, row.size()); 
		assertTrue(row.get(0) instanceof IntermOriginalGroupValuesList);
		assertTrue(row.get(1) instanceof IntermOriginalDataColsList);
		assertTrue(row.get(2) instanceof IntermComputedDataList);
		assertTrue(row.get(3) instanceof IntermComputedTotalsList);
		
		//fourth
		assertTrue(classUnderTest.hasMoreRows());
		assertTrue(classUnderTest.hasMoreRows()); 
		assertTrue(classUnderTest.hasMoreRows());
		assertTrue(classUnderTest.hasMoreRows());
		
		row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.size()); 
		assertTrue(row.get(0) instanceof IntermOriginalGroupValuesList);
		assertTrue(row.get(1) instanceof IntermOriginalDataColsList);
		assertTrue(row.get(2) instanceof IntermComputedDataList);
		assertTrue(row.get(3) instanceof IntermComputedTotalsList);
		
		//fifth line 
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.size()); 
		assertTrue(row.get(0) instanceof IntermOriginalGroupValuesList);
		assertTrue(row.get(1) instanceof IntermOriginalDataColsList);
		assertTrue(row.get(2) instanceof IntermComputedDataList);
		assertTrue(row.get(3) instanceof IntermComputedTotalsList);
		
		//sixth (no more data in the input)
		assertFalse(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertNull(row);
		
		//no more data in the input 
		assertFalse(classUnderTest.hasMoreRows());
		assertFalse(classUnderTest.hasMoreRows());
		assertFalse(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertNull(row);
		
		classUnderTest.close(); 
	}
	
	public void testReadIntermediateReportNoTotals(){
		
		InputStream intermInput = ReportIoUtils.createInputStreamFromClassPath("TestIntermediateInput1x3x1.rep");
		
		assertNotNull(intermInput);
		
		classUnderTest = new IntermediateCrosstabReportTableInput(intermInput);
		
		classUnderTest.open(); 
		
		//first line
		assertTrue(classUnderTest.hasMoreRows());
		List<Object> row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.size()); 
		
		assertTrue(row.get(0) instanceof IntermOriginalGroupValuesList);
		assertTrue(row.get(1) instanceof IntermOriginalDataColsList);
		assertTrue(row.get(2) instanceof IntermComputedDataList);
		assertTrue(row.get(3) instanceof IntermComputedTotalsList);
		
		IntermComputedTotalsList intermCtTotalsList = (IntermComputedTotalsList)row.get(3);
		assertNotNull(intermCtTotalsList.getTotalsDataList()); 
		assertEquals(0, intermCtTotalsList.getTotalsDataList().size());
		
		while(classUnderTest.hasMoreRows()){
			row = classUnderTest.nextRow(); 
			assertNotNull(row);
			assertEquals(4, row.size()); 
			
			assertTrue(row.get(0) instanceof IntermOriginalGroupValuesList);
			assertTrue(row.get(1) instanceof IntermOriginalDataColsList);
			assertTrue(row.get(2) instanceof IntermComputedDataList);
			assertTrue(row.get(3) instanceof IntermComputedTotalsList);
			
			intermCtTotalsList = (IntermComputedTotalsList)row.get(3);
			assertNotNull(intermCtTotalsList.getTotalsDataList()); 
			assertEquals(0, intermCtTotalsList.getTotalsDataList().size());
		}
		
		classUnderTest.close(); 
	}
}
