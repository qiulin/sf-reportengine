/**
 * 
 */
package net.sf.reportengine.in;

import net.sf.reportengine.config.HorizontalAlign;


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
	private ColumnMetadata[] columnMetadata; 
	
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
	
	
	private ColumnMetadata[] readMetadata(){
		ColumnMetadata[] metadata = null; 
		if(data != null && data.length > 0 && data[0] != null && data[0].length >0){
			metadata = new ColumnMetadata[data[0].length];
			for (int i = 0; i < metadata.length; i++) {
				metadata[i] = new ColumnMetadata();
				metadata[i].setColumnId(""+i);
				metadata[i].setColumnLabel(""+i);
				metadata[i].setHorizontalAlign(HorizontalAlign.CENTER);//TODO: infer horizontal align from object's class
			}
		}else{
			metadata = new ColumnMetadata[0];
		}
		
		return metadata;
	}
	
	
	@Override public ColumnMetadata[] getColumnMetadata() {
		return columnMetadata; 
	}
}
