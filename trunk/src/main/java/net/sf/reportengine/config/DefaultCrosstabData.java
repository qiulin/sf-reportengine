/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculatorsFactory;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultCrosstabData extends AbstractCrosstabData {
	
	/**
	 * 
	 */
	private int inputColumnIndex; 
	
	
	public DefaultCrosstabData(){
		this(0); 
	}
	
	public DefaultCrosstabData(int inputColumnIndex){
		this(inputColumnIndex, null); 
	}
	
	public DefaultCrosstabData(int inputColumnIndex, ICalculatorsFactory calcFactory){
		this(inputColumnIndex, calcFactory, null);
	}
	
	public DefaultCrosstabData(int inputColumIndex, ICalculatorsFactory calcFactory, Format formatter){
		super(calcFactory, formatter);
		setInputColumnIndex(inputColumIndex);
	}
	
	public Object getValue(NewRowEvent newRowEvent) {
		Object[] newRow = newRowEvent.getInputDataRow(); 
		return newRow[inputColumnIndex];
	}

	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
	
	
	
	
}
