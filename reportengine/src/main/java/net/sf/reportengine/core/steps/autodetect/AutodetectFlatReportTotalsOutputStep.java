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
package net.sf.reportengine.core.steps.autodetect;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.FlatReportTotalsOutputStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;

/**
 * @author dragos balan
 * @since 0.8
 */
public class AutodetectFlatReportTotalsOutputStep extends FlatReportTotalsOutputStep {
	
	private boolean hasTotals = false; 
	
	@Override 
	public StepResult<String> init(StepInput stepInput){
		super.init(stepInput); 
		hasTotals = getShowTotals(stepInput) || getShowGrandTotal(stepInput); 
		return StepResult.NO_RESULT; 
	}
	
	@Override 
	public StepResult<Integer> execute(NewRowEvent newRowEvent, StepInput stepInput){
		StepResult<Integer> stepResult = null;
		if(hasTotals){
			stepResult = super.execute(newRowEvent, stepInput); 
		}
		return stepResult; 
	}
}
