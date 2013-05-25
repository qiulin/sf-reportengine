/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.util.IOKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class AlgorithmContainer implements Algorithm {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmContainer.class);
	
	private List<Algorithm> algos = new ArrayList<Algorithm>(); 
	
	private Map<IOKeys, Object> initialInput = new EnumMap<IOKeys, Object>(IOKeys.class); 
	
	/**
	 * 
	 */
	public AlgorithmContainer(){
		
	}
	
	public void addAlgo(Algorithm newAlgo){
		algos.add(newAlgo); 
	}
	
	public void setIn(Map<IOKeys, Object> input){
		this.initialInput = input; 
	}
	
	/**
	 * calls the execute method of each algorithm and passes the results from one to the other 
	 * until the end of the algorithm list is reached
	 */
	public void execute(){
		Map<IOKeys, Object> input = initialInput; 
		Map<IOKeys, Object> result = null; 
		 
		for (Iterator<Algorithm> algoIterator = algos.iterator(); algoIterator.hasNext(); ) {
			Algorithm algo = algoIterator.next(); 
			algo.setIn(input);
			algo.execute(); 
			
			result = algo.getResultMap(); 
			
			if(algoIterator.hasNext()){
				//transform this algo's output in next algo's input
				input.putAll(result);
			}
		}
		
		LOGGER.info("algo container final result {}", result);
	}

	public void addIn(IOKeys key, Object value) {
		initialInput.put(key, value); 
	}

	public Map<IOKeys, Object> getResultMap() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getResult(IOKeys key) {
		// TODO Auto-generated method stub
		return null;
	}
}
