/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractExitStep implements AlgorithmExitStep {
	
	/**
	 * a reference to algo input
	 */
	private Map<IOKeys, Object> algoInput = null;
	
	/**
	 * reference to algo context
	 */
	private AlgoContext algoContext = null;
	
	/**
	 * the results after processing this step
	 */
	private Map<IOKeys, Object> stepResults = null;
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmExitStep#exit(java.util.Map, net.sf.reportengine.core.algorithm.AlgoContext)
	 */
	public void exit(Map<IOKeys, Object> algoInput, AlgoContext context) {
		this.algoInput = algoInput; 
		this.algoContext = context; 
		executeExit(); 
	}
	
	
	protected abstract void executeExit(); 
	
	/**
	 * 
	 * @return
	 */
	public AlgoContext getAlgoContext(){
		return algoContext; 
	}
	
	/**
	 * 
	 * @return
	 */
	public Map<IOKeys, Object> getAlgoInput(){
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
