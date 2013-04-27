/*
 * Created on 29.01.2005
 * Author : dragos balan
 */
package net.sf.reportengine.out;



/**
 * Report Output interface. The framework will call the methods in the following order: 
 * 
 *  	reportOutput.open(); 				//once per report
 *  	reportOutput.startReport(); 	`	//once per report
 *  	reportOutput.outputTitle();			//once per report
 *  	reportOutput.startHeaderRow(); 		//for each line of header	
 *  	reportOutput.outputHeaderCell(); 	//several times
 *  	reportOutput.endHeaderRow(); 		//for each header row started
 *  	reportOutput.startDataRow(); 	//for each resulting data row
 *  	reportOutput.outputDataCell(); 	//several times
 *  	reportOutput.endDataRow();		//for each data row started
 *  	reportOutput.endReport(); 		//once per report 
 *  	reportOutput.close(); 			//once per report
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.1
 * @see {@link AbstractByteBasedOutput} {@link AbstractCharBasedOutput}
 */
public interface ReportOutput {
	
	/**
	 * the whitespace character
	 */
	public static final String WHITESPACE = " ";
	
	/**
	 * prepares the output for usage.
	 * One possible usage is to open the output streams 
	 */
	public void open();
	
	/**
	 * One should use this method to output whatever is useful to the whole report.
	 * 
	 * @param reportProps	the properties of the report useful for output
	 */
	public void startReport(ReportProps reportProps); 
	
	/**
	 * outputs the title of the report (if any)
	 * 
	 * @param titleProperties
	 */
	public void outputTitle(TitleProps titleProperties);
	
	
	/**
	 * starts a new row of headers
	 */
	public void startHeaderRow(RowProps rowProperties);
	
	/**
	 * outputs a cell in the header row
	 * @param cellProps
	 */
	public void outputHeaderCell(CellProps cellProps); 
	
	/**
	 * ends a header row
	 */
	public void endHeaderRow(); 
	
	/**
	 * starts a new row of data 
	 * 
	 * @param rowProperties the properties of the row
	 */
	public void startDataRow(RowProps rowProperties);
    
	
	/**
	 * outputs the specified value taking into account the 
     * cell properties imposed by the algorithm 
	 * 
	 * @param cellProp  the properties of the cell to be output    
	 */
	public void outputDataCell(CellProps cellProps);
	
	
    /**
     * ends a data row
     */
    public void endDataRow();

	/**
	 * One should use this method to output whatever is needed for the end of the report.
	 */
    public void endReport(); 
    
	/**
	 * closes the output (frees any resources used during the output)
	 */
	public void close();
    
}
