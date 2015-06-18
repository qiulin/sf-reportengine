/**
 * Copyright (C) 2006 Dragos Balan (dragos.balan@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import net.sf.reportengine.util.ReportIoUtils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * abstract parent class for all your character based output.
 * </p>
 * 
 * <p>
 * What is a character based output? An output backed by a {@link java.io.Writer} to do the writing.<br/> 
 * Examples of character based outputs: text output, an html output, an xml also
 * </p>
 * 
 * <p>
 * 	How to extend this class ? <br/>
 *  This class has basic functionality for handling the opening/closing of the writer.
 *  Your subclasses should implement the methods :
 *  <ol> 
 *  	<li>{@link ReportOutput#outputDataCell(CellProps)}</li>
 *  	<li>{@link ReportOutput#startDataRow(RowProps)} - for row counting use super.startRow()</li>
 *  	<li>{@link ReportOutput#endDataRow()}</li>
 *  </ol>
 *  inside these methods you can always use the {@link #getOutputWriter()} method for accessing
 *  the writer
 * </p>
 * 
 * @author dragos balan (dragos dot balan @ gmail dot com)
 * @since 0.7
 * @see {@link AbstractByteOutput} {@link TextOutput} {@link HtmlOutput} {@link StaxReportOutput}
 */
public abstract class AbstractCharOutput extends AbstractOutput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(AbstractCharOutput.class);
	
	/**
	 * the writer behind this class
	 */
	private Writer outputWriter; 
	
	/**
	 * if this flag is true then this class is responsible for the lifecycle(open/close) of the outputWriter
	 */
	private boolean managedWriter = true; 
	
	/**
	 * char based output into a string writer (memory)
	 */
	public AbstractCharOutput() {
		this(new StringWriter());
	}

	/**
	 * char based output into the specified file path
	 * 
	 * @param filePath	the path of the output file
	 */
	public AbstractCharOutput(String filePath) {
		this(filePath, false); 
	}
	
	/**
	 * char based output into the specified file path using the UTF-8 encoding
	 * 
	 * @param filePath	the path of the output file
	 * @param append 	true if you want to append this content to an existing file
	 */
	public AbstractCharOutput(String filePath, boolean append){
		this(filePath, append, ReportIoUtils.UTF8_ENCODING);
	}
	
	
	/**
	 * char based output into the specified file path using the encoding provided
	 * 
	 * @param filePath	the path of the output file
	 * @param append 	true if you want to append this content to an existing file
	 * @param encoding	the encoding of the file
	 */
	public AbstractCharOutput(String filePath, boolean append, String encoding){
		this(ReportIoUtils.createWriterFromPath(filePath, append, encoding));
		LOGGER.info("output to file {} using encoding {}", filePath, encoding); 
	}
	
	/**
	 * Outputs the characters into the given writer. 
	 * This is the main constructor (all other constructors call this one)
	 * 
	 * @param writer			the output writer
	 */
	public AbstractCharOutput(Writer writer) {
		this(writer, true);
	}
	
	
	/**
	 * Outputs the characters into the given writer. 
	 * This is the main constructor (all other constructors call this one)
	 * 
	 * @param writer			the output writer
	 * @param managedWriter		if true then this class is responsible for the lifecycle(open/close) of the writer
	 */
	public AbstractCharOutput(Writer writer, boolean managedWriter) {
		this.outputWriter = writer;
		this.managedWriter = managedWriter; 
	}
	
	
    /**
     * flushes and closes the writer
     */
    public void close(){
    	try {
    		outputWriter.flush(); 
    		if(managedWriter){
    			outputWriter.close();
    		}
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
    	markAsClosed(); 
    }

	/**
	 * @return the outputWriter
	 */
	public Writer getOutputWriter() {
		return outputWriter;
	}

	/**
	 * @param outputWriter the outputWriter to set
	 */
	public void setOutputWriter(Writer outputWriter) {
		this.outputWriter = outputWriter;
	}
	
	/**
	 * 
	 * @param filePath
	 * @deprecated there's no control over encoding and other parameters. This method will be removed. 
	 */
	public void setFilePath(String filePath) {
		setOutputWriter(ReportIoUtils.createWriterFromPath(filePath, false, ReportIoUtils.UTF8_ENCODING));
	}
}
