/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Comparator;
import java.util.List;

import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;

/**
 * @author dragos
 *
 */
public class NewRowComparator implements Comparator<NewRowEvent> {

	private List<GroupColumn> groupCols; 
	private List<DataColumn> dataCols; 
	
	public NewRowComparator(List<GroupColumn> groupCols, List<DataColumn> dataCols){
		this.groupCols = groupCols; 
		this.dataCols = dataCols; 
	}
	
	public int compare(NewRowEvent row1, NewRowEvent row2) {
		int result = 0;
		
		for (GroupColumn groupCol : groupCols) {
			Comparable valueRow1 = (Comparable)groupCol.getValue(row1);
			Comparable valueRow2 = (Comparable)groupCol.getValue(row2); 
			
			int valueComparison = valueRow1.compareTo(valueRow2); 
			if(valueComparison != 0){
				result = valueComparison; 
				break; 
			}
		}
		
		if(result == 0){
			for (DataColumn dataCol : dataCols) {
				Comparable valueRow1 = (Comparable)dataCol.getValue(row1); 
				Comparable valueRow2 = (Comparable)dataCol.getValue(row2); 
				
				int valueComparison = valueRow1.compareTo(valueRow2); 
				if(valueComparison != 0){
					result = valueComparison; 
					break; 
				}
			}
		}
		
		return result; 
	}
}
