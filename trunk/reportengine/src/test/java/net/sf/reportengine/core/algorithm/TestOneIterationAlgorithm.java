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
			//context.set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0));
			//context.set(ContextKeys.LOCAL_REPORT_INPUT, algoInput.get(IOKeys.REPORT_INPUT)); 
			return new StepResult<Integer>(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(0), IOKeys.TEST_KEY); 
		}
	};
	
	private AlgorithmMainStep testMainStep = new AlgorithmMainStep(){
		private AlgoContext context = null;
		private Map<IOKeys, Object> algoInput = null; 
		
		public void init(Map<IOKeys, Object> algoInput, AlgoContext context){
			this.context = context;
			this.algoInput = algoInput; 
			
			//this.context.set("isInitCalledOnMainStep", true);
			this.context.set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(1));
			
			//this.context.set("executionCount", new Integer(0));
			this.context.set(ContextKeys.NEW_GROUPING_LEVEL, new Integer(0));
			
		}
		
		public void execute(NewRowEvent dataRowEvent){
			Integer executionCounts = (Integer)context.get(ContextKeys.NEW_GROUPING_LEVEL);
			context.set(ContextKeys.NEW_GROUPING_LEVEL, executionCounts+1);
		}
		
		public void exit(){}
		
		public Map<IOKeys, Object> getResultsMap(){
			return null; 
		}
	};
	
	private OpenLoopCloseInputAlgo classUnderTest = null;
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	@Before
	public void setUp() throws Exception {
		classUnderTest = new DefaultLoopThroughReportInputAlgo();
		
//		classUnderTest.addIn(IOKeys.REPORT_INPUT, testInput); 
//		classUnderTest.addIn(IOKeys.REPORT_OUTPUT, testOut); 
		
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
		Assert.assertEquals(Integer.valueOf(0), (Integer)algoResult.get(IOKeys.TEST_KEY));
		//TODO: Assert.assertEquals(2, classUnderTest.getContext().get(ContextKeys.NEW_GROUPING_LEVEL));
	}
}
