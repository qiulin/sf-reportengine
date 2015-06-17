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

import net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep;
import net.sf.reportengine.core.algorithm.steps.AlgorithmMainStep;

/**
 * @author dragos
 *
 */
public interface MultiStepAlgo extends Algorithm {
	
	/**
     * add an init step to the algorithm
     * @param initStep
     */
    public void addInitStep(AlgorithmInitStep initStep);
    
    /**
     * adds a main step to the algorithm
     * @param step  the main step to be added
     */
    public void addMainStep(AlgorithmMainStep step);
    
    /**
     * 
     * @param exitStep
     */
    public void addExitStep(AlgorithmExitStep exitStep);
}
