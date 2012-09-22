/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import net.sf.reportengine.CrossTabReport;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.ReportAlgorithmStepTC;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1;

/**
 * @author Administrator
 *
 */
public class TestCrosstabDistinctValueDetectorStep extends ReportAlgorithmStepTC {
	
	private CrosstabDistinctValuesDetectorStep classUnderTest = null; 
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new CrosstabDistinctValuesDetectorStep(); 
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteCtScenario1() {
		IAlgorithmContext reportContext = getTestContext(); 
		
		reportContext.set(CrossTabReport.CONTEXT_KEY_CROSSTAB_HEADER_ROWS, CtScenario2x2x1.HEADER_ROWS);
		reportContext.set(CrossTabReport.CONTEXT_KEY_CROSSTAB_DATA, CtScenario2x2x1.CROSSTAB_DATA);
		
		classUnderTest.init(reportContext);
		assertNotNull(classUnderTest.getCrosstabHeaderRows());
		
		for (int i = 0; i < CtScenario2x2x1.RAW_INPUT.length; i++) {
			
			classUnderTest.execute(new NewRowEvent(CtScenario2x2x1.RAW_INPUT[i]));
			
			IntermediateDataInfo intermediateDataInfo = (IntermediateDataInfo)reportContext.get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario2x2x1.INTERMEDIATE_DATA_INFO[i]  ,intermediateDataInfo); 
		}
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteCtScenario1x3x1() {
		IAlgorithmContext reportContext = getTestContext(); 
		
		reportContext.set(CrossTabReport.CONTEXT_KEY_CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS);
		reportContext.set(CrossTabReport.CONTEXT_KEY_CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA);
		
		classUnderTest.init(reportContext);
		assertNotNull(classUnderTest.getCrosstabHeaderRows());
		
		for (int i = 0; i < CtScenario1x3x1.RAW_INPUT.length; i++) {
			
			classUnderTest.execute(new NewRowEvent(CtScenario1x3x1.RAW_INPUT[i]));
			
			IntermediateDataInfo intermediateDataInfo = (IntermediateDataInfo)reportContext.get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario1x3x1.INTERMEDIATE_DATA_INFO[i]  ,intermediateDataInfo); 
		}
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteCtScenario1x1x1() {
		IAlgorithmContext reportContext = getTestContext(); 
		
		reportContext.set(CrossTabReport.CONTEXT_KEY_CROSSTAB_HEADER_ROWS, CtScenario1x1x1.ROW_HEADERS);
		reportContext.set(CrossTabReport.CONTEXT_KEY_CROSSTAB_DATA, CtScenario1x1x1.CROSSTAB_DATA);
		
		classUnderTest.init(reportContext);
		assertNotNull(classUnderTest.getCrosstabHeaderRows());
		
		for (int i = 0; i < CtScenario1x1x1.RAW_INPUT.length; i++) {
			
			classUnderTest.execute(new NewRowEvent(CtScenario1x1x1.RAW_INPUT[i]));
			
			IntermediateDataInfo intermediateDataInfo = (IntermediateDataInfo)reportContext.get(CrosstabDistinctValuesDetectorStep.CONTEXT_KEY_INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario1x1x1.INTERMEDIATE_DATA_INFO[i]  ,intermediateDataInfo); 
		}
	}
}
