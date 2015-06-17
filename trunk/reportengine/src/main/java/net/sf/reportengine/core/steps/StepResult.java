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

import net.sf.reportengine.util.ContextKeys;
import net.sf.reportengine.util.IOKeys;

/**
 * This is the result of a algorithm step computation. There are two kinds of StepResults: 
 * 
 * 1. Internal step result (whose value is available only internal to the algorithm) 
 * 2. Outside available step resuls (those results will be visible outside the algorithm via IOKeys)
 * 
 * @author dragos balan
 *
 */
public final class StepResult<U> {
	
	
	private final ContextKeys key; 
	private final U value; 
	
	private final IOKeys ioKey; 
	
	public StepResult(ContextKeys key, U value){
		 this(key, value, IOKeys.NO_KEY); 
	}
	
	public StepResult(ContextKeys key, U value, IOKeys ioKey){
		this.key = key; 
		this.value = value; 
		this.ioKey = ioKey;
	}
	
	public ContextKeys getKey(){
		return key; 
	}
	
	public U getValue(){
		return value; 
	}
	
	public IOKeys getIOKey(){
		return ioKey; 
	}
	
	


	public static final StepResult<String> NO_RESULT = new StepResult<String>(ContextKeys.NO_KEY, ""); 
}
