/**
 * 
 */
package net.sf.reportengine.in;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * Report Input implementation based on a 2 dimensional in memory array
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class ArrayReportInput extends AbstractReportInput {
	
	/**
	 * the data to be returned row by row
	 */
	private Object[][] data;
	
	/**
	 * the current row
	 */
	private int currentRow = 0;
	
	/**
	 * 
	 * @param data
	 */
	public ArrayReportInput(Object[][] data){
		this.data = data;
	}
	
	/**
     * 
     */
    public void open() throws ReportInputException{
    	super.open(); 
    	setColumnMetadata(readMetadata());
    	currentRow = 0;
    }

	public boolean hasMoreRows() {
		return currentRow < data.length ;
	}

	
	public List<Object> nextRow(){
		return Arrays.asList(data[currentRow++]);
	}
		
	
	private List<ColumnMetadata> readMetadata(){
		List<ColumnMetadata> result = new ArrayList<ColumnMetadata>(); 
		if(data != null && data.length > 0 && data[0] != null && data[0].length >0){
			int arrayLength = data[0].length; 
			for (int i = 0; i < arrayLength; i++) {
				String columnId = ""+i;
				ColumnMetadata metadata = new ColumnMetadata(columnId, columnId);
			}
		}
		
		return result;
	}
}
