/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculatorsFactory;

/**
 * This is the basic configuration of a report data column. 
 * The functionality is based on an inputColumnIndex so what this type of column does is 
 * to get the values from an input column ( based on the index of the input column)
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultDataColumn extends AbstractDataColumn {
	
	/**
	 * the index of the input column
	 */
	private int inputColumnIndex; 
	
	
	/**
	 * default constructor. 
	 * Assumes 
	 * 	inputColumn=0
	 * 	no calculators
	 *  header=Column 0 
	 *  no formatter
	 */
	public DefaultDataColumn(){
		this(0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultDataColumn(int inputColumnIndex){
		this("Column "+inputColumnIndex, inputColumnIndex);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 */
	public DefaultDataColumn(String header, int inputColumnIndex){
		this(header, inputColumnIndex, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculatorsFactory
	 */
	public DefaultDataColumn(String header, int inputColumnIndex, ICalculatorsFactory calculatorsFactory){
		this(header, inputColumnIndex, calculatorsFactory, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param calculator
	 * @param formatter
	 */
	public DefaultDataColumn(	String header,
								int inputColumnIndex, 
								ICalculatorsFactory calculator, 
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
