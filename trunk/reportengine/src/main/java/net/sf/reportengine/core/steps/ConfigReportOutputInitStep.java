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

import net.sf.reportengine.out.ReportOutput;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public class ConfigReportOutputInitStep extends AbstractReportInitStep<ReportOutput> {

	public StepResult<ReportOutput> init(StepInput input) {
		ReportOutput output = (ReportOutput)input.getAlgoInput(IOKeys.REPORT_OUTPUT); 
		//getAlgoContext().set(ContextKeys.LOCAL_REPORT_OUTPUT, output);
		return new StepResult<ReportOutput>(ContextKeys.LOCAL_REPORT_OUTPUT, output); 
	}
}
