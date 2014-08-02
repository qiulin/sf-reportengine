/**
 * 
 */
package net.sf.reportengine.out;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import net.sf.reportengine.util.ReportIoUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * abstract parent class for all your byte based output.
 * </p>
 * <p>
 * What is a byte output? An output backed by a {@link java.io.OutputStream} to do the writing.<br/> 
 * Examples of byte outputs: an image report, a pdf, an excel output.
 * </p>
 * 
 * <p>
 * 	How to extend this class ? <br/>
 *  This class has basic functionality for handling the opening/closing of the outputStream.
 *  Your subclasses should implement the methods :
 *  <ol> 
 *  	<li>{@link ReportOutput#outputDataCell(CellProps)}</li>
 *  	<li>{@link ReportOutput#startDataRow(RowProps)} - for row counting please use super.startRow()</li>
 *  	<li>{@link ReportOutput#endDataRow()}</li>
 *  </ol>
 *  inside these methods you can always use the {@link #getOutputStream()} method for accessing
 *  the outputStream
 * </p>
 * 
 * @author dragos balan (dragos dot balan @ gmail dot com)
 * @since 0.7
 * @see {@link AbstractCharOutput} {@link ExcelOutput} {@link XslFoOutput}
 */
public abstract class AbstractByteOutput extends AbstractOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractByteOutput.class);
	
	/**
	 * the output stream behind this report output
	 */
	private OutputStream outputStream; 
	
	/**
	 * outputs into memory as {@code ByteArrayOutputStream}
	 */
	public AbstractByteOutput() {
		this(new ByteArrayOutputStream()); 
	}

	/**
	 * creates a new file and outputs the content into it
	 * 
	 * @param filePath	the output file path
	 */
	public AbstractByteOutput(String filePath) {
		this(filePath, false); 
	}
	
	/**
	 * Creates a new file ( or uses an existing one if append = true) and outputs the content into it
	 * 
	 * @param filePath	the output file path
	 * @param append 	true if you want to append the generated content to an existing file
	 */
	public AbstractByteOutput(String filePath, boolean append) {
		this(ReportIoUtils.createOutputStreamFromPath(filePath, append));
		LOGGER.info("output to file {} in append mode {}", filePath, append); 
	}

	/**
	 * byte output into the specified output stream
	 * This is the main constructor called by all other constructors. 
	 * 
	 * @param out	the output stream
	 */
	public AbstractByteOutput(OutputStream out) {
		this.outputStream = out; 
	}
	
	
	
    /**
     * flushes and closes the writer
     */
    public void close(){
    	try {
    		outputStream.flush(); 
			outputStream.close();
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
    	markAsClosed();
    }

	
	/**
	 * getter for the output stream
	 * @return the outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * setter for the output stream 
	 * @param outputStream the outputStream to set
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * sets the file path
	 * @param filePath
	 */
	public void setFilePath(String filePath){
		try {
			setOutputStream(new FileOutputStream(filePath));
		} catch (FileNotFoundException e) {
			throw new ReportOutputException(e); 
		}
	}
}
