/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * default implementation for report column (IColumn)
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @deprecated As of version 0.4 this class will be replaced by GroupColumn and DataColumn
 */
public class DefaultColumn extends AbstractColumn {
	
	private int inputColumnIndex = 0;	
	
	public DefaultColumn(){
		this(0);
	}
	
	public DefaultColumn(int inputColIndex){
		this("Column "+inputColIndex, inputColIndex);
	}
	
	public DefaultColumn(String header, int inputColIndex){
		this(header, inputColIndex, NO_GROUP_COLUMN);
	}
	
	public DefaultColumn(	String header, 
							int inputColIndex, 
							int groupingOrder){
		this(	header, 
				inputColIndex, 
				groupingOrder, 
				true);
	} 
	
	
	public DefaultColumn(	String header, 
							int inputColIndex, 
							int groupingOrder, 
							boolean showTotalsOnChange){
		this(	header, 
				inputColIndex, 
				groupingOrder, 
				showTotalsOnChange, 
				null);
	}
	
	public DefaultColumn(	String header, 
							int inputColIndex,
							ICalculator calculator){
		this(	header, 
				inputColIndex, 
				NO_GROUP_COLUMN, 
				true, 
				calculator);
	}
	
	public DefaultColumn(	String header, 
							int inputColIndex, 
							int groupingOrder,
							ICalculator calculator){
		this(	header, 
				inputColIndex, 
				groupingOrder, 
				true, 
				calculator);
	}
	
	
	public DefaultColumn(	String header, 
							int inputColIndex, 
							int groupingOrder,
							boolean showTotalsOnChange, 
							ICalculator calculator){
		this(	header, 
				inputColIndex, 
				groupingOrder, 
				showTotalsOnChange, 
				calculator, 
				null);
	}
	
	
	public DefaultColumn(	String header, 
							int inputColIndex, 
							int groupingOrder, 
							ICalculator calculator, 
							Format outFormat){
		this(	header, 
				inputColIndex, 
				groupingOrder, 
				true, 
				calculator, 
				outFormat);
	}
	
	
	public DefaultColumn(	String header, 
							int inputColIndex, 
							int groupingOrder, 
							boolean showTotalsOnChange, 
							ICalculator calculator, 
							Format outFormat){
		
		setInputColumnIndex(inputColIndex);
		setHeader(header);
		setGroupingOrder(groupingOrder);
		setCalculator(calculator);
		setOutFormat(outFormat);
		setShowTotalsOnChange(showTotalsOnChange);
	}
	
	
	public Object getRawValue(NewRowEvent newRowEvent){
		Object[] newRowOfData = newRowEvent.getInputDataRow();
		return newRowOfData[inputColumnIndex];
	}
	

	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
	
	public String toString(){
		StringBuffer result = new StringBuffer(this.getClass().getSimpleName());
		result.append("[");
		result.append("header=");
		result.append(getHeader());
		result.append("]");
		return result.toString();
	}
}
