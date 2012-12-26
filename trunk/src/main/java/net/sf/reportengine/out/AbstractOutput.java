/*
 * Created on 29.01.2005
 */
package net.sf.reportengine.out;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

import net.sf.reportengine.in.ReportInputException;

/**
 * An abstract implementation for IReportOutput. It is recommended
 * that you extend this class rather then implement IReportOutput 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public abstract class AbstractOutput implements IReportOutput {
    
	/**
	 * error message displayed when open() was not used
	 */
	public final static String OUTPUT_NOT_OPEN = "Output not ready! Please call open() method.";
	/**
	 * system file encoding 
	 */
	public final static String SYSTEM_FILE_ENCODING = System.getProperty("file.encoding"); 
	
	/**
	 * utf-8 encoding 
	 */
	public final static String UTF8_ENCODING =  "UTF-8"; 
	
	/**
	 * the default system line separator
	 */
	public final static String LINE_SEPARATOR = System.getProperty("line.separator");
	
    /**
     * tells whether or not to replace null values with white spaces
     */
    private String nullsReplacement ;
    
    /**
     * row count
     */
    private int rowCount = 0;
    
    /**
     * isOpen flag 
     */
    private boolean isOpen = false; 
    
    /**
     * the properties of the current row
     */
    private RowProps currentRowProps; 
    
    /**
     * constructor
     */
    public AbstractOutput(){
    	
    }
    
    
    /**
     * marks this output as open
     */
    public void open(){
    	isOpen = true; 
    }
    
    
    /**
     * empty implementation
     */
    public void close(){
		this.currentRowProps = null; 
    	isOpen = false;
    }
    
    
    /**
     *  makes the necessary checks to start the row
     */ 
    public void startRow(RowProps rowProperties){
    	if(!isOpen) throw new ReportOutputException(OUTPUT_NOT_OPEN); 
    	this.currentRowProps = rowProperties; 
        rowCount++;
    }
    
    
    /**
     * empty implementation
     */
    public void endRow(){}
    
    /**
     * checks null & other impossible to print values
     */
    protected String purifyData(Object val) {
    	String result = val != null ? val.toString() : nullsReplacement;
        return result;
    }
    
    /**
     * getter for nulls replacement
     * @return  the replacement for null values
     */
    public String getNullsReplacement() {
        return nullsReplacement;
    }

    
    /**
     * setter for nulls replacement
     * @param nullsReplacement
     */
    public void setNullsReplacement(String nullsReplacement) {
        this.nullsReplacement = nullsReplacement;
    }

	/**
	 * @return the rowCount
	 */
	public int getRowCount() {
		return rowCount;
	}

	/**
	 * use it to find if the output was open
	 * 
	 * @return	true if the report was open or false otherwise
	 */
	protected boolean isOutputOpen(){
		return isOpen; 
	}
	
	/**
	 * marks this output as being open
	 */
	protected void markAsOpen(){
		isOpen = true; 
	}
	
	/**
	 * marks this output as closed
	 */
	protected void markAsClosed(){
		isOpen = false; 
	}
	
	/**
	 * @return the currentRowProps
	 */
	protected RowProps getCurrentRowProps() {
		return currentRowProps;
	}
}
