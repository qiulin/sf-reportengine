/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.ICalculator;


/**
 * @author Administrator
 *
 */
public class DefaultCrosstabData extends AbstractCrosstabData {
	
	private int inputColumnIndex; 
	
	
	public DefaultCrosstabData(int inputColumnIndex, ICalculator calculator){
		this(inputColumnIndex, calculator, null);
	}
	
	public DefaultCrosstabData(int inputColumIndex, ICalculator calculator, Format formatter){
		super(calculator, formatter);
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
