/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.core.algorithm.IReportContext;
import net.sf.reportengine.core.steps.ReportAlgorithmStepTC;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author Administrator
 *
 */
public class TestIntermediateCrosstabRowManagerStep extends ReportAlgorithmStepTC {
	
	private IntermediateCrosstabRowMangerStep classUnderTest; 

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new IntermediateCrosstabRowMangerStep(); 
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.IntermediateCrosstabRowMangerStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteCtScenario1() {
		IReportContext reportContext = getTestContext(); 
		
		reportContext.set(ContextKeys.CONTEXT_KEY_GROUPING_COLUMNS, CtScenario2x2x1.GROUPING_COLUMNS);
	}

}
