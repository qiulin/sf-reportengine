/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;

import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.ContextKeys;

/**
 * this class holds important values of the current algorithm. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public class DefaultAlgorithmContext implements AlgoContext{
    
	/**
     * an enumMap containing the context data
     */
    private EnumMap<ContextKeys, Object> contextData;
    
    /**
     * constructor of the class
     */
    public DefaultAlgorithmContext(){
        contextData = new EnumMap<ContextKeys, Object>(ContextKeys.class);
    }
    
    /**
     * gets the value whithin the context having the specified identifier
     * 
     * @return the object associated with the key
     */
    public Object get(ContextKeys key){
        return contextData.get(key);
    }
    
    /**
     * @see net.sf.reportengine.core.algorithm.AlgoContext#get(String)
     */
    public void set(ContextKeys key, Object value){
        contextData.put(key,value);
    }
    
    /**
     * 
     */
    public void add(StepResult stepResult){
    	contextData.put(stepResult.getKey(), stepResult.getValue()); 
    }
}
