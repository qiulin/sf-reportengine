/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.out.CellProps;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.MatrixUtils;

/**
 * @author Administrator
 *
 */
public class TestColumnHeaderOutputInitStep extends ReportAlgorithmStepTC {
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.ColumnHeaderOutputInitStep#init(net.sf.reportengine.core.algorithm.ReportContext)}.
	 */
	public void testInitScenario1() {
		ReportContext testReportContext = getTestContext();
		testReportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
		testReportContext.set(ContextKeys.DATA_COLUMNS, Scenario1.DATA_COLUMNS);
		
		ColumnHeaderOutputInitStep classUnderTest = new ColumnHeaderOutputInitStep(); 
		classUnderTest.init(testReportContext);
		
		assertTrue(MatrixUtils.compareMatrices(Scenario1.EXPECTED_REPORT_COLUMNS_HEADER, 
												getTestOutput().getHeaderCellMatrix()));
	}

}
