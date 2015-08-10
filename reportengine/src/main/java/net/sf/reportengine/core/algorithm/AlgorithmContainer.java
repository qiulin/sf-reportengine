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
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.util.AlgoIOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A container ( list ) of algorithms which itself implements the {@link Algorithm} interface. 
 * The execute method calls all Algorithm.execute() methods and passes the result of each one as input
 * for the next one. 
 * 
 * 
 * @author dragos balan
 *
 */
public class AlgorithmContainer implements Algorithm {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmContainer.class);
	
	private List<Algorithm> algos = new ArrayList<Algorithm>(); 
	
//	private Map<IOKeys, Object> initialInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
	
	/**
	 * 
	 */
	public AlgorithmContainer(){
		
	}
	
	public void addAlgo(Algorithm newAlgo){
		algos.add(newAlgo); 
	}
	
//	public void setIn(Map<IOKeys, Object> input){
//		this.initialInput = input; 
//	}
	
	/**
	 * calls the execute method of each algorithm and passes the results from one to the other 
	 * until the end of the algorithm list is reached
	 */
	public Map<AlgoIOKeys, Object> execute(Map<AlgoIOKeys, Object> input){
		//Map<IOKeys, Object> input = initialInput; 
		Map<AlgoIOKeys, Object> result = null; 
		 
		for (Iterator<Algorithm> algoIterator = algos.iterator(); algoIterator.hasNext(); ) {
			Algorithm algo = algoIterator.next(); 
			
			//execution of the algorithm
			result = algo.execute(input); 
			
			if(algoIterator.hasNext()){
				//transform this algo's output in next algo's input
				LOGGER.debug("passing parameters from one algo to the next : {}", result);
				input.putAll(result);
				//input = result;
			}
		}
		
		LOGGER.debug("algo container final result {}", result);
		return result; 
	}

//	public void addIn(IOKeys key, Object value) {
//		initialInput.put(key, value); 
//	}
//	
//	/**
//	 * returns null as this container viewed as Algorithm has 
//	 * no results
//	 */
//	public Map<IOKeys, Object> getResultMap() {
//		return null;
//	}
//	
//	/**
//	 * returns null as this this container has no results
//	 */
//	public Object getResult(IOKeys key) {
//		return null;
//	}
}
