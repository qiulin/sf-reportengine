/*
 * Created on 29.01.2005
 * Author : dragos balan
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.algorithm.IAlgorithmOutput;


/**
 * <p>
 *  the output interface
 * </p>
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 *
 *  
 */
public interface IReportOutput extends IAlgorithmOutput{
	
	/**
	 * the whitespace character
	 */
	public static final String WHITESPACE = " ";
	
	/**
	 * starts a new row in the report
	 */
	public void startRow();
    
    /**
     * ends of a row in the report 
     */
    public void endRow();

	/**
	 * outputs the specified value taking into accoount the 
     * cell properties imposed by the algorithm 
	 * 
	 * @param cellProp  the properties of the cell to be outputted    
	 */
	public void output(CellProps cellProps);
    
}
