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
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.InMemoryTableInput;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.StepIOKeys;

import org.apache.commons.lang.math.NumberUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author dragos balan
 *
 */
public class TestOneIterationAlgorithm {

    private TableInput testInput =
        new InMemoryTableInput(new Object[][] { new String[] { "1", "2", "3" }, new String[] { "4", "5", "6" } });

    private AlgorithmInitStep testInitStep = new AlgorithmInitStep<Integer>() {
        public StepResult<Integer> init(StepInput stepInput) {
            return new StepResult<Integer>(StepIOKeys.DATA_ROW_COUNT,
                                           Integer.valueOf(0),
                                           AlgoIOKeys.TEST_KEY);
        }
    };

    private AlgorithmMainStep testMainStep = new AlgorithmMainStep<Integer, Integer, String>() {

        public StepResult<Integer> init(StepInput stepInput) {
            // this.context.set(ContextKeys.DATA_ROW_COUNT, Integer.valueOf(1));
            // this.context.set(ContextKeys.NEW_GROUPING_LEVEL, new Integer(0));
            return new StepResult(StepIOKeys.NO_KEY, NumberUtils.INTEGER_ZERO);
        }

        public StepResult<Integer> execute(NewRowEvent dataRowEvent, StepInput stepInput) {
            Integer executionCounts = (Integer) stepInput.getContextParam(StepIOKeys.NO_KEY);
            // context.set(ContextKeys.NEW_GROUPING_LEVEL, executionCounts+1);
            return new StepResult<Integer>(StepIOKeys.NO_KEY,
                                           executionCounts + 1,
                                           AlgoIOKeys.TEST_KEY);
        }

        public StepResult<String> exit(StepInput stepInput) {
            return StepResult.NO_RESULT;
        }
    };

    private DefaultTableAlgo classUnderTest = null;

    /*
     * (non-Javadoc)
     * 
     * @see junit.framework.TestCase#setUp()
     */
    @Before
    public void setUp() throws Exception {
        classUnderTest = new DefaultLoopThroughTableInputAlgo("Test Loop Through TableInput");

        classUnderTest.addInitStep(testInitStep);
        classUnderTest.addMainStep(testMainStep);
    }

    /**
     * Test method for
     * {@link net.sf.reportengine.core.algorithm.DefaultTableAlgo#execute()}.
     */
    @Test
    public void testExecuteAlgorithm() {
        Map<AlgoIOKeys, Object> mockAlgoInput = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);
        mockAlgoInput.put(AlgoIOKeys.TABLE_INPUT, testInput);
        // mockAlgoInput.put(IOKeys.REPORT_OUTPUT, testOut);

        Map<AlgoIOKeys, Object> algoResult = classUnderTest.execute(mockAlgoInput);
        Assert.assertEquals(Integer.valueOf(2), (Integer) algoResult.get(AlgoIOKeys.TEST_KEY));
    }
}
