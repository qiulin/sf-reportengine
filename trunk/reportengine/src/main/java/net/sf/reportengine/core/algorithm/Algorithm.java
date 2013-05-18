/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.List;
import java.util.Map;

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
    public void setIn(List<AlgoInput> input);
    
    /**
     * 
     * @param input
     */
    public void addIn(AlgoInput input); 
    
    /**
     * executes the report and displays it
     */
    public void execute();
    
    /**
     * sets the output of the report
     * @param out
     */
    public Map<String, Object> getResultMap();
    
    
    /**
     * 
     * @param name
     * @return
     */
    public Object getResult(String name); 
    
}
