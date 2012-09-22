/*
 * Created on 12.02.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * <p>IReportInput implementation for a stream report inputs<br/> 
 * An important feature of this IReportInput implementations it's the <b>separator</b>
 * which is used for columns separation. By default the separator is comma (",")
 * but you can use the setSeparator(String) method to specify whatever separators you want 
 * </p>
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class StreamReportInput extends AbstractReportInput{
    
	/**
	 * the logger
	 */
	private static final Logger logger = Logger.getLogger(StreamReportInput.class);
	
    /**
     * the initial value for columnCount
     * This value is used to determine if the columnsCount of the input has been evaluated
     */
    private static final int COLUMN_COUNT_INITIAL_VALUE = Integer.MIN_VALUE;
    
    private static final String DEFAULT_SEPARATOR = ",";
	
    /**
     * the input stream 
     */
    private Reader inputReader;
    
	/**
     * the reader of the file 
     */
	private BufferedReader reader = null;
    
    /**
     * the separator (default value is <code>comma separator</code>
     */
	private String separator = null;
    
	/**
     * the columns count
     */
    private int columnsCount = COLUMN_COUNT_INITIAL_VALUE;
    
    /**
     * the raw data ( unparsed and un-split in columns)
     */
    private String nextRawDataRow;
	
    /**
     * constructs having comma as separator 
     * @param fileName      the path name of the file containing data
     * @throws FileNotFoundException
     */
    public StreamReportInput(String fileName) throws FileNotFoundException{
		this(fileName, DEFAULT_SEPARATOR);
	}
	
    /**
     * constructor 
     * 
     * @param fileName      the path and name of the file containing data
     * @param separator     the separator used to identify each column
     * @throws FileNotFoundException 
     */
	public StreamReportInput(String fileName, String separator) throws FileNotFoundException{
	    this(new FileReader(fileName), separator);
	}
	
	
	/**
	 * 
	 * @param is
	 * @param separator
	 */
	public StreamReportInput(InputStream is, String separator){
		this(new InputStreamReader(is), separator);
	}
	
	/**
	 * 
	 * @param is
	 */
	public StreamReportInput(InputStream is){
		this(is, DEFAULT_SEPARATOR);
	}
	
	/**
	 * 
	 * @param inReader
	 */
	public StreamReportInput(Reader inReader){
		this(inReader, DEFAULT_SEPARATOR);
	}
	
	/**
	 * 
	 * @param is			the input stream 
	 * @param separator		the separator used to identify each column
	 */
	public StreamReportInput(Reader inReader, String separator){
		this.inputReader = inReader;
		this.separator = separator;
	}
	
	/**
	 * 
	 * @param inFile
	 * @param separator
	 */
	public StreamReportInput(File inFile, String separator) throws FileNotFoundException{
		this(new FileReader(inFile), separator);
	}
    
    /**
     * 
     */
    public void open() throws ReportInputException {
    	if(logger.isDebugEnabled()){
    		logger.debug("opening the stream report input");
    	}
        super.open();
        try{
        	reader = new BufferedReader(inputReader);
 
            nextRawDataRow = readNextLine();
            
            if(nextRawDataRow != null){
                columnsCount = new StringTokenizer(nextRawDataRow, separator).countTokens();
            }
            
        }catch(IOException ioExc){
            throw new ReportInputException("An IO Error occurred !", ioExc);
        }
    }
    
    
    /**
     * closes the input and releases all resources
     */
    public void close() throws ReportInputException {
        super.close();
        try{
            if(reader != null){
                reader.close();
                reader = null;
            }
        }catch(IOException exc){
            throw new ReportInputException(" An IO Error occured when closing the input reader !", exc);
        }
    }
	
	
	/**
	 * returns the column count
	 */
	public int getColumnsCount()  {
	    return columnsCount;
	}
	
   
   /**
    * returns the next row of data if any row available otherwise returns null<br>
    * You should combine the usage of this method with <source>hasMoreRows()</source>
    * or you can iterator through the rows until null is returned<br/>
    * Example:
    * <pre>
    * while(input.hasMoreRows()){             
    *   Object[] dataRow = input.nextRow(); 
    *   //do something with data            
    * } 
    * </pre> 
    */
    public Object[] nextRow() throws ReportInputException {
    	Object[] result = null;
        try {
            //if read not performed  && read next row of data
            if(hasMoreRows()){
            	ArrayList<Object> tempDataRow = new ArrayList<Object>();
                StringTokenizer strTokenizer = new StringTokenizer(nextRawDataRow, separator);
                
                while(strTokenizer.hasMoreTokens()){
                    tempDataRow.add(strTokenizer.nextToken());
                };
       
                assert tempDataRow.size() == columnsCount : 
                       " Normally each row should have the same length !";
                
                
                //now we read the next raw row
                nextRawDataRow = readNextLine();
                result = tempDataRow.toArray(new Object[]{});
            }
        } catch (IOException e) {
            throw new ReportInputException("An IO Error occured !",e);
        }
        
        return result; 
    }
    
    
    /**
     * returns true if there are more rows to read otherwise false
     */
    public boolean hasMoreRows(){
        return nextRawDataRow != null;
    }

	/**
     * getter for separator
     * @return the 
     */
    public String getSeparator() {
        return separator;
    }

    /**
     * the separator is used for columns identification
     * @param separator     the separator to be used 
     */    
    public void setSeparator(String separator) {
        this.separator = separator;
    }
    
    /**
     * reads the next line 
     * @return
     */
    private String readNextLine() throws IOException{
        return reader.readLine();
    }
}
