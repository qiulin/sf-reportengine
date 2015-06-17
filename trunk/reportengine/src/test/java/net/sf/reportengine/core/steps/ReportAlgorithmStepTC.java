/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import junit.framework.TestCase;
import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.DefaultAlgorithmContext;
import net.sf.reportengine.core.calc.CalcIntermResult;
import net.sf.reportengine.out.CellPropsArrayOutput;
import net.sf.reportengine.out.LoggerOutput;
import net.sf.reportengine.out.OutputDispatcher;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * utility test case containing usefull constants and properties for all 
 * steps to be tested
 * 
 * @author dragos balan
 *
 */
public class ReportAlgorithmStepTC extends TestCase {
	
	private AlgoContext TEST_REPORT_CONTEXT; 
	private OutputDispatcher TEST_OUTPUT_DISPATCHER; 
	
	private CellPropsArrayOutput cumulativeReportOutput = null;
	private Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
	
	protected void setUp() throws Exception {
		super.setUp();
		
		cumulativeReportOutput = new CellPropsArrayOutput();
		
		TEST_OUTPUT_DISPATCHER = new OutputDispatcher();
		TEST_OUTPUT_DISPATCHER.registerOutput(cumulativeReportOutput);
		TEST_OUTPUT_DISPATCHER.registerOutput(new LoggerOutput());
		
		TEST_REPORT_CONTEXT = new DefaultAlgorithmContext();
		mockAlgoInput.put(IOKeys.REPORT_OUTPUT, TEST_OUTPUT_DISPATCHER);
	}
	
	
	protected AlgoContext getTestContext(){
		return TEST_REPORT_CONTEXT;
	}
	
	protected void setAggLevel(int aggLevel){
		TEST_REPORT_CONTEXT.set(ContextKeys.NEW_GROUPING_LEVEL, aggLevel);
	}
	
	protected void setCalcIntermResults(CalcIntermResult[][] calcIntermResults){
		TEST_REPORT_CONTEXT.set(ContextKeys.CALC_INTERM_RESULTS, calcIntermResults);
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
	
	protected void assertEqualsCalculatorResults(CalcIntermResult[][] expectedValues){
		CalcIntermResult[][] calcMatrix = (CalcIntermResult[][])TEST_REPORT_CONTEXT.get(ContextKeys.CALC_INTERM_RESULTS);
		assertEquals(expectedValues.length, calcMatrix.length);
		for(int i=0; i< expectedValues.length; i++){
			assertEquals(expectedValues[i].length, calcMatrix[i].length);
			for(int j=0; j < expectedValues[i].length; j++){
				assertEquals(expectedValues[i][j].getResult(), calcMatrix[i][j].getResult());
			}
		}
	}
}
