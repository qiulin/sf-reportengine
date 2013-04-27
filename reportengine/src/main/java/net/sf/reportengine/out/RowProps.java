/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;

/**
 * <p>properties of a report row</p>
 * <ol>
 * 	<li>content type</li>
 * 	<li>row number </li>
 * </ol>
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 */
public class RowProps {
    
    /**
     * row number
     */
    private final int rowNumber; 
    
    /**
     * 
     */
    public RowProps(){
    	this(0);
    }
    
    /**
     * 
     * @param content
     * @param rowNumber
     */
    public RowProps(int rowNumber){
    	this.rowNumber = rowNumber; 
    }
    
    
    
    /**
     * retrieves the row number stored in this row properties
     * @return
     */
    public int getRowNumber(){
    	return rowNumber; 
    }
}
