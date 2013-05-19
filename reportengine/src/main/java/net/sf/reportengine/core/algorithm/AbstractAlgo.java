/**
 * 
 */
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractAlgo implements Algorithm {
	
	/**
	 * the input parameters map
	 */
    private Map<IOKeys, Object> inputMap = new EnumMap<IOKeys, Object>(IOKeys.class); 
    
    /**
     * the result (output) map
     */
    private Map<IOKeys, Object> resultMap = new EnumMap<IOKeys, Object>(IOKeys.class); 
    
    /**
     * implementation for IReportEngine.setIn
     */
    public void setIn(Map<IOKeys, Object> input){
		this.inputMap = input;
		
    }
    
    public void addIn(IOKeys key, Object value){
    	this.inputMap.put(key, value); 
    }
    
    protected Map<IOKeys,Object> getInput(){
    	return inputMap; 
    }
    
    public Map<IOKeys, Object> getResultMap(){
    	return resultMap; 
    }
    
    protected void setResultMap(Map<IOKeys, Object> result){
    	this.resultMap = result; 
    }
    
    protected void addResult(IOKeys key, Object value){
    	resultMap.put(key, value); 
    }
    
    public Object getResult(IOKeys key){
    	return resultMap.get(key); 
    }
}
