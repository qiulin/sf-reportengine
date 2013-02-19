/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class TestIntermCtDataList extends TestCase {
	
	private IntermComputedDataList classUnderTest; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.IntermComputedDataList#getValueFor(int[])}.
	 */
	public void testGetValueFor() {
		classUnderTest = new IntermComputedDataList(); 
		classUnderTest.addData(new IntermediateDataInfo("0.0.0", 0,0,0));
		classUnderTest.addData(new IntermediateDataInfo("1.5.3", 1,5,3));
		classUnderTest.addData(new IntermediateDataInfo("0.2.1", 0,2,1));
		classUnderTest.addData(new IntermediateDataInfo("1.1.0", 1,1,0));
		
		
		Object value = classUnderTest.getValueFor(0,0,0); 
		assertNotNull(value); 
		assertEquals("0.0.0", value);
		
		value = classUnderTest.getValueFor(1,1,0); 
		assertNotNull(value); 
		assertEquals("1.1.0", value);
		
		//non existent values
		value = classUnderTest.getValueFor(7,1,0); 
		assertNull(value); 
	}

}
