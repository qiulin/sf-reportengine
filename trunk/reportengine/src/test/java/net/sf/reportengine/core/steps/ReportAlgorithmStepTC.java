/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.reportengine.core.algorithm.DefaultReportContext;
import net.sf.reportengine.core.algorithm.ReportContext;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.LoggerOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.InputKeys;

/**
 * utility test case containing usefull constants and properties for all 
 * steps to be tested
 * 
 * @author dragos balan
 *
 */
public class ReportAlgorithmStepTC extends TestCase {
	
	private ReportContext TEST_REPORT_CONTEXT; 
	private OutputDispatcher TEST_OUTPUT_DISPATCHER; 
	
	private CellPropsArrayOutput cumulativeReportOutput = null;
	private Map<InputKeys, Object> mockAlgoInput = new EnumMap<InputKeys, Object>(InputKeys.class);
	
	protected void setUp() throws Exception {
		super.setUp();
		
		cumulativeReportOutput = new CellPropsArrayOutput();
		
		TEST_OUTPUT_DISPATCHER = new OutputDispatcher();
		TEST_OUTPUT_DISPATCHER.registerOutput(cumulativeReportOutput);
		TEST_OUTPUT_DISPATCHER.registerOutput(new LoggerOutput());
		
		TEST_REPORT_CONTEXT = new DefaultReportContext();
		mockAlgoInput.put(InputKeys.REPORT_OUTPUT, TEST_OUTPUT_DISPATCHER);
	}
	
	
	protected ReportContext getTestContext(){
		return TEST_REPORT_CONTEXT;
	}
	
	protected void setAggLevel(int aggLevel){
		TEST_REPORT_CONTEXT.set(ContextKeys.NEW_GROUPING_LEVEL, aggLevel);
	}
	
	protected void setCalculatorMatrix(Calculator[][] calculators){
		TEST_REPORT_CONTEXT.set(ContextKeys.CALCULATORS, calculators);
	}
	
//	protected void setComputedInputValues(Object[] computedValues){
//		TEST_REPORT_CONTEXT.set(ContextKeys.COMPUTED_CELL_VALUES, computedValues);
//	}
	
	protected void setPreviousGroupValues(Object[] prevValues){
		TEST_REPORT_CONTEXT.set(ContextKeys.LAST_GROUPING_VALUES, prevValues);
	}
	
	protected CellPropsArrayOutput getTestOutput(){
		return cumulativeReportOutput;
	}
	
	protected void assertEqualsCalculatorValues(Calculator[][] expectedValues){
		Calculator[][] calcMatrix = (Calculator[][])TEST_REPORT_CONTEXT.get(ContextKeys.CALCULATORS);
		assertEquals(expectedValues.length, calcMatrix.length);
		for(int i=0; i< expectedValues.length; i++){
			assertEquals(expectedValues[i].length, calcMatrix[i].length);
			for(int j=0; j < expectedValues[i].length; j++){
				assertEquals(expectedValues[i][j].getResult(), calcMatrix[i][j].getResult());
			}
		}
	}
}
