/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.Map;

import net.sf.reportengine.util.IOKeys;

/**
 * <p>
 * 	this is the base interface for all report algorithm
 * </p> 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public interface Algorithm {
    
    /**
     * sets the input of the report.
     * @param input	
     */     
    public void setIn(Map<IOKeys, Object> input);
    
    /**
     * 
     * @param input
     */
    public void addIn(IOKeys key, Object value); 
    
    /**
     * executes the report and displays it
     */
    public void execute();
    
    /**
     * sets the output of the report
     * @param out
     */
    public Map<IOKeys, Object> getResultMap();
    
    /**
     * 
     * @param name
     * @return
     */
    public Object getResult(IOKeys key); 
    
}
