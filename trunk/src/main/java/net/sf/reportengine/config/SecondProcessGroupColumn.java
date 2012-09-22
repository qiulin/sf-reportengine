/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;

/**
 * @author Administrator
 *
 */
public class SecondProcessGroupColumn implements IGroupingColumn {
	
	private IGroupingColumn originalGroupColumn; 
	
	public SecondProcessGroupColumn(IGroupingColumn anotherGroupColumn){
		this.originalGroupColumn = anotherGroupColumn;  
	}
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IGroupingColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the first object in each row array is an 
		//instance of IntermOrigGroupValuesList
		Object[] newRow = newRowEvent.getInputDataRow(); 
		IntermOriginalGroupValuesList intermGroupValues = (IntermOriginalGroupValuesList)newRow[0]; 
		
		//we get the group value according to the group level
		return intermGroupValues.getGroupValues().get(getGroupingLevel()); 
	}


	public String getHeader() {
		return originalGroupColumn.getHeader(); 
	}


	public int getGroupingLevel() {
		return originalGroupColumn.getGroupingLevel();
	}


	public String getFormattedValue(Object object) {
		return originalGroupColumn.getFormattedValue(object);
	}
}
