/**
 * 
 */
package net.sf.reportengine.util;

import java.math.BigDecimal;

import junit.framework.TestCase;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.calc.CalculatorException;
import net.sf.reportengine.core.calc.CountCalculator;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.core.calc.SumCalculator;

/**
 * @author dragos
 *
 */
public class TestCalculatorMatrix extends TestCase {
	
	/**
	 * the class to be tested
	 */
	private CalculatorMatrix classUnderTest; 
	
	//private static int[] TEST_COLUMNNS_TO_COMPUTE = new int[]{1,2};
	private static Integer[] TEST_VALUES = new Integer[]{1,2,3,4,5,6,7};
	//private static ICalculator[] TEST_PROTOTYPE = new ICalculator[]{Calculator.COUNT, Calculator.SUM};
	
	private static IDataColumn[] TEST_DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("No calculator column", 0, null),
		new DefaultDataColumn("Count Column", 1, Calculator.COUNT), 
		new DefaultDataColumn("Sum Column", 2, Calculator.SUM), 
		new DefaultDataColumn("We don't care about this one", 3)
	};
	
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
	
	
	public void testExtractCalculatorsData(){
		classUnderTest = new CalculatorMatrix(3, TEST_DATA_COLUMNS);
		
		assertNotNull(classUnderTest);
		assertNotNull(classUnderTest.getDataColumnsHavingCalculators());
		assertNotNull(classUnderTest.getPrototypeCalculators());
		assertNotNull(classUnderTest.getIndexOfColumnsHavingCalculators()); 
		
		assertEquals(2, classUnderTest.getDataColumnsHavingCalculators().length);
		assertEquals(2, classUnderTest.getIndexOfColumnsHavingCalculators().length);
		assertEquals(2, classUnderTest.getPrototypeCalculators().length);
		
		assertEquals(1, classUnderTest.getIndexOfColumnsHavingCalculators()[0]);
		assertEquals(2, classUnderTest.getIndexOfColumnsHavingCalculators()[1]);
		
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CalculatorMatrix#initFirstXRows(int)}.
	 */
	public void testInitFirstXRows() throws CalculatorException {
		classUnderTest = new CalculatorMatrix(3, TEST_DATA_COLUMNS);
		
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		//reinit the first two columns
		classUnderTest.initFirstXRows(2);
		assertNotNull(classUnderTest);
		
		//the first row should be reinited
		ICalculator[] row1 = classUnderTest.getRow(0);
		assertNotNull(row1);
		assertEquals(row1.length, 2);
		assertEquals(row1[0].getResult(), CountCalculator.ZERO_VALUE);
		assertEquals(row1[1].getResult(), SumCalculator.ZERO_VALUE);
		
		//the second row should be also reinited
		ICalculator[] row2 = classUnderTest.getRow(1);
		assertNotNull(row2);
		assertEquals(row2.length, 2);
		assertEquals(row2[0].getResult(), CountCalculator.ZERO_VALUE);
		assertEquals(row2[1].getResult(), SumCalculator.ZERO_VALUE);
		
		//the third row should not be reinited
		ICalculator[] row3 = classUnderTest.getRow(2);
		assertNotNull(row3);
		assertEquals(row3.length, 2);
		assertNotSame(row3[0].getResult(), CountCalculator.ZERO_VALUE);
		assertNotSame(row3[1].getResult(), SumCalculator.ZERO_VALUE);
		
		try{
			classUnderTest.getRow(3);
			assertTrue(false);
		}catch(ArrayIndexOutOfBoundsException exc){
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.CalculatorMatrix#addValuesToEachRow(java.lang.Object[])}.
	 * @throws CalculatorException 
	 */
	public void testAddValuesToEachRow() throws CalculatorException {
		classUnderTest = new CalculatorMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		
		assertNotNull(classUnderTest);
		ICalculator[] row = null; 
		for(int i=0; i< 3; i++){
			row = classUnderTest.getRow(i);
			assertNotNull(row);
			assertEquals(row.length, 2);
			
			//test the column with COUNT
			assertNotNull(row[0]);
			Object result = row[0].getResult();
			assertTrue(result instanceof BigDecimal);
			assertEquals(((BigDecimal)result).intValue(), 1);
			
			//test the column with SUM
			assertNotNull(row[1]);
			result = row[1].getResult();
			assertTrue(result instanceof BigDecimal);
			assertEquals(((BigDecimal)result).intValue(), 3);
		
			try{
				ICalculator calc = row[2];
				assertTrue(false);
			}catch(ArrayIndexOutOfBoundsException exc){
				assertTrue(true);
			}
		}
		
		//add values once more
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		assertNotNull(classUnderTest);
		
		//TEST FIRST ROW
		row = classUnderTest.getRow(0);
		assertNotNull(row);
		assertEquals(row.length, 2);
		
		//count on first row first column
		assertNotNull(row[0]);
		Object result = row[0].getResult();
		assertTrue(result instanceof BigDecimal);
		assertEquals(((BigDecimal)result).intValue(), 2);
		
		//sum on the first row second column
		assertNotNull(row[1]);
		result = row[1].getResult();
		assertTrue(result instanceof BigDecimal);
		assertEquals(((BigDecimal)result).intValue(), 6);
		
		//TEST SECOND ROW
		row = classUnderTest.getRow(1);
		assertNotNull(row);
		assertEquals(row.length, 2);
		
		//count on first row first column
		assertNotNull(row[0]);
		result = row[0].getResult();
		assertTrue(result instanceof BigDecimal);
		assertEquals(((BigDecimal)result).intValue(), 2);
		
		//sum on the first row second column
		assertNotNull(row[1]);
		result = row[1].getResult();
		assertTrue(result instanceof BigDecimal);
		assertEquals(((BigDecimal)result).intValue(), 6);
		
		
		//TEST THIRD ROW
		row = classUnderTest.getRow(2);
		assertNotNull(row);
		assertEquals(row.length, 2);
		
		//count on third row first column
		assertNotNull(row[0]);
		result = row[0].getResult();
		assertTrue(result instanceof BigDecimal);
		assertEquals(((BigDecimal)result).intValue(), 2);
		
		//sum on the third row second column
		assertNotNull(row[1]);
		result = row[1].getResult();
		assertTrue(result instanceof BigDecimal);
		assertEquals(((BigDecimal)result).intValue(), 6);
		
		try{
			classUnderTest.getRow(4);
			assertTrue(false);
		}catch(ArrayIndexOutOfBoundsException exc){
			assertTrue(true);
		}
		
	}

	public void testExport() throws CalculatorException{
		classUnderTest = new CalculatorMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		
		Object[][] results = classUnderTest.exportResults();
		assertNotNull(results);
		assertEquals(results.length, classUnderTest.getRowCount());
		assertEquals(((BigDecimal)results[0][0]).intValue(), 2);
		assertEquals(((BigDecimal)results[0][1]).intValue(), 6);
		
		assertEquals(((BigDecimal)results[1][0]).intValue(), 2);
		assertEquals(((BigDecimal)results[1][1]).intValue(), 6);
		
		assertEquals(((BigDecimal)results[2][0]).intValue(), 2);
		assertEquals(((BigDecimal)results[2][1]).intValue(), 6);		
	}

}
