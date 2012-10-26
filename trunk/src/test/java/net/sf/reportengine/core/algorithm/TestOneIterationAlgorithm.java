/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import junit.framework.TestCase;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.IAlgorithmMainStep;
import net.sf.reportengine.in.IReportInput;
import net.sf.reportengine.in.MemoryReportInput;
import net.sf.reportengine.out.MemoryOutput;
import net.sf.reportengine.util.ContextKeys;

/**
 * @author dragos
 *
 */
public class TestOneIterationAlgorithm extends TestCase {
	
	private IReportInput testInput = new MemoryReportInput(
			new Object[][]{
					new String[]{"1","2","3"},
					new String[]{"4","5","6"}
			});
	private MemoryOutput testOut = new MemoryOutput();
	
	private IAlgorithmInitStep testInitStep = new IAlgorithmInitStep(){
		public void init(IReportContext context){
			//context.set("isInitCalledOnInitStep", true);
			context.set(ContextKeys.CONTEXT_KEY_SHOW_GRAND_TOTAL, true);
		}
	};
	
	private IAlgorithmMainStep testMainStep = new IAlgorithmMainStep(){
		IReportContext context = null;
		
		public void init(IReportContext context){
			this.context = context;
			//this.context.set("isInitCalledOnMainStep", true);
			this.context.set(ContextKeys.CONTEXT_KEY_SHOW_TOTALS, true);
			
			//this.context.set("executionCount", new Integer(0));
			this.context.set(ContextKeys.CONTEXT_KEY_NEW_GROUPING_LEVEL, new Integer(0));
		}
		
		public void execute(NewRowEvent dataRowEvent){
			Integer executionCounts = (Integer)context.get(ContextKeys.CONTEXT_KEY_NEW_GROUPING_LEVEL);
			context.set(ContextKeys.CONTEXT_KEY_NEW_GROUPING_LEVEL, executionCounts+1);
		}
		
		public void exit(){
			
		}
	};
	
	private OneIterationAlgorithm classUnderTest = null;
	
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new OneIterationAlgorithm();
		classUnderTest.setIn(testInput);
		classUnderTest.setOut(testOut);
		classUnderTest.addInitStep(testInitStep);
		classUnderTest.addMainStep(testMainStep);
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	/**
	 * Test method for {@link net.sf.reportengine.core.algorithm.OneIterationAlgorithm#executeAlgorithm()}.
	 */
	public void testExecuteAlgorithm() {
		classUnderTest.executeAlgorithm();
		assertTrue((Boolean)classUnderTest.getContext().get(ContextKeys.CONTEXT_KEY_SHOW_GRAND_TOTAL));
		assertTrue((Boolean)classUnderTest.getContext().get(ContextKeys.CONTEXT_KEY_SHOW_TOTALS));
		assertEquals(2, classUnderTest.getContext().get(ContextKeys.CONTEXT_KEY_NEW_GROUPING_LEVEL));
	}
}
