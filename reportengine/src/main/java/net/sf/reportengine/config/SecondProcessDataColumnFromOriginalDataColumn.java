/**
 * 
 */
package net.sf.reportengine.config;

import java.util.List;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculator;
import net.sf.reportengine.core.steps.crosstab.IntermOriginalDataColsList;

/**
 * This is only for internal usage !
 * 
 * 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessDataColumnFromOriginalDataColumn implements DataColumn {
	
	/**
	 * 
	 */
	private DataColumn originalDataColumn;
	
	/**
	 * 
	 */
	private int indexInOriginalDataColsArray; 
	
	
	/**
	 * 
	 * @param origDataColumn
	 * @param indexInDataColsArray
	 */
	public SecondProcessDataColumnFromOriginalDataColumn(	DataColumn origDataColumn, 
															int indexInDataColsArray){
		this.originalDataColumn = origDataColumn; 
		this.indexInOriginalDataColsArray = indexInDataColsArray; 
	}

	public String getHeader() {
		return originalDataColumn.getHeader();
	}

	public String getFormattedValue(Object value) {
		return originalDataColumn.getFormattedValue(value); 
	}
	
	public String getFormattedTotal(Object value){
		return originalDataColumn.getFormattedTotal(value); 
	}
	
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the second object in each row array is an 
		//instance of IntermOrigGroupValuesList
		List<Object> newRow = newRowEvent.getInputDataRow(); 
		IntermOriginalDataColsList intermGroupValues = (IntermOriginalDataColsList)newRow.get(1); 
				
		//we get the group value according to the group level
		return intermGroupValues.getDataValues().get(indexInOriginalDataColsArray); 
	}

	public GroupCalculator getCalculator() {
		return originalDataColumn.getCalculator();
	}

	public HorizAlign getHorizAlign() {
		return originalDataColumn.getHorizAlign(); 
	}
	
	public VertAlign getVertAlign() {
		return originalDataColumn.getVertAlign(); 
	}
	
	public int getSortLevel() {
		return originalDataColumn.getSortLevel(); 
	}

	public SortType getSortType() {
		return originalDataColumn.getSortType();
	}
}
