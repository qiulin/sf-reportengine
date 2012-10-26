/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.config.DefaultDataColumn;
import net.sf.reportengine.config.IDataColumn;
import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.calc.Calculators;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author Administrator
 *
 */
public class TestFlatReportExtractDataInitStep extends ReportAlgorithmStepTC {
	
	private static IDataColumn[] TEST_DATA_COLUMNS = new IDataColumn[]{
		new DefaultDataColumn("No calculator column", 0, null),
		new DefaultDataColumn("Count Column", 1, Calculators.COUNT), 
		new DefaultDataColumn("Sum Column", 2, Calculators.SUM), 
		new DefaultDataColumn("We don't care about this one", 3), 
		new DefaultDataColumn("Another column without calculator", 4), 
		new DefaultDataColumn("Last calculator column", 5, Calculators.AVG)
	};
	
	
	private FlatReportExtractDataInitStep classUnderTest = null; 
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.steps.ReportAlgorithmStepTC#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new FlatReportExtractDataInitStep(); 
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.FlatReportExtractDataInitStep#init(net.sf.reportengine.core.algorithm.IReportContext)}.
	 */
	public void testInit() {
		IReportContext reportContext = getTestContext(); 
		reportContext.set(ContextKeys.CONTEXT_KEY_DATA_COLUMNS, TEST_DATA_COLUMNS); 
		
		classUnderTest.init(reportContext); 
		
		int[] result = (int[])reportContext.get(ContextKeys.CONTEXT_KEY_DISTRIBUTION_OF_CALCULATORS);
		assertNotNull(result);
		assertEquals(TEST_DATA_COLUMNS.length, result.length);
		
		
		assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[0]); 
		assertEquals(0, result[1]);
		assertEquals(1, result[2]);
		assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[3]);
		assertEquals(FlatReportExtractDataInitStep.NO_CALCULATOR_ON_THIS_POSITION, result[4]);
		assertEquals(2, result[5]);
		
	}

}
