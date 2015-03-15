/*
 * Created on 28.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import net.sf.reportengine.core.steps.StepResult;
import net.sf.reportengine.util.ContextKeys;


/**
 * The algorithm context is a local-to-the-algorithm storage used for sharing information among algorithm steps. 
 *
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public interface AlgoContext {
    
	/**
	 * adds the result of a step into the context
	 * 
	 * @param stepResult
	 */
	public void add(StepResult stepResult);
	
    /**
     * adds the pair (key, object) in the report context
     * 
     * @param key		the identifier of the given object
     * @param obj		the object to be registered
     */
    public void set(ContextKeys key, Object obj);
    
    /**
     * gets a value from the context
     * @param key	the indentifier 
     * @return		the requested value
     */
    public Object get(ContextKeys key);
}
