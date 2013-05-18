/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.util.InputKeys;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos
 *
 */
public class AlgorithmContainer {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AlgorithmContainer.class);
	
	private List<Algorithm> algos = new ArrayList<Algorithm>(); 
	
	private List<AlgoInput> initialInput = new ArrayList<AlgoInput>(); 
	
	public AlgorithmContainer(){
		
	}
	
	public void setInput(List<AlgoInput> input){
		this.initialInput = input; 
	}
	
	public void execute(){
		List<AlgoInput> input = initialInput; 
		Map<String, Object> result = null; 
		 
		for (Iterator<Algorithm> algoIterator = algos.iterator(); algoIterator.hasNext(); ) {
			Algorithm algo = algoIterator.next(); 
			algo.setIn(input);
			algo.execute(); 
			
			result = algo.getResultMap(); 
			
			if(algoIterator.hasNext()){
				//transform this algo's output in next algo's input
				input = transformResult(result);
			}
		}
		
		LOGGER.info("algo container final result {}", result);
	}
	
	private List<AlgoInput> transformResult(Map<String, Object> resultMap){
		List<AlgoInput> resultInput = new ArrayList<AlgoInput>(resultMap.size()); 
		for (Map.Entry<String, Object> resultItem : resultMap.entrySet()) {
			resultInput.add(new AlgoInput(resultItem.getValue(), InputKeys.valueOf(resultItem.getKey()))); 
		}
		return resultInput; 
	}
}
