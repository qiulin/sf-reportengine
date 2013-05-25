/*
 * Created on 30.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.util.IOKeys;


/**
 * abstract implementation for the IAlgorithmStep providing 
 * an algoContext and default implementations for init(), exit() methods.
 * 
 * @author dragos balan(dragos.balan@gmail.com)
 */
public abstract class AbstractAlgoMainStep implements AlgorithmMainStep {
    
    /**
     * this is a reference to the report context
     */
    private AlgoContext algoContext;
    
    /**
     * input parameters of this algorithm 
     */
    private Map<IOKeys, Object> algoInput; 
    
    /**
     * lazy init map ( most of the steps don't have results)
     */
    private Map<IOKeys, Object> stepResults = null; 
    
    /**
     * default implementation for AlgorithmInitStep.init() method
     * which only sets the algorithm context  
     * 
     */
    public void init(Map<IOKeys, Object> algoInput, AlgoContext algoContext){
        this.algoContext = algoContext;    
        this.algoInput = algoInput; 
    }
    
    /**
     * just an empty implementation for exit 
     * @see net.sf.reportengine.core.algorithm.AlgorithmMainStep#exit()
     */
    public void exit(Map<IOKeys,Object> algoInput, AlgoContext context) {}
    
    
    /**
     * getter for the context
     * @return
     */
    protected AlgoContext getAlgoContext(){
    	return algoContext;
    }
    
    
    protected Map<IOKeys, Object> getAlgoInput(){
    	return algoInput; 
    }
    
    
    public Map<IOKeys, Object> getResultsMap(){
    	return stepResults; 
    }
    
    protected void addResult(IOKeys keys, Object value){
    	if(stepResults == null){
    		stepResults = new EnumMap<IOKeys, Object>(IOKeys.class); 
    	}
    	stepResults.put(keys, value); 
    }
}
