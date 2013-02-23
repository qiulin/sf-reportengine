/*
 * Created on 12.02.2005
 * Author : dragos balan
 */
package net.sf.reportengine.in;



/**
 * <p>This is the top interface for report input.</p> 
 * 
 * @author dragos balan (dragos dot balan at gmail.com)
 * @since 0.2
 */
public interface IReportInput {
	
	/**
     * prepares the input for reading. 
     * With this method you can open streams, database connections etc.
     */
    public void open();
    
    /**
     * use this method to release all resources used during the reading 
     * of the input lines
     */
    public void close();
	
    /**
     * <p>retrieves the next row of data </p>
     * This method should always return the same number of objects otherwise 
     * the framework will throw an IllegaArgumentException
     * @return an array of data objects
     */
    public Object[] nextRow();
    
    /**
     * returns true if there are rows left to read otherwise false
     * @return  true if the input has more rows to return
     */
    public boolean hasMoreRows();
    
    /**
     * tells whether this input is able to provide medata or not
     * 
     * @return	true if this imput is able to provide metadata
     */
    public boolean suppportsColumnMetadata();
    
    /**
     * <p>getter for columns metadata</p>
     *  
     * @return the column metadata if this input supports metadata otherwise an empty array
     */
    public ColumnMetadata[] getColumnMetadata(); 
}
