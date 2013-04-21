/*
 * Created on 29.01.2005
 * Author : dragos balan
 */
package net.sf.reportengine.out;



/**
 * The report output. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.1
 * @see {@link AbstractByteBasedOutput} {@link AbstractCharBasedOutput}
 */
public interface IReportOutput {
	
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
	 * outputs the title of the report (if any)
	 * 
	 * @param titleProperties
	 */
	public void outputTitle(TitleProps titleProperties);
	
	
	/**
	 * starts a new row in the report
	 * 
	 * @param rowProperties the properties of the row
	 */
	public void startRow(RowProps rowProperties);
    
	
	/**
	 * outputs the specified value taking into account the 
     * cell properties imposed by the algorithm 
	 * 
	 * @param cellProp  the properties of the cell to be output    
	 */
	public void output(CellProps cellProps);
	
	
    /**
     * ends of a row in the report 
     */
    public void endRow();

	
	/**
	 * closes the output (frees any resources used during the output)
	 */
	public void close();
    
}
