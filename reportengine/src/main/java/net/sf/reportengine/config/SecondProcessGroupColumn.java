/**
 * 
 */
package net.sf.reportengine.config;

import java.util.List;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalGroupValuesList;

/**
 * This is only for internal use.
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessGroupColumn implements GroupColumn {
	
	private GroupColumn originalGroupColumn; 
	
	public SecondProcessGroupColumn(GroupColumn anotherGroupColumn){
		this.originalGroupColumn = anotherGroupColumn;  
	}
	

	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.GroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the first object in each row array is an 
		//instance of IntermOrigGroupValuesList
		List<Object> newRow = newRowEvent.getInputDataRow(); 
		IntermOriginalGroupValuesList intermGroupValues = (IntermOriginalGroupValuesList)newRow.get(0); 
		
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


	public HorizAlign getHorizAlign() {
		return originalGroupColumn.getHorizAlign(); 
	}
	
	public VertAlign getVertAlign() {
		return originalGroupColumn.getVertAlign(); 
	}
	
	public boolean showDuplicates(){
		return originalGroupColumn.showDuplicates(); 
	}
}
