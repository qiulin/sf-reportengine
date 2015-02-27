/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.util.IOKeys;

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
	public Map<IOKeys, Object> execute(Map<IOKeys, Object> input){
		//Map<IOKeys, Object> input = initialInput; 
		Map<IOKeys, Object> result = null; 
		 
		for (Iterator<Algorithm> algoIterator = algos.iterator(); algoIterator.hasNext(); ) {
			Algorithm algo = algoIterator.next(); 
			//algo.setIn(input);
			
			//execution of the algorithm
			result = algo.execute(input); 
			
			//result = algo.getResultMap(); 
			
			if(algoIterator.hasNext()){
				//transform this algo's output in next algo's input
				//input.putAll(result);
				input = result;
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
