/*
 * Created on 14.12.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.core.algorithm;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * @author dragos balan
 * @version $Revision$
 * $log$
 */
public class NewRowEvent  implements Serializable{
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -1450602765872099843L;
	
	/**
	 * 
	 */
	private List<Object> inputData;
    
	/**
	 * 
	 * @param inputDataRow
	 * @deprecated 
	 */
	public NewRowEvent(Object[] inputDataRow){
		this(Arrays.asList(inputDataRow)); 
	}
	
	/**
	 * 
	 * @param inputDataRow
	 */
    public NewRowEvent(List<Object> inputDataRow){
        this.inputData = inputDataRow; 
    }
    
    /**
     * 
     * @return
     */
    public List<Object> getInputDataRow(){
        return inputData;
    }
    
    /**
     * 
     */
    public String toString(){
        return new StringBuilder("NewRowEvent:").append(inputData).toString();
    }
    
    /**
     * 
     */
    public boolean equals(Object another){
    	boolean result = false; 
    	if(another instanceof NewRowEvent){
    		NewRowEvent anotherAsNRE = (NewRowEvent)another; 
    		result = inputData.equals(anotherAsNRE.getInputDataRow());
    	}
    	return result; 
    }
}
