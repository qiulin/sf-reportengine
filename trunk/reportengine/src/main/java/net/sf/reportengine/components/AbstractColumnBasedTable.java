/**
 * 
 */
package net.sf.reportengine.components;

import java.util.ArrayList;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;

/**
 * @author dragos balan
 *
 */
public abstract class AbstractColumnBasedTable extends AbstractTable {


	/**
     * the data columns
     */
    private List<DataColumn> dataColsAsList = new ArrayList<DataColumn>();; 
    
    /**
     * grouping columns
     */
    private List<GroupColumn> groupColsAsList = new ArrayList<GroupColumn>(); 
    
    /**
	 * getter for data colums of this report
	 * @return an ordered list of data columns
	 */
	public List<DataColumn> getDataColumns() {
		return dataColsAsList; 
	}

	/**
	 * setter for the list of data columns 
	 * @param dataColsList
	 */
	public void setDataColumns(List<DataColumn> dataColsList){
		this.dataColsAsList = dataColsList; 
	}
	
	/**
	 * adds a data column to the existing list
	 * @param newColumn
	 */
	public void addDataColumn(DataColumn newColumn){
		this.dataColsAsList.add(newColumn); 
	}
	
	/**
	 * getter for the list of group columns
	 * @return an ordered list of group columns
	 */
	public List<GroupColumn> getGroupColumns() {
		return groupColsAsList; 
	}
	
	/**
	 * sets the given columns lists. 
	 * Please note that you cannot use {@link #setGroupColumns(List)} after {@link #addGroupColumn(GroupColumn)}
	 * because it will override the existing columns. 
	 * 
	 * @param groupColsList the new list of group columns
	 */
	public void setGroupColumns(List<GroupColumn> groupColsList){
		this.groupColsAsList = groupColsList;
	}
	
	/**
	 * adds a new group column to the list of the existing columns. 
	 * Please note that your column is added at the end of the existing list
	 * 
	 * @param newGroupCol the new group column
	 */
	public void addGroupColumn(GroupColumn newGroupCol){
		this.groupColsAsList.add(newGroupCol); 
	}

}
