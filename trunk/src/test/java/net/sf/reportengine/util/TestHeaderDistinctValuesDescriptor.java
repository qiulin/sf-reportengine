/**
 * 
 */
package net.sf.reportengine.util;

import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class TestHeaderDistinctValuesDescriptor extends TestCase {
	
	private DistinctValuesRow classUnderTest; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new DistinctValuesRow();
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.DistinctValuesRow#addDistinctValueIfNotExists(java.lang.Object)}.
	 */
	public void testAddDistinctValueIfNotExists1() {
		int result = classUnderTest.addDistinctValueIfNotExists("One");
		assertEquals(result, 0);
		
		result = classUnderTest.addDistinctValueIfNotExists("Two");
		assertEquals(result, 1);
		
		result = classUnderTest.addDistinctValueIfNotExists("Three");
		assertEquals(result, 2);
		
		result = classUnderTest.addDistinctValueIfNotExists("Two");
		assertEquals(result, 1);
		
		result = classUnderTest.addDistinctValueIfNotExists("One");
		assertEquals(result, 0);
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.DistinctValuesRow#addDistinctValueIfNotExists(java.lang.Object)}.
	 */
	public void testAddDistinctValueIfNotExists2() {
		int result = classUnderTest.addDistinctValueIfNotExists(1);
		assertEquals(result, 0);
		
		result = classUnderTest.addDistinctValueIfNotExists(2);
		assertEquals(result, 1);
		
		result = classUnderTest.addDistinctValueIfNotExists(1);
		assertEquals(result, 0);
		
		result = classUnderTest.addDistinctValueIfNotExists(3);
		assertEquals(result, 2);
				
		result = classUnderTest.addDistinctValueIfNotExists(2);
		assertEquals(result, 1);
	}

}
