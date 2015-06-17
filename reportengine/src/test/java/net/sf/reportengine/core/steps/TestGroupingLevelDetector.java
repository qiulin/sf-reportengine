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
package net.sf.reportengine.core.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.scenarios.CalculatedColumnsScenario;
import net.sf.reportengine.scenarios.Scenario1;
import net.sf.reportengine.scenarios.Scenario2;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

public class TestGroupingLevelDetector extends ReportAlgorithmStepTC {
	
	
	private GroupLevelDetectorStep classUnderTest;
	
	protected void setUp() throws Exception {
		super.setUp();
		classUnderTest = new GroupLevelDetectorStep(); 
	}
	
	
	public void testExecuteScenario1() {
		AlgoContext testReportContext = getTestContext();
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, Scenario1.GROUPING_COLUMNS);
		mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario1.GROUPING_COLUMNS);
		
		classUnderTest.init(new StepInput(mockAlgoInput, testReportContext));
		
		for(int i=0; i<Scenario1.RAW_DATA.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario1.RAW_DATA[i]);
			StepResult<Integer> stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, testReportContext));
			
			assertNotNull(stepResult);
			assertEquals(Scenario1.AGG_LEVEL[i], stepResult.getValue()); //testReportContext.get(ContextKeys.NEW_GROUPING_LEVEL)
			
			testReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario1.PREVIOUS_GROUP_VALUES[i]);
		}
	}
	
	
	public void testExecuteScenario2() {
		AlgoContext testReportContext = getTestContext();
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class);
		
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, Scenario2.GROUPING_COLUMNS);
		mockAlgoInput.put(IOKeys.GROUP_COLS, Scenario2.GROUPING_COLUMNS);
		classUnderTest.init(new StepInput(mockAlgoInput, testReportContext));
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(Scenario2.RAW_INPUT[i]);
			StepResult<Integer> stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, testReportContext));
			
			assertNotNull(stepResult);
			assertEquals(Scenario2.AGG_LEVEL[i], stepResult.getValue()); //testReportContext.get(ContextKeys.NEW_GROUPING_LEVEL)
			
			testReportContext.set(ContextKeys.LAST_GROUPING_VALUES, Scenario2.PREVIOUS_GROUP_VALUES[i]);
		}
	}

	
	
	public void testExecuteCalculatedColumnsScenario() {
		AlgoContext testReportContext = getTestContext();
		Map<IOKeys, Object> mockAlgoInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
		
		mockAlgoInput.put(IOKeys.GROUP_COLS, CalculatedColumnsScenario.GROUP_COLUMNS);
		mockAlgoInput.put(IOKeys.DATA_COLS, CalculatedColumnsScenario.DATA_COLUMNS);
		
		//testReportContext.set(ContextKeys.DATA_COLUMNS, CalculatedColumnsScenario.DATA_COLUMNS);
		//testReportContext.set(ContextKeys.GROUP_COLUMNS, CalculatedColumnsScenario.GROUP_COLUMNS);
		
		classUnderTest.init(new StepInput(mockAlgoInput, testReportContext));
		
		for(int i=0; i<Scenario2.RAW_INPUT.length; i++){
			NewRowEvent dataRowEvent = new NewRowEvent(CalculatedColumnsScenario.RAW_DATA[i]);
			//testReportContext.set(ContextKeys.COMPUTED_CELL_VALUES, CalculatedColumnsScenario.COMPUTED_VALUES[i]);
			StepResult<Integer> stepResult = classUnderTest.execute(dataRowEvent, new StepInput(mockAlgoInput, testReportContext));
			
			assertNotNull(stepResult);
			assertEquals(CalculatedColumnsScenario.AGG_LEVEL[i], stepResult.getValue()); //testReportContext.get(ContextKeys.NEW_GROUPING_LEVEL)
			
			testReportContext.set(ContextKeys.LAST_GROUPING_VALUES, CalculatedColumnsScenario.PREVIOUS_GROUP_VALUES[i]);
		}
	}
}
