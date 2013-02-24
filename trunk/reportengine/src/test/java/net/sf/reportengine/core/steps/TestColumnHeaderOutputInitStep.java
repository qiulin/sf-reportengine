/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.MatrixUtils;

/**
 * @author Administrator
 *
 */
public class TestColumnHeaderOutputInitStep extends ReportAlgorithmStepTC {
	
	private ColumnHeaderOutputInitStep classUnderTest = null;
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		
		classUnderTest = new ColumnHeaderOutputInitStep(); 
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep#init(net.sf.reportengine.core.algorithm.IReportContext)}.
	 */
	public void testInitScenario1() {
		IReportContext testReportContext = getTestContext();
		testReportContext.set(ContextKeys.GROUPING_COLUMNS, Scenario1.GROUPING_COLUMNS);
		testReportContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		
		classUnderTest.init(testReportContext);
		
		assertTrue(MatrixUtils.compareMatrices(new CellProps[][]{Scenario1.EXPECTED_REPORT_COLUMNS_HEADER}, getTestOutput().getCellMatrix()));
	}

}
