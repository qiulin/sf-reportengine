/**
 * 
 */
package net.sf.reportengine.out;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractCharacterOutput extends AbstractOutput {
	
	/**
	 * the writer behind this class
	 */
	private Writer outputWriter; 
	
	/**
	 * 
	 */
	public AbstractCharacterOutput() {
		this(new StringWriter());
	}

	/**
	 * @param filePath
	 */
	public AbstractCharacterOutput(String filePath) {
		setFilePath(filePath); 
	}

	/**
	 * @param writer
	 */
	public AbstractCharacterOutput(Writer writer) {
		setOutputWriter(writer); 
	}
	
	
    /**
     * flushes and closes the writer
     */
    public void close(){
    	try {
    		outputWriter.flush(); 
			outputWriter.close();
		} catch (IOException e) {
			throw new ReportOutputException(e);
		}
    	super.close();
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
	 */
	public void setFilePath(String filePath) {
		try {
			setOutputWriter(new OutputStreamWriter(new FileOutputStream(filePath), UTF8_ENCODING));
		} catch (FileNotFoundException e) {
			throw new ReportOutputException(e); 
		} catch(UnsupportedEncodingException e){
			throw new ReportOutputException(e); 
		}
	}

}
