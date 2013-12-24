/**
 * 
 */
package net.sf.reportengine.config;

import junit.framework.TestCase;
import net.sf.reportengine.core.calc.GroupCalculators;

/**
 * @author dragos
 *
 */
public class TestDefaultDataColumn extends TestCase {

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

	/**
	 * Test method for {@link net.sf.reportengine.config.DefaultDataColumn#DefaultDataColumn(java.lang.String, int, net.sf.reportengine.core.calc.GroupCalculator, java.text.Format)}.
	 */
	public void testDefaultDataColumnStringIntICalculatorFormat() {
		DefaultDataColumn classUnderTest = new DefaultDataColumn(); 
		classUnderTest.setHeader("Month"); 
		classUnderTest.setInputColumnIndex(0);
		classUnderTest.setCalculator(GroupCalculators.SUM);
	}
	
	
	public void testDefaultDataColumnConstructor() {
		DefaultDataColumn classUnderTest = new DefaultDataColumn("Month", 0, GroupCalculators.SUM); 
	}
}
