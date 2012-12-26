/**
 * 
 */
package net.sf.reportengine.out;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractByteOutput extends AbstractOutput {
	
	/**
	 * the outputstream behind this report output
	 */
	private OutputStream outputStream; 
	
	/**
	 * 
	 */
	public AbstractByteOutput() {
		setOutputStream(new ByteArrayOutputStream()); 
	}

	/**
	 * @param filePath
	 */
	public AbstractByteOutput(String filePath) {
		setFilePath(filePath); 
	}

	/**
	 * @param out
	 */
	public AbstractByteOutput(OutputStream out) {
		setOutputStream(out); 
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
    	super.close();
    }

	
	/**
	 * @return the outputStream
	 */
	public OutputStream getOutputStream() {
		return outputStream;
	}

	/**
	 * @param outputStream the outputStream to set
	 */
	public void setOutputStream(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * 
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
