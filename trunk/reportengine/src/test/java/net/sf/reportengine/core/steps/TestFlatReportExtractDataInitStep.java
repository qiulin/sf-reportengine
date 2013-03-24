/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author Administrator
 *
 */
public class TestFlatReportExtractDataInitStep extends ReportAlgorithmStepTC {
	
	private static List<DataColumn> TEST_DATA_COLUMNS = Arrays.asList(new DataColumn[]{
		new DefaultDataColumn("No calculator column", 0, null),
		new DefaultDataColumn("Count Column", 1, Calculators.COUNT), 
		new DefaultDataColumn("Sum Column", 2, Calculators.SUM), 
		new DefaultDataColumn("We don't care about this one", 3), 
		new DefaultDataColumn("Another column without calculator", 4), 
		new DefaultDataColumn("Last calculator column", 5, Calculators.AVG)
	});
	
	
	private FlatReportExtractDataInitStep classUnderTest = null; 
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.steps.ReportAlgorithmStepTC#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new FlatReportExtractDataInitStep(); 
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.FlatReportExtractDataInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	public void testInit() {
		ReportContext reportContext = getTestContext(); 
		reportContext.set(ContextKeys.DATA_COLUMNS, TEST_DATA_COLUMNS); 
		
		classUnderTest.init(reportContext); 
		
		int[] result = (int[])reportContext.get(ContextKeys.DISTRIBUTION_OF_CALCULATORS);
		assertNotNull(result);
		assertEquals(TEST_DATA_COLUMNS.size(), result.length);
		
		
		assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[0]); 
		assertEquals(0, result[1]);
		assertEquals(1, result[2]);
		assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[3]);
		assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[4]);
		assertEquals(2, result[5]);
		
	}

}
