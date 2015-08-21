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

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;

/**
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractMultiStepAlgo extends AbstractAlgo {

    /**
     * A list containing
     * <code>net.sf.reportengine.algorithm.IAlgorithmInitStep</code>s to be
     * performed only once (at the begining of the algorithm)
     */
    private List<AlgorithmInitStep> initSteps = new ArrayList<AlgorithmInitStep>();

    /**
     * A list containing all the steps
     * <code>net.sf.reportengine.algorithm.IAlgoritmStep</code>
     */
    private List<AlgorithmMainStep> mainSteps = new ArrayList<AlgorithmMainStep>();

    /**
     * A list containing
     * <code>net.sf.reportengine.algorithm.IAlgorithmExitStep</code>s to be
     * performed only once (at the begining of the algorithm)
     */
    private List<AlgorithmExitStep> exitSteps = new ArrayList<AlgorithmExitStep>();

    /**
     * default empty
     */
    public AbstractMultiStepAlgo() {

    }

    /**
     * adds a new step to the algorithm
     * 
     * @param newStep
     *            the new step to be added to the report algorithm
     */
    public void addMainStep(AlgorithmMainStep newStep) {
        mainSteps.add(newStep);
    }

    /**
     * adds a new init step to the algorithm
     * 
     * @param initStep
     *            the step to be added
     */
    public void addInitStep(AlgorithmInitStep initStep) {
        initSteps.add(initStep);
    }

    /**
     * adds a new exit step to the algorithm
     * 
     * @param exitStep
     *            the step to be added
     */
    public void addExitStep(AlgorithmExitStep exitStep) {
        exitSteps.add(exitStep);
    }

    public List<AlgorithmInitStep> getInitSteps() {
        return initSteps;
    }

    public List<AlgorithmExitStep> getExitSteps() {
        return exitSteps;
    }

    public List<AlgorithmMainStep> getMainSteps() {
        return mainSteps;
    }
}
