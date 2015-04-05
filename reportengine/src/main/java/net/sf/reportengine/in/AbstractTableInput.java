/*
 * Created on 29.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Minimal implementation for some ReportInput methods.  
 * Use this class as starter for any other ReportInput implementation. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractTableInput implements TableInput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractTableInput.class);
	
    /**
     * default header for columns
     */
    public final static String DEFAULT_COLUMN_HEADER = "Column";
    
    /**
     * open flag
     */
    private boolean isOpen = false;
    
    
    /**
     * the columns metadata
     */
    private List<ColumnMetadata> columnMetadata = new ArrayList<ColumnMetadata>(); 
    
    /**
     * marks the input as open. 
     */
    public void open(){
    	if(isOpen){
            throw new IllegalStateException("You cannot open twice the same input. Close it and then reopen it !");
        }
    	LOGGER.debug("opening the input...");
        isOpen = true;
    }

    /**
     * marks the input as closed
     */
    public void close(){
    	if(!isOpen){
            throw new IllegalStateException("You cannot close an input which is not open !");
        }
        isOpen = false;
    	LOGGER.debug("report input closed succesfully");
    }
    
    /**
     * @return true if the report is open or false otherwise
     */
    public boolean isOpen(){
    	return isOpen; 
    }
    
    
    public List<ColumnMetadata> getColumnMetadata(){
    	return columnMetadata; 
    }
    
    protected void setColumnMetadata(List<ColumnMetadata> metadata){
    	this.columnMetadata = metadata; 
    }
}
