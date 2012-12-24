/*
 * Created on 29.01.2005
 */
package net.sf.reportengine.out;

import java.io.FileWriter;
import java.io.IOException;
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
    
	public final static String NO_WRITER_SET_ERROR_MESSAGE = "Output not ready ! Please set a writer or a filename to your output";
	
	/**
	 * the default system file encoding 
	 */
	public final static String SYSTEM_FILE_ENCODING = System.getProperty("file.encoding"); 
    
   /**
     * the output writer
     */
    private Writer outputWriter;
    
    /**
     * the filename (used only if the report output is a file)
     */
    private String fileName;
    
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
     * empty output. 
     * If you use this constructor you will have to set the 
     * other properties by calling the setter methods 
     * ({@link #setFileName(String)}
     * {@link #setWriter(Writer)}
     * {@link #setOutputStream(OutputStream)}
     * {@link #setOutputStream(OutputStream, String)}
     */
    public AbstractOutput(){}
    
    /**
     * 
     * @param fileName
     */
    public AbstractOutput(String fileName){
    	setFileName(fileName); 
    }
    
    /**
     * 
     * @param out
     */
    public AbstractOutput(OutputStream out){
    	setOutputStream(out);
    }
    
    /**
     * constructs a report output in the specified output stream 
     * 
     * @param out the stream used for output
     */
    public AbstractOutput(OutputStream out, String encoding) {
        setOutputStream(out, encoding); 
    }
    
    /**
     * 
     * @param writer
     */
    public AbstractOutput(Writer writer){
    	setWriter(writer);
    }
    
    
    /**
     * empty implementation
     */
    public void open(){
    	if(outputWriter == null){
    		//check if the filename is specified
    		if(fileName != null){
    			try {
    				setWriter(new FileWriter(fileName));
    			} catch (IOException e) {
    				throw new ReportOutputException(e); 
    			} 
    		}else{
    			//filename is also null
    			throw new ReportOutputException(NO_WRITER_SET_ERROR_MESSAGE); 
    		} 
    	}
    	isOpen = true; 
    }
    
    
    /**
     * empty implementation
     */
    public void close(){
    	try {
			outputWriter.close();
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
    	isOpen = false;
    }
    
    
    /**
     *  
     */ 
    public void startRow(RowProps rowProperties){
        rowCount++;
    }
    
    
    /**
     * empty implementation
     */
    public void endRow(){}
    
    
    /**
     * 
     * @return
     */
    public Writer getWriter() {
        return outputWriter;
    }
    
    /**
     * registers the writer 
     * @param outStream
     */
    public void setWriter(Writer writer){
        this.outputWriter = writer;
    }
    
    /**
     * 
     * @param outStream
     * @param encoding
     */
    public void setOutputStream(OutputStream outStream, String encoding){
    	try {
			setWriter(new OutputStreamWriter(outStream, encoding));
		} catch (UnsupportedEncodingException e) {
			throw new ReportInputException(e);
		}
    }
    
    /**
     * 
     * @param outStream
     */
    public void setOutputStream(OutputStream outStream){
    	setOutputStream(outStream, SYSTEM_FILE_ENCODING);
    }
    
    
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

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String filename) {
		this.fileName = filename;
	}
	
	/**
	 * use it to find if the output was open
	 * 
	 * @return	true if the report was open or false otherwise
	 */
	protected boolean isOutputOpen(){
		return isOpen; 
	}
}
