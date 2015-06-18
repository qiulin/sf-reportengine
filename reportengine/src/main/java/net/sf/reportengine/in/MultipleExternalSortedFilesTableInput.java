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
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;
import java.util.PriorityQueue;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.NewRowComparator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos
 *
 */
public class MultipleExternalSortedFilesTableInput implements TableInput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(MultipleExternalSortedFilesTableInput.class);
	
	/**
	 * 
	 */
	private PriorityQueue<RowsDataFileBuffer> externalFilesQueue; 
	
	
	public MultipleExternalSortedFilesTableInput(List<File> externalSortedFiles, 
											NewRowComparator newRowComparator){
		
		LOGGER.info("building input from {} external sorted files", externalSortedFiles.size()); 
		
		try{
			externalFilesQueue = new PriorityQueue<RowsDataFileBuffer>(	externalSortedFiles.size(), 
																		new RowsDataFileBufferComparator(newRowComparator));
	
			for (File file : externalSortedFiles) {
				//LOGGER.info("searching for external file "+file.getAbsolutePath());
				this.externalFilesQueue.add(new RowsDataFileBuffer(new FileInputStream(file))); 
			}
		}catch(FileNotFoundException fnfExc){
			throw new TableInputException("One external file could not be found :", fnfExc); 
		}
	}
	
	
//	public MultipleExternalSortedFilesInput(List<InputStream> externalSortedStreams, 
//									NewRowComparator newRowComparator){
//		LOGGER.info("building input from {} external sorted files", externalSortedStreams.size()); 
//		externalFilesQueue = new PriorityQueue<RowsDataFileBuffer>(
//									externalSortedStreams.size(), 
//									new RowsDataFileBufferComparator(newRowComparator));
//		
//		for (InputStream is : externalSortedStreams) {
//			this.externalFilesQueue.add(new RowsDataFileBuffer(is)); 
//		}
//	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#open()
	 */
	public void open() {
		

	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#close()
	 */
	public void close() {
		//TODO: move the close of the fileinput streams here
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#nextRow()
	 */
	public List<Object> nextRow() {
		List<Object> result = null; 
		if(hasMoreRows()){
			RowsDataFileBuffer rowsDataFileBuff = externalFilesQueue.poll();
			NewRowEvent newRowEvent = rowsDataFileBuff.poll();
			
			if(rowsDataFileBuff.isEmpty()){
				rowsDataFileBuff.close(); 
			}else{
				externalFilesQueue.add(rowsDataFileBuff); 
			}
			
			result = newRowEvent.getInputDataRow(); 
		}
		
		return result; 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#hasMoreRows()
	 */
	public boolean hasMoreRows() {
		return !externalFilesQueue.isEmpty(); 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#getColumnMetadata()
	 */
	public List<ColumnMetadata> getColumnMetadata() {
		// TODO Auto-generated method stub
		return null;
	}
}
