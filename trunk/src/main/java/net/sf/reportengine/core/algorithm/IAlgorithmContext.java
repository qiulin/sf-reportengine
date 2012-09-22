/*
 * Created on 28.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;


/**
 * report context interface
 *
 * interface for report context
 * @author dragos balan (dragos.balan@gmail.com)
 */
public interface IAlgorithmContext {
    
    /**
     * registers the pair (key, object) in the report context
     * @param key		the identifier of the given object
     * @param obj		the object to be registered
     */
    public void set(String key, Object obj);
    
    /**
     * gets a value from the context
     * @param key	the indentifier of the reqeusted value
     * @return		the requested value
     */
    public Object get(String key);
    
    
    /**
     * sets the input 
     * @param input
     */
    public void setInput(IAlgorithmInput input);
    
    /**
     * getter for input
     * @return  the input 
     */
    public IAlgorithmInput getInput();
    
    /**
     * setter for output
     * @param output
     */
    public void setOutput(IAlgorithmOutput output);
    
    /**
     * getter for output
     * @return  the output
     */
    public IAlgorithmOutput getOutput();
    
    
}
