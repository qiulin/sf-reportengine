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
package net.sf.reportengine.core.steps.autodetect;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.PreviousRowManagerStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.AlgoIOKeys;

/**
 * this is just a PreviousRowManagerStep which verifies at runtime if the report has group columns 
 * in order to execute itself
 * 
 * @author dragos balan
 * @since 0.8
 *
 */
public class AutodetectPreviousRowManagerStep extends PreviousRowManagerStep{
	
	/**
	 * flag
	 */
	private boolean reportHasGroups; 
	
	/**
	 * 
	 */
	@Override
	public StepResult<String> init(StepInput stepInput){
		reportHasGroups = getGroupColumns(stepInput) != null && getGroupColumns(stepInput).size() > 0;
		return StepResult.NO_RESULT; 
	}
	
	@Override 
	public StepResult<Object[]> execute(NewRowEvent rowEvent, StepInput stepInput) {
		StepResult<Object[]> stepResult = null; 
		if(reportHasGroups){
			stepResult = super.execute(rowEvent, stepInput);
		}
		return stepResult; 
	}
}
