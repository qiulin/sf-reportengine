/**
 * 
 */
package net.sf.reportengine.core.steps;

import net.sf.reportengine.core.algorithm.DefaultAlgoContext;
import net.sf.reportengine.core.algorithm.IAlgorithmContext;
import net.sf.reportengine.core.calc.ICalculator;
import net.sf.reportengine.out.MemoryOutput;
import net.sf.reportengine.out.IReportOutput;
import net.sf.reportengine.out.LoggerOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.test.ReportengineTC;

/**
 * utility test case containing usefull constants and properties for all 
 * steps to be tested
 * 
 * @author dragos
 *
 */
public class ReportAlgorithmStepTC extends ReportengineTC {
	
	private IAlgorithmContext TEST_REPORT_CONTEXT; 
	private OutputDispatcher TEST_OUTPUT_DISPATCHER; 
	
	private MemoryOutput cumulativeReportOutput = null;
	
	protected void setUp() throws Exception {
		super.setUp();
		
		cumulativeReportOutput = new MemoryOutput();
		
		TEST_OUTPUT_DISPATCHER = new OutputDispatcher();
		TEST_OUTPUT_DISPATCHER.registerOutput(cumulativeReportOutput);
		TEST_OUTPUT_DISPATCHER.registerOutput(new LoggerOutput());
		
		TEST_REPORT_CONTEXT = new DefaultAlgoContext();
		TEST_REPORT_CONTEXT.setOutput(TEST_OUTPUT_DISPATCHER);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}
	
	protected IAlgorithmContext getTestContext(){
		return TEST_REPORT_CONTEXT;
	}
	
	protected void setAggLevel(int aggLevel){
		TEST_REPORT_CONTEXT.set(GroupingLevelDetectorStep.CONTEXT_KEY_NEW_GROUPING_LEVEL, aggLevel);
	}
	
	protected void setCalculatorMatrix(ICalculator[][] calculators){
		TEST_REPORT_CONTEXT.set(TotalsCalculatorStep.CONTEXT_KEY_CALCULATORS, calculators);
	}
	
	protected void setComputedInputValues(Object[] computedValues){
		TEST_REPORT_CONTEXT.set(ComputeColumnValuesStep.CONTEXT_KEY_COMPUTED_CELL_VALUES, computedValues);
	}
	
	protected void setPreviousGroupValues(Object[] prevValues){
		TEST_REPORT_CONTEXT.set(PreviousRowManagerStep.CONTEXT_KEY_LAST_GROUPING_VALUES, prevValues);
	}
	
	protected MemoryOutput getTestOutput(){
		return cumulativeReportOutput;
	}
	
	protected void registerOutput(IReportOutput output){
		TEST_OUTPUT_DISPATCHER.registerOutput(output);
	}
	
	protected void assertEqualsCalculatorValues(ICalculator[][] expectedValues){
		ICalculator[][] calcMatrix = (ICalculator[][])TEST_REPORT_CONTEXT.get(TotalsCalculatorStep.CONTEXT_KEY_CALCULATORS);
		assertEquals(expectedValues.length, calcMatrix.length);
		for(int i=0; i< expectedValues.length; i++){
			assertEquals(expectedValues[i].length, calcMatrix[i].length);
			for(int j=0; j < expectedValues[i].length; j++){
				assertEquals(expectedValues[i][j].getResult(), calcMatrix[i][j].getResult());
			}
		}
	}
}
