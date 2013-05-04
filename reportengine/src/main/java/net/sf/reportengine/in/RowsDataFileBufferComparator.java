/**
 * 
 */
package net.sf.reportengine.in;

import java.util.Comparator;

import net.sf.reportengine.core.steps.NewRowComparator;

/**
 * @author dragos
 *
 */
class RowsDataFileBufferComparator implements Comparator<RowsDataFileBuffer> {
	
	/**
	 * 
	 */
	private NewRowComparator newRowComparator;
	
	/**
	 * 
	 * @param newRowComparator
	 */
	public RowsDataFileBufferComparator(NewRowComparator newRowComparator){
		this.newRowComparator = newRowComparator; 
	}
	
	/* (non-Javadoc)
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(RowsDataFileBuffer buffer1, RowsDataFileBuffer buffer2) {
		return newRowComparator.compare(buffer1.peek(), buffer2.peek());
	}
}
