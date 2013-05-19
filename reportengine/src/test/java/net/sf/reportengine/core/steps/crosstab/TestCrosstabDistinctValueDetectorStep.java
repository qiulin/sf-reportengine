/**
 * 
 */
package net.sf.reportengine.core.steps.crosstab;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.algorithm.AlgorithmContext;
import net.sf.reportengine.core.steps.ReportAlgorithmStepTC;
import net.sf.reportengine.scenarios.ct.CtScenario1x1x1;
import net.sf.reportengine.scenarios.ct.CtScenario1x3x1;
import net.sf.reportengine.scenarios.ct.CtScenario2x2x1With1G1D;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

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
		AlgorithmContext reportContext = getTestContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		
		mockAlgoInput.put(	IOKeys.CROSSTAB_HEADER_ROWS, 
							CtScenario2x2x1With1G1D.HEADER_ROWS);
		mockAlgoInput.put(	IOKeys.CROSSTAB_DATA, 
							CtScenario2x2x1With1G1D.CROSSTAB_DATA);
		
		classUnderTest.init(mockAlgoInput, reportContext);
		assertNotNull(classUnderTest.getCrosstabHeaderRows());
		
		for (int i = 0; i < CtScenario2x2x1With1G1D.RAW_INPUT.length; i++) {
			
			classUnderTest.execute(new NewRowEvent(CtScenario2x2x1With1G1D.RAW_INPUT[i]));
			
			IntermediateDataInfo intermediateDataInfo = (IntermediateDataInfo)reportContext.get(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario2x2x1With1G1D.INTERMEDIATE_DATA_INFO[i]  ,intermediateDataInfo); 
		}
	}
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteCtScenario1x3x1() {
		AlgorithmContext reportContext = getTestContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		
		mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x3x1.HEADER_ROWS);
		mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario1x3x1.CROSSTAB_DATA);
		
		classUnderTest.init(mockAlgoInput, reportContext);
		assertNotNull(classUnderTest.getCrosstabHeaderRows());
		
		for (int i = 0; i < CtScenario1x3x1.RAW_INPUT.length; i++) {
			
			classUnderTest.execute(new NewRowEvent(CtScenario1x3x1.RAW_INPUT[i]));
			
			IntermediateDataInfo intermediateDataInfo = (IntermediateDataInfo)reportContext.get(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario1x3x1.INTERMEDIATE_DATA_INFO[i]  ,intermediateDataInfo); 
		}
	}
	
	
	/**
	 * Test method for {@link net.sf.reportengine.core.steps.crosstab.CrosstabDistinctValuesDetectorStep#execute(net.sf.reportengine.core.algorithm.NewRowEvent)}.
	 */
	public void testExecuteCtScenario1x1x1() {
		AlgorithmContext reportContext = getTestContext(); 
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		
		mockAlgoInput.put(IOKeys.CROSSTAB_HEADER_ROWS, CtScenario1x1x1.ROW_HEADERS);
		mockAlgoInput.put(IOKeys.CROSSTAB_DATA, CtScenario1x1x1.CROSSTAB_DATA_NO_TOTALS);
		
		classUnderTest.init(mockAlgoInput, reportContext);
		assertNotNull(classUnderTest.getCrosstabHeaderRows());
		
		for (int i = 0; i < CtScenario1x1x1.RAW_INPUT.length; i++) {
			
			classUnderTest.execute(new NewRowEvent(CtScenario1x1x1.RAW_INPUT[i]));
			
			IntermediateDataInfo intermediateDataInfo = (IntermediateDataInfo)reportContext.get(ContextKeys.INTERMEDIATE_CROSSTAB_DATA_INFO);
			assertNotNull(intermediateDataInfo);
			assertEquals(CtScenario1x1x1.INTERMEDIATE_DATA_INFO[i]  ,intermediateDataInfo); 
		}
	}
}
