/**
 * 
 */
package net.sf.reportengine.in;

import java.io.File;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.NewRowEventWrapper;

/**
 * @author dragos
 *
 */
public class ExternalSortedFilesInput implements ReportInput {
	
	/**
	 * 
	 */
	private PriorityQueue<RowsDataFileBuffer> externalFilesQueue; 
	
	/**
	 * 
	 */
	private Comparator<NewRowEvent> newRowComparator; 
	
	/**
	 * 
	 * @param externalSortedFiles
	 */
	public ExternalSortedFilesInput(List<File> externalSortedFiles, 
									Comparator<NewRowEvent> newRowComparator){
		this.externalFilesQueue = new PriorityQueue<RowsDataFileBuffer>();
		for (File file : externalSortedFiles) {
			this.externalFilesQueue.add(new RowsDataFileBuffer(file)); 
		}
		this.newRowComparator = newRowComparator; 
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
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#nextRow()
	 */
	public Object[] nextRow() {
		Object[] result = null; 
		if(hasMoreRows()){
			RowsDataFileBuffer rowsDataFileBuff = externalFilesQueue.poll();
			NewRowEvent newRowEvent = rowsDataFileBuff.pop();
			
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
		return externalFilesQueue.size() > 0; 
	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.in.ReportInput#getColumnMetadata()
	 */
	public List<ColumnMetadata> getColumnMetadata() {
		// TODO Auto-generated method stub
		return null;
	}

}
