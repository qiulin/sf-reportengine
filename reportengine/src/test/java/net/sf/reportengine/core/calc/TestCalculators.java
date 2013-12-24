/**
 * 
 */
package net.sf.reportengine.core.calc;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Date;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestCalculators {

	@Test
	public void testCountCalculator() {
		CountGroupCalculator calculator = new CountGroupCalculator(); 
		calculator.init(); 
		
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), NumberUtils.INTEGER_ZERO); 
		
		calculator.compute("first object"); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), NumberUtils.INTEGER_ONE); 
		
		calculator.compute(new Integer(7)); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), Integer.valueOf(2));
		
		calculator.init(); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), NumberUtils.INTEGER_ZERO); 
		
		calculator.compute("third object"); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), NumberUtils.INTEGER_ONE); 
		
		calculator.compute(new Date()); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), Integer.valueOf(2));
		
	}
	
	
	@Test
	public void testUniversalSumCalculator() {
		UniversalSumGroupCalculator calculator = new UniversalSumGroupCalculator(); 
		calculator.init(); 
		
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), BigDecimal.ZERO); 
		
		calculator.compute("1"); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), new BigDecimal(1)); 
		
		calculator.compute(new Integer(7)); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), new BigDecimal(8));
		
		calculator.init(); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), BigDecimal.ZERO); 
		
		calculator.compute("1.18907"); 
		assertNotNull(calculator.getResult()); 
		assertTrue(calculator.getResult().doubleValue()== 1.18907D); 
		
		calculator.compute(new Double(2.3)); 
		assertNotNull(calculator.getResult()); 
		assertTrue(calculator.getResult().doubleValue() == 3.48907);
	}
	
	@Test
	public void testUniversalAvgCalculator() {
		UniversalAvgGroupCalculator calculator = new UniversalAvgGroupCalculator(); 
		calculator.init(); 
		
		//assertNotNull(calculator.getResult()); 
		//assertEquals(calculator.getResult(), BigDecimal.ZERO); 
		
		calculator.compute("1"); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), new BigDecimal(1)); 
		
		calculator.compute(new Integer(7)); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult(), new BigDecimal(4));
		
		calculator.init(); 
		//assertNotNull(calculator.getResult()); 
		//assertEquals(calculator.getResult(), BigDecimal.ZERO); 
		
		calculator.compute("1.1890"); 
		assertNotNull(calculator.getResult()); 
		assertTrue(calculator.getResult().doubleValue()== 1.1890); 
		
		calculator.compute(new Double(2.3)); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult().doubleValue(), 1.7445D, 0/*delta*/);
		
		calculator.compute(new Double(2.511)); 
		assertNotNull(calculator.getResult()); 
		assertEquals(calculator.getResult().doubleValue(), 2.0, 0/*delta*/);
	}
}
