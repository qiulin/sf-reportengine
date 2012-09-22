/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;

/**
 * This is the basic configuration of a report data column. 
 * The functionality is based on an inputColumnIndex so what this type of column does is 
 * to get the values from an input column ( based on the index of the input column)
 * 
 * @author dragos balan
 * @since 0.4
 */
public class DefaultDataColumn extends AbstractDataColumn {
	
	private int inputColumnIndex; 
	
	public DefaultDataColumn(int inputColumnIndex){
		this("Column "+inputColumnIndex, inputColumnIndex);
	}
	
	public DefaultDataColumn(String header, int inputColumnIndex){
		this(header, inputColumnIndex, null);
	}
	
	public DefaultDataColumn(String header, int inputColumnIndex, ICalculator calculator){
		this(header, inputColumnIndex, calculator, null);
	}
	
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								ICalculator calculator, 
								Format formatter){
		super(header, calculator, formatter);
		setInputColumnIndex(inputColumnIndex);
	}
	
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.IDataColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow()[inputColumnIndex];
	}


	public int getInputColumnIndex() {
		return inputColumnIndex;
	}


	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}

}
