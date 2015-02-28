/*
 * Created on 27.08.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.util.EnumMap;
import java.util.Map;

import net.sf.reportengine.util.IOKeys;

/**
 * <p>
 * 	this is the base interface for all report algorithm
 * </p> 
 * @author dragos balan (dragos dot balan at gmail dot com)
 */
public interface Algorithm {
	
	
	public final Map<IOKeys, Object> EMPTY_READ_ONLY_PARAMS_MAP = new EnumMap<IOKeys, Object>(IOKeys.class); //TODO make it read only
   
    /**
     * executes the algorithm based on the input and returns the output
     * 
     * @param input 	 the input parameter map
     * @return an output parameter map
     */
    public Map<IOKeys, Object> execute(Map<IOKeys, Object> input);
    
}
