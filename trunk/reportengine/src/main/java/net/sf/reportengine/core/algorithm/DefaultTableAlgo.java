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
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.AlgoIOKeys;
import net.sf.reportengine.util.StepIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * This multi step algorithm performs the following operations : 1. transfer the
 * algo input into the context 2. opens the input 3. loops through the
 * reportInput and executes the algorithm steps 4. closes the input 5. transfer
 * the context into algo output
 * </p>
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public abstract class DefaultTableAlgo extends AbstractMultiStepAlgo {

    /**
     * the one and only logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(DefaultTableAlgo.class);

    /**
     * builds the report input from the input parameters
     * 
     * @param inputParams
     *            the map of input parameters
     * @return the input stream which will be looped
     */
    protected abstract TableInput buildTableInput(Map<AlgoIOKeys, Object> inputParams);

    /**
     * 
     */
    public DefaultTableAlgo(String algorithmName) {
        super(algorithmName);
    }

    /**
     * executes the report
     */
    public Map<AlgoIOKeys, Object> execute(Map<AlgoIOKeys, Object> inputParams) {

        Map<AlgoIOKeys, Object> algoResult = new EnumMap<AlgoIOKeys, Object>(AlgoIOKeys.class);

        LOGGER.debug("creating the context");
        AlgoContext context = new DefaultAlgorithmContext();

        TableInput reportInput = null;
        try {
            LOGGER.debug("opening report input");
            reportInput = buildTableInput(inputParams);
            reportInput.open();

            LOGGER.debug("start looping");
            StepResult stepResult = null;

            // execution of the init steps
            for (AlgorithmInitStep initStep : getInitSteps()) {
                stepResult = initStep.init(new StepInput(inputParams, context));
                processStepResult(context, algoResult, stepResult);
            }

            // call init for each step
            for (AlgorithmMainStep mainStep : getMainSteps()) {
                stepResult = mainStep.init(new StepInput(inputParams, context));
                processStepResult(context, algoResult, stepResult);
            }

            // iteration through input data (row by row)
            while (reportInput.hasMoreRows()) {

                // get the current data row
                List<Object> currentRow = reportInput.nextRow();
                LOGGER.trace("executing algo steps for input row {}", currentRow);

                // then we pass the dataRow through all the report steps
                for (AlgorithmMainStep algoStep : getMainSteps()) {
                    stepResult =
                        algoStep.execute(new NewRowEvent(currentRow), new StepInput(inputParams,
                                                                                    context));
                    processStepResult(context, algoResult, stepResult);
                }
            }

            // call exit
            for (AlgorithmMainStep mainStep : getMainSteps()) {
                stepResult = mainStep.exit(new StepInput(inputParams, context));
                processStepResult(context, algoResult, stepResult);
            }

            // calling the exit for all registered steps
            for (AlgorithmExitStep exitStep : getExitSteps()) {
                stepResult = exitStep.exit(new StepInput(inputParams, context));
                processStepResult(context, algoResult, stepResult);
            }
            // } catch (Throwable exc) {
            // LOGGER.error(exc.getMessage(), exc);
            // throw new ReportEngineRuntimeException(exc);
        } finally {
            LOGGER.debug("end loop. Closing algorithm input...");
            if (reportInput != null) {
                reportInput.close();
            }
        }

        return algoResult;
    }

    private void processStepResult(AlgoContext context,
                                   Map<AlgoIOKeys, Object> result,
                                   StepResult stepResult) {

        if (stepResult != null && !StepResult.NO_RESULT.equals(stepResult)) {
            if (!StepIOKeys.SKIP_CONTEXT_KEY.equals(stepResult.getKey())) {
                context.set(stepResult.getKey(), stepResult.getValue());
            }
            if (!AlgoIOKeys.NO_KEY.equals(stepResult.getIOKey())) {
                result.put(stepResult.getIOKey(), stepResult.getValue());
            }
        }
    }
}
