/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.AbstractReport;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.steps.ReportAlgorithmStepTC;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1;

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
		IAlgorithmContext reportContext = getTestContext(); 
		
		reportContext.set(AbstractReport.CONTEXT_KEY_GROUPING_COLUMNS, CtScenario2x2x1.GROUPING_COLUMNS);
	}

}
