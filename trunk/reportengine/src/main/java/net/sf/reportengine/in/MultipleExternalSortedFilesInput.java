/**
 * 
 */
package net.sf.reportengine.in;

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
public class MultipleExternalSortedFilesInput implements ReportInput {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory
			.getLogger(MultipleExternalSortedFilesInput.class);
	/**
	 * 
	 */
	private PriorityQueue<RowsDataFileBuffer> externalFilesQueue; 
	
	/**
	 * 
	 * @param externalSortedFiles
	 */
	public MultipleExternalSortedFilesInput(List<InputStream> externalSortedStreams, 
									NewRowComparator newRowComparator){
		LOGGER.info("building input from {} external sorted files", externalSortedStreams.size()); 
		externalFilesQueue = new PriorityQueue<RowsDataFileBuffer>(
									externalSortedStreams.size(), 
									new RowsDataFileBufferComparator(newRowComparator));
		
		for (InputStream is : externalSortedStreams) {
			this.externalFilesQueue.add(new RowsDataFileBuffer(is)); 
		}
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#open()
	 */
	public void open() {
		

	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#close()
	 */
	public void close() {
		

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
