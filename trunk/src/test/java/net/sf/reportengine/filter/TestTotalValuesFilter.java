/**
 * 
 */
package net.sf.reportengine.filter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;
import net.sf.reportengine.config.SubtotalsInfo;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.steps.MockCalculator;

/**
 * @author dragos balan
 *
 */
public class TestTotalValuesFilter extends TestCase {
	
	private TotalValuesFilter classUnderTest;
	private List<Object> excludedValues = new ArrayList<Object>();
	
	/*
	 * @see TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		 classUnderTest = new TotalValuesFilter();
	}

	/*
	 * @see TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		classUnderTest = null;
	}
    
	
    public void testFiltering1(){
    	excludedValues.add("ExcludeMe!");
    	classUnderTest.setSubtotalIndex(0);
    	classUnderTest.setExcludedValues(excludedValues);
    	
    	SubtotalsInfo subtotalInfo = new SubtotalsInfo();
    	subtotalInfo.setGroupingCalculators(new ICalculator[]{	new MockCalculator("TestValue"), 
    															new MockCalculator("ExcludeMe!")});
    	assertTrue(classUnderTest.isDisplayable(subtotalInfo));
    }
    
    public void testFiltering2(){
    	excludedValues.add("ExcludeMe!");
    	classUnderTest.setSubtotalIndex(0);
    	classUnderTest.setExcludedValues(excludedValues);
    	
    	SubtotalsInfo subtotalInfo = new SubtotalsInfo();
    	subtotalInfo.setGroupingCalculators(new ICalculator[]{	new MockCalculator("ExcludeMe!"), 
    															new MockCalculator("TestValue")});
    	assertFalse(classUnderTest.isDisplayable(subtotalInfo));
    }
    
    public void testFiltering3(){
    	excludedValues.add(new BigDecimal(0));
    	classUnderTest.setSubtotalIndex(0);
    	classUnderTest.setExcludedValues(excludedValues);
    	
    	SubtotalsInfo subtotalInfo = new SubtotalsInfo();
    	subtotalInfo.setGroupingCalculators(new ICalculator[]{	new MockCalculator("0"), 
    															new MockCalculator("TestValue")});
    	assertTrue(classUnderTest.isDisplayable(subtotalInfo));
    }
    
    
    public void testFiltering4(){
    	excludedValues.add(new BigDecimal(0));
    	classUnderTest.setSubtotalIndex(0);
    	classUnderTest.setExcludedValues(excludedValues);
    	
    	SubtotalsInfo subtotalInfo = new SubtotalsInfo();
    	subtotalInfo.setGroupingCalculators(new ICalculator[]{	new MockCalculator(new BigDecimal(0)), 
    															new MockCalculator("TestValue")});
    	assertFalse(classUnderTest.isDisplayable(subtotalInfo));
    }
}
