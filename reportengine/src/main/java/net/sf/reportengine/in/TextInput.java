/*
 * Created on 12.02.2005
 * Author : dragos balan 
 */
package net.sf.reportengine.in;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import net.sf.reportengine.util.ReportIoUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * Report input implementation for streams (files, urls, etc) backed by a java.io.BufferedReader<br/> 
 * By default this implementation expects a comma (,) as data separator
 * but you can use the #setSeparator(String) method to specify whatever 
 * separator you want 
 * </p>
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class TextInput extends AbstractReportInput{
    
	/**
	 * the message for cases when no writer is set
	 */
	protected static final String NO_WRITER_SET_ERROR_MESSAGE = "The input cannot be opened. Please set a writer or a filePath to your input.";

	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(TextInput.class);
	
    /**
     * the default separator
     */
    private static final String DEFAULT_SEPARATOR = ",";
	
	/**
     * the reader of the file 
     */
	private BufferedReader reader = null;
    
    /**
     * the separator (default value is <code>comma separator</code>
     */
	private String separator = DEFAULT_SEPARATOR;
    
    /**
     * whether or not the first line should be interpreted as header
     */
    private boolean firstLineIsHeader = false; 
    
	/**
     * the raw data (unparsed and un-split in columns)
     */
    private String nextRawDataRow;
	
    /**
     * empty text imput constructor. 
     * Please use one of the {@link #setInputReader(Reader)}
     */
    public TextInput(){
    	
    }
    
    /**
     * creates a report input for the given file with comma as separator in the default encoding
     * 
     * @param filePath      the path name of the file containing data
     * @throws FileNotFoundException
     */
    public TextInput(String filePath){
		this(filePath, DEFAULT_SEPARATOR);
	}
	
    /**
     * Creates a report input from the given fileName (in the default encoding) using the separator
     * 
     * @param filePath	path and filename
     * @param separator	data-separator
     */
    public TextInput(String filePath, String separator){
    	this(filePath, separator, ReportIoUtils.UTF8_ENCODING);
    }
    
    /**
     * Creates an input for the given fileName using the provided separator.
     * When reading the specified encoding is used.
     * 
     * @param filePath		the path of the file containing data
     * @param separator     the separator used to identify data/column
     * @param encoding 		the encoding used when reading the file
     * 
     * @throws FileNotFoundException 
     */
	public TextInput(String filePath, String separator, String encoding){
		this(filePath, separator, encoding, false); 
	}
	
	
	/**
     * Creates an input for the given fileName using the provided separator.
     * When reading the specified encoding is used.
     * 
     * @param filePath		the path of the file containing data
     * @param separator     the separator used to identify data/column
     * @param encoding 		the encoding used when reading the file
     * @param firstLineIsHeader specifies whether the first line contains the column headers or not
     * 
     */
	public TextInput(String filePath, String separator, String encoding, boolean firstLineIsHeader){
		setInputReader(ReportIoUtils.createReaderFromPath(filePath, encoding));
		setSeparator(separator);
		setFirstLineHeader(firstLineIsHeader); 
	}
	
	/**
	 * creates a report input for the given input stream using the utf-8 encoding 
	 * and comma as data-separator
	 * 
	 * @param is	the input stream
	 */
	public TextInput(InputStream is){
		this(is, DEFAULT_SEPARATOR);
	}
	
	/**
	 * creates a report input for the given InputStream using the utf-8 encoding 
	 * and the specified data-separator
	 * 
	 * @param is			the input stream
	 * @param separator		data-separator
	 * @throws UnsupportedEncodingException
	 */
	public TextInput(InputStream is, String separator){
		this(is, separator, ReportIoUtils.UTF8_ENCODING);
	}
	
	/**
	 * creates a report input for the given InputStream using the specified encoding and data-separator
	 * 
	 * @param is			the input stream
	 * @param separator		data separator
	 * @param encoding		the encoding 
	 * @throws UnsupportedEncodingException
	 */
	public TextInput(InputStream is, String separator, String encoding){
		this(is, separator, encoding, false); 
	}
	
	
	/**
	 * creates a report input for the given InputStream using the specified encoding and data-separator
	 * 
	 * @param is			the input stream
	 * @param separator		data separator
	 * @param encoding		the encoding 
	 * @param encoding 		this flag specifies whether or not the first line contains the column headers
	 * @throws UnsupportedEncodingException
	 */
	public TextInput(InputStream is, String separator, String encoding, boolean firstLineIsHeader){
		try{
			setInputReader(new InputStreamReader(is, encoding));
			setSeparator(separator);
			setFirstLineHeader(firstLineIsHeader); 
		}catch(UnsupportedEncodingException uee){
			throw new ReportInputException(uee); 
		}
	}
	
	/**
	 * creates a report-input based on the provided reader using comma as data-separator
	 * 
	 * @param inReader	the reader
	 */
	public TextInput(Reader inReader){
		this(inReader, DEFAULT_SEPARATOR);
	}
	
	
	/**
	 * creates a report-input based on the provided reader and using separator to distinguish between
	 * data/columns
	 * 
	 * @param inReader		the reader
	 * @param separator		the separator used to identify data/columns
	 */
	public TextInput(Reader reader, String separator){
		this(reader, separator, false); 
	}
	
	/**
	 * creates a report-input based on the provided reader and using separator to distinguish between
	 * data/columns
	 * 
	 * @param inReader		the reader
	 * @param separator		the separator used to identify data/columns
	 * @param firstLineIsHeaderFlag	this flag specifies if the first line contains the column headers
	 */
	public TextInput(Reader inReader, String separator, boolean firstLineIsHeaderFlag){
		setInputReader(inReader); 
		setSeparator(separator);
		setFirstLineHeader(firstLineIsHeaderFlag);
	}
	
	/**
     * prepares the reader for further usaga. Actually this implementation
     * already reads the first line in order to be prepared for calls to 
     * #hasMoreRows() and #nextRow()
     */
    public void open() {
    	super.open();
        try{
        	if(reader == null) throw new ReportInputException(NO_WRITER_SET_ERROR_MESSAGE); 
        	
        	LOGGER.debug("first line is header ", firstLineIsHeader);
        	if(isFirstLineHeader()){
        		String rawHeader = reader.readLine();
        		if(rawHeader != null){
        			String[] headerArr = transformRawDataRowIntoArray(rawHeader, separator);
        			setColumnMetadata(extractMetadataFromHeaders(headerArr)); 
        		}else{
        			LOGGER.warn("Couldn't find the header while opening the input"); 
        		}
        	}
        	
        	//we need to read at least one row even if the 
        	// linesToBeSkipped is zero because after open() 
        	//we have to be able to respond to hasMoreRows() 
        	
        	//read and keep the first real-row
        	nextRawDataRow = reader.readLine();
        	
            if(nextRawDataRow != null && !isFirstLineHeader()){
            	//if the metadata was not previously read then we construct an almost empty 
            	//metadata array
                int columnsCount = new StringTokenizer(nextRawDataRow, separator).countTokens();
                setColumnMetadata(createEmptyMetadata(columnsCount));
            }else{
            	if(nextRawDataRow == null){
            		LOGGER.warn("While opening the input we found no rows"); 
            	}
            }
        }catch(IOException ioExc){
            throw new ReportInputException("IO Error occurred when opening the TextInput", ioExc);
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
            throw new ReportInputException("An IO Error occured when closing the input reader !", exc);
        }
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
    public Object[] nextRow(){
    	Object[] result = null;
        try {
            //if read not performed  && read next row of data
            if(hasMoreRows()){
            	result = transformRawDataRowIntoArray(nextRawDataRow, separator);
                
                //now we read the next raw row
                nextRawDataRow = reader.readLine();
            }
        } catch (IOException e) {
            throw new ReportInputException("IO Error occured while reading data !",e);
        }
        
        return result; 
    }
    
    /**
     * transforms a raw data row into an array of objects
     * 
     * @param rawLine
     * @param separator
     * @return
     */
    private String[] transformRawDataRowIntoArray(String rawLine, String separator){
    	ArrayList<String> tempDataRow = new ArrayList<String>();
        StringTokenizer strTokenizer = new StringTokenizer(rawLine, separator);
        
        while(strTokenizer.hasMoreTokens()){
            tempDataRow.add(strTokenizer.nextToken());
        };
        
        return tempDataRow.toArray(new String[tempDataRow.size()]);
    }
    
    
    private List<ColumnMetadata> extractMetadataFromHeaders(String[] headerArr){
    	List<ColumnMetadata> result = new ArrayList<ColumnMetadata>(headerArr.length); 
		for (String header : headerArr) {
			result.add(new ColumnMetadata(header, header)); 
		}
		return result;
    }
    
    private List<ColumnMetadata> createEmptyMetadata(int columnCount){
    	List<ColumnMetadata> result = new ArrayList<ColumnMetadata>(columnCount); 
		for (int i=0; i<columnCount; i++) {
			ColumnMetadata colMetadata = new ColumnMetadata(""+i); 
			result.add(colMetadata); 
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
    	this.reader = new BufferedReader(reader);
    }
    
    /**
     * returns the input reader
     * @return	a reader
     */
    public Reader getInputReader(){
    	return reader; 
    }

	/**
	 * 
	 * @param flag
	 */
	public void setFirstLineHeader(boolean flag){
		this.firstLineIsHeader = flag; 
	}
	
	public boolean isFirstLineHeader(){
		return firstLineIsHeader; 
	}
	
	/**
	 * @param filePath the filePath to set
	 */
	public void setFilePath(String filePath) {
		setInputReader(ReportIoUtils.createReaderFromPath(filePath));
	}
}
