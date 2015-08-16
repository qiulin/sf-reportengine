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

import java.util.Map;

import net.sf.reportengine.in.TableInput;
import net.sf.reportengine.util.AlgoIOKeys;


/**
 * This is basically a {@link OpenLoopCloseInputAlgo} which identifies the input from inputParams map by the IOKey.REPORT_INPUT key. 
 * As any other {@link OpenLoopCloseInputAlgo} this algo performes the following actions: 
 * 1. opens the report input 
 * 2. loops through the report input and calls the steps.execute methods
 * 3. closes the input
 * 
 * @author dragos balan
 *
 */
public class DefaultLoopThroughReportInputAlgo extends OpenLoopCloseInputAlgo {

	@Override
	protected TableInput buildTableInput(Map<AlgoIOKeys, Object> inputParams) {
		return (TableInput) inputParams.get(AlgoIOKeys.TABLE_INPUT); 
	}

}