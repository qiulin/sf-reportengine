/*
 * Created on 29.01.2005
 */
package net.sf.reportengine.out;

import java.io.OutputStream;

import net.sf.reportengine.core.ReportConstants;
import net.sf.reportengine.util.Locator;

/**
 * An abstract implementation for IEngineOut. It is strongly recommended
 * that you extend this class rather then implement IReportOutput at least
 * until one stable release
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 */
public abstract class AbstractOutput implements IReportOutput {
    
    
   /**
     * the output stream (required by the IEngineOut)
     */
    private OutputStream outputStream;
    
    /**
     * tells whether or not to replace null values with white spaces
     */
    private String nullsReplacement ;
    
    
    private int rowCount = 0;
    /**
     * constructs a report output.
     * <b>You should never use this kind of constructor whithout using setOutputStream();</b> 
     */
    public AbstractOutput(){
        //empty
    }
    
    
    /**
     * constructs a report output in the specified output stream 
     * 
     * @param out the stream used for output
     */
    public AbstractOutput(OutputStream out) {
        setOutputStream(out);
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
     * empty impl
     */
    public void endRow(){}
    
    
    public void startHeader(){}
    
    public void endHeader(){}
    
    public void startFooter(){}
    
    public void endFooter(){}
    
    /**
     * outputs data
     * @param algorithmProps  the properties imposed by the algorithm  
     */
    public abstract void output(CellProps algorithmProps);
    
    /*
     *  (non-Javadoc)
     * @see net.sf.reportengine.out.IReportOutput#getOutputStream()
     */
    public OutputStream getOutputStream() {
        return outputStream;
    }
    
    /**
     * output stream setter
     * @param outStream
     */
    public void setOutputStream(OutputStream outStream){
        this.outputStream = outStream;
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
