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
package net.sf.reportengine.core.steps.neo;

import net.sf.reportengine.core.steps.AbstractReportExitStep;
import net.sf.reportengine.core.steps.StepInput;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.out.AbstractReportOutput;
import net.sf.reportengine.util.IOKeys;

/**
 * @author balan
 *
 */
public class EndTableExitStep extends AbstractReportExitStep<String> {

	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(net.sf.reportengine.core.steps.StepInput)
	 */
	public StepResult<String> exit(StepInput stepInput) {
		((AbstractReportOutput)stepInput.getAlgoInput(IOKeys.NEW_REPORT_OUTPUT)).output("endTable.ftl");
		return StepResult.NO_RESULT; 
	}

}
