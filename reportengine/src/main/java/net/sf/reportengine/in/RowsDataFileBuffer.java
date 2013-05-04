/**
 * 
 */
package net.sf.reportengine.in;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.NewRowEventWrapper;
import net.sf.reportengine.util.ReportIoUtils;

/**
 * @author dragos
 *
 */
class RowsDataFileBuffer {
	
	/**
	 * 
	 */
	private ObjectInputStream objectInputStream;
	
	/**
	 * 
	 */
	private NewRowEventWrapper buffer;
	
	/**
	 * 
	 * @param inputFile
	 */
	public RowsDataFileBuffer(File inputFile){
		this(ReportIoUtils.createInputStreamFromFile(inputFile)); 
	}
	
	/**
	 * 
	 * @param inputStream
	 */
	public RowsDataFileBuffer(InputStream inputStream) {
		try {
			objectInputStream = new ObjectInputStream(inputStream);
			loadNext();
		} catch (IOException e) {
			throw new ReportInputException(e) ;
		}
	}
	
	public boolean isEmpty() {
		return buffer == null || buffer.isLast();
	}
	
	private void loadNext() {
		try {
			buffer = (NewRowEventWrapper)objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			throw new ReportInputException(e); 
		} catch (IOException e){
			throw new ReportInputException(e); 
		}
	}
	
	public void close() {
		try{
			objectInputStream.close();
			buffer = null; 
		}catch(IOException e){
			throw new ReportInputException(e); 
		}
	}
	
	
	public NewRowEvent peek() {
		if(isEmpty()) return null;
		return buffer.getNewRowEvent();
	}
	
	public NewRowEvent pop() {
	  NewRowEvent result = peek();
	  loadNext();
	  return result;
	}
}
