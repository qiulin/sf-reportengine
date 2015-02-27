/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractInitStep implements AlgorithmInitStep {
	
	/**
	 * reference to algo context
	 */
	private AlgoContext algoContext = null;
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(java.util.Map, net.sf.reportengine.core.algorithm.AlgoContext)
	 */
	public Map<IOKeys, Object> init(Map<IOKeys, Object> algoInput, AlgoContext algoContext) {
		this.algoContext = algoContext; 
		return executeInit(algoInput); 
	}
	
	/**
	 * call this method for the needed processing 
	 */
	protected abstract Map<IOKeys, Object> executeInit(Map<IOKeys, Object> inputParams); 
	
	/**
	 * 
	 * @return
	 */
	public AlgoContext getAlgoContext(){
		return algoContext; 
	}
}
