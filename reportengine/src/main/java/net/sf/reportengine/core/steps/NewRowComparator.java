/**
 * 
 */
package net.sf.reportengine.core.steps;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import net.sf.reportengine.config.AbstractDataColumn;
import net.sf.reportengine.config.DataColumn;
import net.sf.reportengine.config.GroupColumn;
import net.sf.reportengine.core.algorithm.NewRowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author dragos balan
 *
 */
public class NewRowComparator implements Comparator<NewRowEvent> {
	
	/**
	 * the one and only logger
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(NewRowComparator.class);
	
	/**
	 * 
	 */
	private List<GroupColumn> groupCols; 
	
	/**
	 * 
	 */
	private SortedSet<DataColumn> dataColsHavingOrdering = new TreeSet<DataColumn>(
			new Comparator<DataColumn>(){
				public int compare(DataColumn col1, DataColumn col2) {
					int result = 0;//they are equal
					if(col1.getOrderLevel() > col2.getOrderLevel()){
						result = 1;//the higher the order level, the lower the ordering priority 
					}else{
						if(col1.getOrderLevel() <  col2.getOrderLevel()){
							result = -1;
						}else{
							LOGGER.warn("Two columns having the same order priority found. Wrong configuration !"); 
						}
					}
					return result; 
				}
			}
		); 
	
	/**
	 * 
	 * @param groupCols
	 * @param dataCols
	 */
	public NewRowComparator(List<GroupColumn> groupCols, List<DataColumn> dataCols){
		this.groupCols = groupCols; 
		for (DataColumn dataColumn : dataCols) {
			if(dataColumn.getOrderLevel() > AbstractDataColumn.NO_ORDER){
				dataColsHavingOrdering.add(dataColumn); 
			}
		}
		LOGGER.info("after construction the dataColumnsHavingOrdering is {}", dataColsHavingOrdering);  
	}
	
	/**
	 * 
	 */
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
		
		if(result == 0 && !dataColsHavingOrdering.isEmpty()){
			for (DataColumn dataCol : dataColsHavingOrdering) {
				//LOGGER.info("comparing rows for data cols {} ", dataCol.getHeader()); 
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
