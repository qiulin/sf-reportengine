/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.scenarios.Scenario1;
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
	 * Test method for {@link net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep#init(net.sf.reportengine.core.algorithm.IAlgorithmContext)}.
	 */
	public void testInitScenario1() {
		IAlgorithmContext testReportContext = getTestContext();
		testReportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, Scenario1.GROUPING_COLUMNS.toArray());
		testReportContext.set(AbstractReport.CONTEXT_KEY_DATA_COLUMNS, Scenario1.DATA_COLUMNS.toArray());
		
		classUnderTest.init(testReportContext);
		
		assertTrue(MatrixUtils.compareMatrices(new CellProps[][]{Scenario1.EXPECTED_REPORT_COLUMNS_HEADER}, getTestOutput().getCellMatrix()));
	}

}
