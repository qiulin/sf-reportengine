/**
 * 
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import net.sf.reportengine.util.ReportIoUtils;

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
		setOutputWriter(ReportIoUtils.createWriterFromPath(filePath));
	}

}
