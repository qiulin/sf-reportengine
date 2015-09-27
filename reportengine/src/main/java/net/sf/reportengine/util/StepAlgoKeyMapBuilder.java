/**
 * copyright 2015 dragos balan
 */
package net.sf.reportengine.util;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author dragos balan
 *
 */
public class StepAlgoKeyMapBuilder {
    
    private Map<StepIOKeys, AlgoIOKeys> result;
    
    public StepAlgoKeyMapBuilder(){
        result = new HashMap<StepIOKeys, AlgoIOKeys>(); 
    }
    
    public StepAlgoKeyMapBuilder add(StepIOKeys stepKey, AlgoIOKeys algoKey){
        result.put(stepKey, algoKey); 
        return this; 
    }
    
    public Map<StepIOKeys, AlgoIOKeys> build(){
        return result; 
    }
}
