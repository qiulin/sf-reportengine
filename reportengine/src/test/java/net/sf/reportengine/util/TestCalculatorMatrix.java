/**
 * 
 */
package net.sf.reportengine.util;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.math.NumberUtils;

import junit.framework.TestCase;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.core.calc.Calculator;

/**
 * @author dragos balan
 *
 */
public class TestCalculatorMatrix extends TestCase {
	
	/**
	 * the class to be tested
	 */
	private CalculatorMatrix classUnderTest; 
	
	private static List<Object> TEST_VALUES = Arrays.asList(new Object[]{1,2,3,4,5,6,7});
	
	private static List<DataColumn> TEST_DATA_COLUMNS = Arrays.asList(
			new DataColumn[]{
					new DefaultDataColumn("No calculator column", 0, null),
					new DefaultDataColumn("Count Column", 1, Calculators.COUNT), 
					new DefaultDataColumn("Sum Column", 2, Calculators.SUM), 
					new DefaultDataColumn("We don't care about this one", 3)
	});
	
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
		classUnderTest.initAllCalculators(); 
		
		assertNotNull(classUnderTest);
		assertNotNull(classUnderTest.getDataColumnsHavingCalculators());
		assertNotNull(classUnderTest.getCalculatorPrototypes());
		assertNotNull(classUnderTest.getIndexOfColumnsHavingCalculators()); 
		
		assertEquals(2, classUnderTest.getDataColumnsHavingCalculators().length);
		assertEquals(2, classUnderTest.getIndexOfColumnsHavingCalculators().length);
		assertEquals(2, classUnderTest.getCalculatorPrototypes().length);
		
		assertEquals(1, classUnderTest.getIndexOfColumnsHavingCalculators()[0]);
		assertEquals(2, classUnderTest.getIndexOfColumnsHavingCalculators()[1]);
		
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.util.CalculatorMatrix#initFirstXRows(int)}.
	 */
	public void testInitFirstXRows() {
		classUnderTest = new CalculatorMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.initAllCalculators(); 
		
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		//reinit the first two columns
		classUnderTest.initFirstXRows(2);
		assertNotNull(classUnderTest);
		
		//the first row should be reinited
		Calculator[] row1 = classUnderTest.getRow(0);
		assertNotNull(row1);
		assertEquals(row1.length, 2);
		assertEquals(row1[0].getResult(), NumberUtils.INTEGER_ZERO);
		assertEquals(row1[1].getResult(), BigDecimal.ZERO);
		
		//the second row should be also re-init
		Calculator[] row2 = classUnderTest.getRow(1);
		assertNotNull(row2);
		assertEquals(row2.length, 2);
		assertEquals(row2[0].getResult(), NumberUtils.INTEGER_ZERO);
		assertEquals(row2[1].getResult(), BigDecimal.ZERO);
		
		//the third row should not be reinited
		Calculator[] row3 = classUnderTest.getRow(2);
		assertNotNull(row3);
		assertEquals(row3.length, 2);
		assertNotSame(row3[0].getResult(), NumberUtils.INTEGER_ZERO);
		assertNotSame(row3[1].getResult(), BigDecimal.ZERO);
		
		try{
			classUnderTest.getRow(3);
			assertTrue(false);
		}catch(ArrayIndexOutOfBoundsException exc){
			assertTrue(true);
		}
	}

	/**
	 * Test method for {@link net.sf.reportengine.util.CalculatorMatrix#addValuesToEachRow(java.lang.Object[])}.
	 */
	public void testAddValuesToEachRow() {
		classUnderTest = new CalculatorMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.initAllCalculators(); 
		
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		
		assertNotNull(classUnderTest);
		Calculator[] row = null; 
		for(int i=0; i< 3; i++){
			row = classUnderTest.getRow(i);
			assertNotNull(row);
			assertEquals(row.length, 2);
			
			//test the column with COUNT
			assertNotNull(row[0]);
			Object result = row[0].getResult();
			assertTrue(result instanceof Integer);
			assertEquals(result, 1);
			
			//test the column with SUM
			assertNotNull(row[1]);
			result = row[1].getResult();
			assertTrue(result instanceof BigDecimal);
			assertEquals(((BigDecimal)result).intValue(), 3);
		
			try{
				Calculator calc = row[2];
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
		assertTrue(result instanceof Integer);
		assertEquals(result, 2);
		
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
		assertTrue(result instanceof Integer);
		assertEquals(result, 2);
		
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
		assertTrue(result instanceof Integer);
		assertEquals(result, 2);
		
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

	public void testExport() {
		classUnderTest = new CalculatorMatrix(3, TEST_DATA_COLUMNS);
		classUnderTest.initAllCalculators(); 
		
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		classUnderTest.addValuesToEachRow(new NewRowEvent(TEST_VALUES));
		
		Object[][] results = classUnderTest.exportResults();
		assertNotNull(results);
		assertEquals(results.length, classUnderTest.getRowCount());
		assertEquals(results[0][0], 2);//count
		assertEquals(((BigDecimal)results[0][1]).intValue(), 6);//sum
		
		assertEquals(results[1][0], 2);//count
		assertEquals(((BigDecimal)results[1][1]).intValue(), 6);//sum
		
		assertEquals(results[2][0], 2);//count
		assertEquals(((BigDecimal)results[2][1]).intValue(), 6);//sum		
	}
}
