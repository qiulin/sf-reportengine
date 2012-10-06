/*
 * Created on 12.02.2005
 * Author : dragos balan
 */
package net.sf.reportengine.core.algorithm;


/**
 * input for any algorithm 
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 */
public interface IAlgorithmInput {
    
    
    /**
     * opens the input for reading 
     */
    public void open();
    
    /**
     * closes the input and releases all resources used
     */
    public void close();
	
    /**
     * retrieves the next row of data 
     * @return an array of data objects
     */
    public Object[] nextRow();
    
    /**
     * true if there are any rows left otherwise false
     * @return  true if the input has more rows to return
     */
    public boolean hasMoreRows();
    
}
