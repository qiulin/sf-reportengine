/**
 * 
 */
package net.sf.reportengine.util;

import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import junit.framework.TestCase;

/**
 * @author Administrator
 *
 */
public class TestCrosstabMetadata extends TestCase {
	

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	public void testComputeCoefficients2x2x1(){
		DistinctValuesHolder distinctValuesHolder = new DistinctValuesHolder(CtScenario2x2x1With1G1D.HEADER_ROWS); 
		assertNotNull(distinctValuesHolder);
		
		distinctValuesHolder.addValueIfNotExist(0, "North");
		distinctValuesHolder.addValueIfNotExist(0, "South"); 
		distinctValuesHolder.addValueIfNotExist(0, "East"); 
		distinctValuesHolder.addValueIfNotExist(0, "West"); 
		
		distinctValuesHolder.addValueIfNotExist(1, "M"); 
		distinctValuesHolder.addValueIfNotExist(1, "F");
		
		
		CtMetadata classUnderTest = new CtMetadata(distinctValuesHolder); 
		classUnderTest.computeCoefficients(); 
		
		assertEquals(classUnderTest.getDataColumnCount(), 8);
		
		assertEquals(2, classUnderTest.getColspanForLevel(0));
		assertEquals(1, classUnderTest.getColspanForLevel(1));
		
		assertEquals(3, classUnderTest.getColspanWhenTotals(0));
		assertEquals(1, classUnderTest.getColspanWhenTotals(1));
		
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CrosstabMetaData#computeCoefficients()}.
	 */
	public void testComputeCoefficients1x3x1() {
		
		DistinctValuesHolder distinctValuesHolder = new DistinctValuesHolder(CtScenario1x3x1.HEADER_ROWS); 
		assertNotNull(distinctValuesHolder);
		
		distinctValuesHolder.addValueIfNotExist(0, "North");
		distinctValuesHolder.addValueIfNotExist(0, "South"); 
		distinctValuesHolder.addValueIfNotExist(0, "East"); 
		distinctValuesHolder.addValueIfNotExist(0, "West"); 
		
		distinctValuesHolder.addValueIfNotExist(1, "M"); 
		distinctValuesHolder.addValueIfNotExist(1, "F");
		
		distinctValuesHolder.addValueIfNotExist(2, "20");
		distinctValuesHolder.addValueIfNotExist(2, "50"); 
		distinctValuesHolder.addValueIfNotExist(2, "80"); 
		
		CtMetadata classUnderTest = new CtMetadata(distinctValuesHolder); 
		classUnderTest.computeCoefficients(); 
		
		assertEquals(classUnderTest.getDataColumnCount(), 24);
		
		assertEquals(6, classUnderTest.getColspanForLevel(0));
		assertEquals(3, classUnderTest.getColspanForLevel(1)); 
		assertEquals(1, classUnderTest.getColspanForLevel(2)); 
		
		assertEquals(9, classUnderTest.getColspanWhenTotals(0));
		assertEquals(4, classUnderTest.getColspanWhenTotals(1)); 
		assertEquals(1, classUnderTest.getColspanWhenTotals(2));
	}

}
