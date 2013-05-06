/**
 * 
 */
package net.sf.reportengine.config;

import java.math.BigDecimal;
import java.text.Format;
import java.util.Arrays;
import java.util.List;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculator;
import net.sf.reportengine.core.steps.crosstab.IntermComputedDataList;

/**
 * this is only for internal use. 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class SecondProcessDataColumn extends AbstractDataColumn{
	
	private int[] positionRelativeToHeader; 
	
	/**
	 * 
	 * @param positionRelativeToHeader
	 * @param calculator
	 * @param formatter
	 */
	public SecondProcessDataColumn(	int[] positionRelativeToHeader, 	
									Calculator calc, 
									Format formatter) {
		//normally we don't need the column header
		super("Data"+Arrays.toString(positionRelativeToHeader), calc, formatter);
		this.positionRelativeToHeader = positionRelativeToHeader; 
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
}
