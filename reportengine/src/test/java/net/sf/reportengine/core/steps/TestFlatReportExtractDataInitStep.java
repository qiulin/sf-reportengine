/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author Administrator
 *
 */
public class TestFlatReportExtractDataInitStep {
	
	private static List<DataColumn> TEST_DATA_COLUMNS = Arrays.asList(new DataColumn[]{
		new DefaultDataColumn("No calculator column", 0, null),
		new DefaultDataColumn("Count Column", 1, Calculators.COUNT), 
		new DefaultDataColumn("Sum Column", 2, Calculators.SUM), 
		new DefaultDataColumn("We don't care about this one", 3), 
		new DefaultDataColumn("Another column without calculator", 4), 
		new DefaultDataColumn("Last calculator column", 5, Calculators.AVG)
	});
	
	

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.FlatReportExtractDataInitStep#init(net.sf.reportengine.core.algorithm.AlgorithmContext)}.
	 */
	@Test
	public void testInit() {
		FlatReportExtractDataInitStep classUnderTest = new FlatReportExtractDataInitStep(); 
		
		AlgorithmContext reportContext = new DefaultAlgorithmContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
		
		//reportContext.set(ContextKeys.DATA_COLUMNS, TEST_DATA_COLUMNS); 
		mockAlgoInput.put(IOKeys.DATA_COLS, TEST_DATA_COLUMNS);
		
		classUnderTest.init(mockAlgoInput, reportContext); 
		
		int[] result = (int[])reportContext.get(ContextKeys.DISTRIBUTION_OF_CALCULATORS);
		Assert.assertNotNull(result);
		Assert.assertEquals(TEST_DATA_COLUMNS.size(), result.length);
		
		
		Assert.assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[0]); 
		Assert.assertEquals(0, result[1]);
		Assert.assertEquals(1, result[2]);
		Assert.assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[3]);
		Assert.assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[4]);
		Assert.assertEquals(2, result[5]);
	}
}
