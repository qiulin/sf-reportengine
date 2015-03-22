/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.ArrayReportInput;
import net.sf.reportengine.in.ReportInput;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos
 *
 */
public class TestOneIterationAlgorithm {
	
	private ReportInput testInput = new ArrayReportInput(
			new Object[][]{
					new String[]{"1","2","3"},
					new String[]{"4","5","6"}
			});
	
	private CellPropsArrayOutput testOut = new CellPropsArrayOutput();
	
	private AlgorithmInitStep testInitStep = new AlgorithmInitStep<Integer>(){
		public StepResult<Integer> init(StepInput stepInput){
			return new StepResult<Integer>(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0), IOKeys.TEST_KEY); 
		}
	};
	
	private AlgorithmMainStep testMainStep = new AlgorithmMainStep<Integer, Integer, String>(){
		
		public StepResult<Integer> init(StepInput stepInput){
			//this.context.set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(1));
			//this.context.set(ContextKeys.NEW_GROUPING_LEVEL, new Integer(0));
			return new StepResult(ContextKeys.NO_KEY, NumberUtils.INTEGER_ZERO);
		}
		
		public StepResult<Integer> execute(NewRowEvent dataRowEvent, StepInput stepInput){
			Integer executionCounts = (Integer)stepInput.getContextParam(ContextKeys.NO_KEY);
			//context.set(ContextKeys.NEW_GROUPING_LEVEL, executionCounts+1);
			return new StepResult<Integer>(ContextKeys.NO_KEY, executionCounts+1, IOKeys.TEST_KEY); 
		}
		
		public StepResult<String> exit(StepInput stepInput){
			return StepResult.NO_RESULT; 
		}
	};
	
	private OpenLoopCloseInputAlgo classUnderTest = null;
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		classUnderTest = new DefaultLoopThroughReportInputAlgo();
		
		classUnderTest.addInitStep(testInitStep);
		classUnderTest.addMainStep(testMainStep);
	}


	/**
	 * Test method for {@link net.sf.reportengine.core.algorithm.OpenLoopCloseInputAlgo#execute()}.
	 */
	@Test
	public void testExecuteAlgorithm() {
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		mockAlgoInput.put(IOKeys.REPORT_INPUT, testInput); 
		mockAlgoInput.put(IOKeys.REPORT_OUTPUT, testOut); 
		
		Map<IOKeys, Object> algoResult = classUnderTest.execute(mockAlgoInput);
		Assert.assertEquals(Integer.valueOf(2), (Integer)algoResult.get(IOKeys.TEST_KEY));
	}
}
