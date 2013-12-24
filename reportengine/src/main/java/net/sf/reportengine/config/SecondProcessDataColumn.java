/**
 * 
 */
package net.sf.reportengine.config;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculator;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;

/**
 * Warning: only for internal use !
 * This is a data column which uses 
 * 	1. the original crosstab data for visual properties (horiz/vert align, formatting)
 *  2. the previously computed value ( computed in the intermediate step)
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessDataColumn implements DataColumn{
	
	/**
	 * 
	 */
	private int[] positionRelativeToHeader; 
	
	/**
	 * original crosstab data
	 */
	private CrosstabData originalCtData; 
	
	/**
	 * 
	 * @param positionRelativeToHeader
	 * @param calculator
	 * @param formatter
	 */
	public SecondProcessDataColumn(	int[] positionRelativeToHeader, 	
									CrosstabData crosstabData) {
		//normally we don't need the column header
		this.positionRelativeToHeader = positionRelativeToHeader;
		this.originalCtData = crosstabData; 
	}
	
	
	public Object getValue(NewRowEvent newRowEvent) {
		//according to the contract the third object in each row array is an 
		//instance of IntermCtDataList
		List<Object> newRow = newRowEvent.getInputDataRow(); 
		IntermComputedDataList intermDataList = (IntermComputedDataList)newRow.get(2); 
		
		Object result = intermDataList.getValueFor(positionRelativeToHeader); 
		if(result == null){
			result = BigDecimal.ZERO; 
		}
		return result; 
	}
	
	public int[] getPosition(){
		return positionRelativeToHeader; 
	}


	public String getHeader() {
		return "Data"+Arrays.toString(positionRelativeToHeader);
	}


	public String getFormattedValue(Object value) {
		return originalCtData.getFormattedValue(value);
	}
	
	public String getFormattedTotal(Object value){
		return originalCtData.getFormattedTotal(value);
	}

	public GroupCalculator getCalculator() {
		return originalCtData.getCalculator();
	}


	public HorizAlign getHorizAlign() {
		return originalCtData.getHorizAlign();
	}


	public VertAlign getVertAlign() {
		return originalCtData.getVertAlign();
	}


	public int getSortLevel() {
		return NO_SORTING;
	}


	public SortType getSortType() {
		return SortType.NONE;
	}
}
