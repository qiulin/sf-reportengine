/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.reportengine.util.InputKeys;

/**
 * @author dragos
 *
 */
public abstract class AbstractAlgo implements Algorithm {
	
    private Map<InputKeys, Object> inputMap = new EnumMap<InputKeys, Object>(InputKeys.class); 
    
    private Map<String, Object> resultMap = new HashMap<String, Object>(); 
    
    /**
     * implementation for IReportEngine.setIn
     */
    public void setIn(List<AlgoInput> inputList){
        for (AlgoInput algoInput : inputList) {
			this.inputMap.put(algoInput.getName(), algoInput.getValue()); 
		}
    }
    
    public void addIn(AlgoInput input){
    	this.inputMap.put(input.getName(), input.getValue()); 
    }
    
    protected Map<InputKeys,Object> getInput(){
    	return inputMap; 
    }
    
    public Map<String, Object> getResultMap(){
    	return resultMap; 
    }
    
    protected void setResultMap(Map<String, Object> result){
    	this.resultMap = result; 
    }
    
    protected void addResult(AlgoResult result){
    	resultMap.put(result.getName(), result.getValue()); 
    }
    
    protected void addResult(String name, Object value){
    	resultMap.put(name, value); 
    }
    
    public Object getResult(String name){
    	return resultMap.get(name); 
    }
    
    
}
