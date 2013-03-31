/**
 * 
 */
package net.sf.reportengine.in;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.HorizAlign;


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
	 */
	private List<ColumnMetadata> columnMetadata; 
	
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
    	columnMetadata = readMetadata();
    	currentRow = 0;
    }

    /**
     * 
     */
    public void close() throws ReportInputException {
       super.close(); 
    }
	
	
	@Override public boolean hasMoreRows() {
		return currentRow < data.length ;
	}

	
	@Override public Object[] nextRow(){
		return data[currentRow++];
	}
	
	@Override public boolean supportsMetadata(){
		return true; 
	}
	
	
	private List<ColumnMetadata> readMetadata(){
		List<ColumnMetadata> result = new ArrayList<ColumnMetadata>(); 
		if(data != null && data.length > 0 && data[0] != null && data[0].length >0){
			int arrayLength = data[0].length; 
			for (int i = 0; i < arrayLength; i++) {
				ColumnMetadata metadata = new ColumnMetadata();
				metadata.setColumnId(""+i);
				metadata.setColumnLabel(""+i);
				metadata.setHorizontalAlign(HorizAlign.CENTER);//TODO: infer horizontal align from object's class
			}
		}
		
		return result;
	}
	
	
	@Override public List<ColumnMetadata> getColumnMetadata() {
		return columnMetadata; 
	}
}
