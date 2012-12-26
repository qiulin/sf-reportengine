/**
 * 
 */
package net.sf.reportengine.in;

import java.io.InputStream;

import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalDataColsList;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;
import net.sf.reportengine.core.steps.crosstab.IntermComputedTotalsList;
import net.sf.reportengine.test.ReportengineTC;

/**
 * @author Administrator
 *
 */
public class TestIntermediateReportInput extends ReportengineTC {
	
	private IntermediateCrosstabReportInput classUnderTest = null; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}
	
	public void testReadIntermediateReportWhenTotals(){
		
		InputStream intermInput = getInputStreamFromClasspath("TestIntermediateInput2x2x1xT.rep");
		
		assertNotNull(intermInput);
		
		classUnderTest = new IntermediateCrosstabReportInput(intermInput);
		
		classUnderTest.open(); 
		
		//first line
		assertTrue(classUnderTest.hasMoreRows());
		Object[] row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.length); 
		assertTrue(row[0] instanceof IntermOriginalGroupValuesList);
		assertTrue(row[1] instanceof IntermOriginalDataColsList);
		assertTrue(row[2] instanceof IntermComputedDataList);
		assertTrue(row[3] instanceof IntermComputedTotalsList);
		
		//second line 
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.length); 
		assertTrue(row[0] instanceof IntermOriginalGroupValuesList);
		assertTrue(row[1] instanceof IntermOriginalDataColsList);
		assertTrue(row[2] instanceof IntermComputedDataList);
		assertTrue(row[3] instanceof IntermComputedTotalsList);
		
		//third line 
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertEquals(4, row.length); 
		assertTrue(row[0] instanceof IntermOriginalGroupValuesList);
		assertTrue(row[1] instanceof IntermOriginalDataColsList);
		assertTrue(row[2] instanceof IntermComputedDataList);
		assertTrue(row[3] instanceof IntermComputedTotalsList);
		
		//fourth
		assertTrue(classUnderTest.hasMoreRows());
		assertTrue(classUnderTest.hasMoreRows()); 
		assertTrue(classUnderTest.hasMoreRows());
		assertTrue(classUnderTest.hasMoreRows());
		
		row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.length); 
		assertTrue(row[0] instanceof IntermOriginalGroupValuesList);
		assertTrue(row[1] instanceof IntermOriginalDataColsList);
		assertTrue(row[2] instanceof IntermComputedDataList);
		assertTrue(row[3] instanceof IntermComputedTotalsList);
		
		//fifth line 
		assertTrue(classUnderTest.hasMoreRows());
		row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.length); 
		assertTrue(row[0] instanceof IntermOriginalGroupValuesList);
		assertTrue(row[1] instanceof IntermOriginalDataColsList);
		assertTrue(row[2] instanceof IntermComputedDataList);
		assertTrue(row[3] instanceof IntermComputedTotalsList);
		
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
		
		InputStream intermInput = getInputStreamFromClasspath("TestIntermediateInput1x3x1.rep");
		
		assertNotNull(intermInput);
		
		classUnderTest = new IntermediateCrosstabReportInput(intermInput);
		
		classUnderTest.open(); 
		
		//first line
		assertTrue(classUnderTest.hasMoreRows());
		Object[] row = classUnderTest.nextRow(); 
		assertNotNull(row);
		assertEquals(4, row.length); 
		assertTrue(row[0] instanceof IntermOriginalGroupValuesList);
		assertTrue(row[1] instanceof IntermOriginalDataColsList);
		assertTrue(row[2] instanceof IntermComputedDataList);
		assertTrue(row[3] instanceof IntermComputedTotalsList);
		IntermComputedTotalsList intermCtTotalsList = (IntermComputedTotalsList)row[3];
		assertNotNull(intermCtTotalsList.getTotalsDataList()); 
		assertEquals(0, intermCtTotalsList.getTotalsDataList().size());
		
		while(classUnderTest.hasMoreRows()){
			row = classUnderTest.nextRow(); 
			assertNotNull(row);
			assertEquals(4, row.length); 
			assertTrue(row[0] instanceof IntermOriginalGroupValuesList);
			assertTrue(row[1] instanceof IntermOriginalDataColsList);
			assertTrue(row[2] instanceof IntermComputedDataList);
			assertTrue(row[3] instanceof IntermComputedTotalsList);
			intermCtTotalsList = (IntermComputedTotalsList)row[3];
			assertNotNull(intermCtTotalsList.getTotalsDataList()); 
			assertEquals(0, intermCtTotalsList.getTotalsDataList().size());
		}
		
		classUnderTest.close(); 
	}

	//TODO: create assert statements for all test above
}
