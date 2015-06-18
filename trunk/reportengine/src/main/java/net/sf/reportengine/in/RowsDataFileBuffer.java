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
	 */
	private boolean lastObjectReadWasMarkedAsLast = false; 
	
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
			throw new TableInputException(e) ;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isEmpty() {
		return buffer == null;
	}
	
	/**
	 * 
	 */
	private void loadNext() {
		try {
			if(!lastObjectReadWasMarkedAsLast){
				buffer = (NewRowEventWrapper)objectInputStream.readObject();
				lastObjectReadWasMarkedAsLast = buffer.isLast(); 
			}else{
				buffer = null; 
			}
		} catch (ClassNotFoundException e) {
			throw new TableInputException(e); 
		} catch (IOException e){
			throw new TableInputException(e); 
		}
	}
	
	/**
	 * 
	 */
	public void close() {
		try{
			objectInputStream.close();
			buffer = null; 
		}catch(IOException e){
			throw new TableInputException(e); 
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public NewRowEvent peek() {
		NewRowEvent result = null; 
		if(buffer !=  null){
			result = buffer.getNewRowEvent();
		}
		return result; 
	}
	
	/**
	 * 
	 * @return
	 */
	public NewRowEvent poll() {
	  NewRowEvent result = peek();
	  loadNext();
	  return result;
	}
}
