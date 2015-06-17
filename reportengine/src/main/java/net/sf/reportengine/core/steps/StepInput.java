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

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

public class StepInput {
	
	private final Map<IOKeys, Object> algoInput; 
	private final AlgoContext context; 
	
	
	public StepInput(Map<IOKeys, Object> algoInput, AlgoContext context){
		this.algoInput = algoInput; 
		this.context = context; 
	}
	
	
	public Object getAlgoInput(IOKeys key){
		return algoInput.get(key); 
	}
	
	public Object getContextParam(ContextKeys key){
		return context.get(key); 
	}
}
