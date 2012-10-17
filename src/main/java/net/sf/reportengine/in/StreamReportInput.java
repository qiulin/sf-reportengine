/*
 * Created on 12.02.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

/**
 * <p>
 * Report input implementation for streams (files, urls, etc) backed by a java.io.Reader<br/> 
 * By default this implementation expects a comma (,) as data separator
 * but you can use the #setSeparator(String) method to specify whatever 
 * separator you want 
 * </p>
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class StreamReportInput extends AbstractReportInput{
    
	/**
	 * the logger
	 */
	private static final Logger logger = Logger.getLogger(StreamReportInput.class);
    
    /**
     * the default separator
     */
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
    private int columnsCount = Integer.MIN_VALUE;
    
    /**
     * this field represents the number of lines that should be skipped 
     * from the beginning of the stream
     */
    private int skipFirstXLines = 0; 
    

	/**
     * the raw data (unparsed and un-split in columns)
     */
    private String nextRawDataRow;
	
    /**
     * creates a report input for the given file with comma as separator in the default encoding
     * 
     * @param fileName      the path name of the file containing data
     * @throws FileNotFoundException
     */
    public StreamReportInput(String fileName){
		this(fileName, DEFAULT_SEPARATOR);
	}
	
    /**
     * Creates a report input from the given fileName (in the default encoding) using the separator
     * 
     * @param fileName	path and filename
     * @param separator	data-separator
     */
    public StreamReportInput(String fileName, String separator){
    	this(fileName, separator, System.getProperty("file.encoding"));
    }
    
    /**
     * Creates an input for the given fileName using the provided separator.
     * When reading the specified encoding is used.
     * 
     * @param fileName      the path of the file containing data
     * @param separator     the separator used to identify data/column
     * @param encoding 		the encoding used when reading the file
     * 
     * @throws FileNotFoundException 
     */
	public StreamReportInput(String fileName, String separator, String encoding){
		try{
			setInputReader(new InputStreamReader(new FileInputStream(fileName), encoding));
			setSeparator(separator);
		}catch(FileNotFoundException fnf){
			throw new ReportInputException(fnf);
		}catch(UnsupportedEncodingException uee){
			throw new ReportInputException(uee);
		}
	}
	
	
	/**
	 * creates a report input for the given InputStream using the specified encoding and data-separator
	 * 
	 * @param is			the input stream
	 * @param separator		data separator
	 * @param encoding		the encoding 
	 * @throws UnsupportedEncodingException
	 */
	public StreamReportInput(InputStream is, String separator, String encoding){
		try{
			setInputReader(new InputStreamReader(is, encoding));
			setSeparator(separator); 
		}catch(UnsupportedEncodingException uee){
			throw new ReportInputException(uee); 
		}
	}
	
	/**
	 * creates a report input for the given InputStream using the default system encoding and the
	 * specified data-separator
	 * 
	 * @param is			the input stream
	 * @param separator		data-separator
	 * @throws UnsupportedEncodingException
	 */
	public StreamReportInput(InputStream is, String separator){
		this(is, separator, System.getProperty("file.encoding"));
	}
	
	/**
	 * creates a report input for the given input stream using the default system encoding and comma 
	 * as data-separator
	 * 
	 * @param is	the input stream
	 * @throws UnsupportedEncodingException
	 */
	public StreamReportInput(InputStream is){
		this(is, DEFAULT_SEPARATOR);
	}
	
	/**
	 * creates a report-input based on the provided reader using comma as data-separator
	 * 
	 * @param inReader	the reader
	 */
	public StreamReportInput(Reader inReader){
		this(inReader, DEFAULT_SEPARATOR);
	}
	
	/**
	 * creates a report-input based on the provided reader and using separator to distinguish between
	 * data/columns
	 * 
	 * @param inReader		the reader
	 * @param separator		the separator used to identify data/columns
	 */
	public StreamReportInput(Reader inReader, String separator){
		setInputReader(inReader); 
		setSeparator(separator);
	}
	
	/**
     * prepares the reader for further usaga. Actually this implementation
     * already reads the first line in order to be prepared for calls to 
     * #hasMoreRows() and #nextRow()
     */
    public void open() {
    	super.open();
        try{
        	reader = new BufferedReader(inputReader);
        	
        	//we need to read at least one row even if the 
        	// skipFirstXLines is zero because after open() 
        	//we have to be able to respond to hasMoreRows() 
        	int skipped = 0;
        	while(skipped < skipFirstXLines){
        		reader.readLine();
        		skipped++;
        	};
        	nextRawDataRow = reader.readLine();
        	
            if(nextRawDataRow != null){
                columnsCount = new StringTokenizer(nextRawDataRow, separator).countTokens();
            }
        }catch(IOException ioExc){
            throw new ReportInputException("IO Error occurred when opening the StreamReportInput", ioExc);
        }
    }
    
    
    /**
     * closes the input and releases all resources
     */
    public void close() {
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
    * You should combine this method with #hasMoreRows()
    * or you can iterate through the rows until null is returned<br/>
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
                nextRawDataRow = reader.readLine();
                result = tempDataRow.toArray(new Object[tempDataRow.size()]);
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
     * registers the data-separator which is used to differentiate among colums-data
     * @param separator     the data/column separator 
     */    
    public void setSeparator(String separator) {
        this.separator = separator;
    }
    
    /**
     * registers the provided input reader
     * @param reader
     */
    public void setInputReader(Reader reader){
    	this.inputReader = reader;
    }
    
    /**
     * returns the input reader
     * @return	a reader
     */
    public Reader getInputReader(){
    	return inputReader; 
    }
    
    /**
     * retrieves the number of lines to be skipped at the begining of the stream
     * @return
     */
    public int getSkipFirstLines() {
		return skipFirstXLines;
	}

    /**
     * tells this input to skip the first specified lines. 
     * This is useful when your input already has some column headers
     * 
     * @param skipFirstXLines
     */
	public void setSkipFirstLines(int skipFirstLines) {
		this.skipFirstXLines = skipFirstLines;
	}
}
