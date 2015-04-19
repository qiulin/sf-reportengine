/**
 * 
 */
package net.sf.reportengine.components;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.in.TableInput;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractColumnBasedTable extends AbstractTable {


	/**
     * the data columns
     */
    private List<DataColumn> dataColsAsList = new ArrayList<DataColumn>();
    
    /**
     * grouping columns
     */
    private List<GroupColumn> groupColsAsList = new ArrayList<GroupColumn>(); 
    
    /**
     * 
     * @param input
     * @param dataCols
     * @param groupCols
     * @param showTotals
     * @param showGrandTotal
     * @param showDataRows
     * @param valuesSorted
     */
    public AbstractColumnBasedTable(TableInput input, List<DataColumn> dataCols, List<GroupColumn> groupCols, boolean showTotals, boolean showGrandTotal, boolean showDataRows, boolean valuesSorted){
    	super(input, showTotals, showGrandTotal, showDataRows, valuesSorted); 
    	this.dataColsAsList = dataCols; 
    	this.groupColsAsList = groupCols; 
    }
    
    /**
	 * getter for data colums of this report
	 * @return an ordered list of data columns
	 */
	public List<DataColumn> getDataColumns() {
		return dataColsAsList; 
	}

	
	/**
	 * getter for the list of group columns
	 * @return an ordered list of group columns
	 */
	public List<GroupColumn> getGroupColumns() {
		return groupColsAsList; 
	}
	
}
