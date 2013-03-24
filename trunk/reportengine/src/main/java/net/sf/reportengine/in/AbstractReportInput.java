/*
 * Created on 29.10.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.util.List;

import org.apache.log4j.Logger;


/**
 * Minimal implementation for some ReportInput methods.  
 * Use this class as starter for any other ReportInput implementation. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractReportInput implements ReportInput {
	
	/**
	 * the logger
	 */
	private static final Logger logger = Logger.getLogger(AbstractReportInput.class);
	
    /**
     * default header for columns
     */
    public final static String DEFAULT_COLUMN_HEADER = "Column";
    
    /**
     * open flag
     */
    private boolean isOpen = false;

    /**
     * marks the input as open. 
     */
    public void open(){
    	if(isOpen){
            throw new IllegalStateException("You cannot open twice the same input. Close it and then reopen it !");
        }
    	
    	if(logger.isDebugEnabled()){
    		logger.debug("opening the report input");
    	}
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
        if(logger.isDebugEnabled()){
    		logger.debug("stream report input closed succesfully");
    	}
    }
    
    /**
     * @return true if the report is open or false otherwise
     */
    public boolean isOpen(){
    	return isOpen; 
    }
    
    /**
     * This is the default implementation which returns false. 
     * By returning false we signal the non-existence of meta data in this report. 
     * Make sure you overwrite this method if your input contains meta data
     */
    public boolean supportsMetadata(){
    	return false;
    }
    
    
    /**
     * this default implementation returns an empty array.
     * Don't hesitate to overwrite this method and return a non-empty array if your input
     * has metadata 
     */
    public List<ColumnMetadata> getColumnMetadata(){
    	throw new IllegalStateException("A call to getColumnMetadata() method cannot be made if your input doesn't contain meta data"); 
    }
}
