/**
 * 
 */
package net.sf.reportengine.core.algorithm.steps;

import java.util.Map;

import net.sf.reportengine.core.algorithm.AlgoContext;
import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.IOKeys;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractInitStep<U> implements AlgorithmInitStep<U> {
	
//	/* (non-Javadoc)
//	 * @see net.sf.reportengine.core.algorithm.steps.AlgorithmInitStep#init(java.util.Map, net.sf.reportengine.core.algorithm.AlgoContext)
//	 */
//	public StepResult<U> init(Map<IOKeys, Object> algoInput, AlgoContext algoContext) {
//		this.algoContext = algoContext; 
//		return executeInit(algoInput); 
//	}
//	
//	/**
//	 * call this method for the needed processing 
//	 */
//	protected abstract StepResult<U> executeInit(Map<IOKeys, Object> inputParams); 
//	
//	/**
//	 * 
//	 * @return
//	 */
//	public AlgoContext getAlgoContext(){
//		return algoContext; 
//	}
}
