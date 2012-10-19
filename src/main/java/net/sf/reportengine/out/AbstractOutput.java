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
    
    
   /**
     * the output writer
     */
    private Writer outputWriter;
    
    /**
     * tells whether or not to replace null values with white spaces
     */
    private String nullsReplacement ;
    
    
    private int rowCount = 0;
    
    /**
     * 
     * @param fileName
     */
    public AbstractOutput(String fileName){
    	try {
			setWriter(new FileWriter(fileName));
		} catch (IOException e) {
			throw new ReportInputException(e); 
		} 
    }
    
    /**
     * 
     * @param out
     */
    public AbstractOutput(OutputStream out){
    	this(out, System.getProperty("file.encoding")); 
    }
    
    /**
     * constructs a report output in the specified output stream 
     * 
     * @param out the stream used for output
     */
    public AbstractOutput(OutputStream out, String encoding) {
        try {
			setWriter(new OutputStreamWriter(out, encoding));
		} catch (UnsupportedEncodingException e) {
			throw new ReportInputException(e);
		}
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
    public void open(){}
    
    
    /**
     * empty implementation
     */
    public void close(){}
    
    
    /**
     * empty implementation 
     */ 
    public void startRow(){
        rowCount++;
    }
    
    
    /**
     * empty implementation
     */
    public void endRow(){}
    
    
    /**
     * outputs data
     * @param algorithmProps  the properties imposed by the algorithm  
     */
    public abstract void output(CellProps algorithmProps);
    
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
}
