/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.Calculator;


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
	
	
	/**
	 * 
	 */
	public DefaultCrosstabData(){
		this(0); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultCrosstabData(int inputColumnIndex){
		this(inputColumnIndex, null); 
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param calcFactory
	 */
	public DefaultCrosstabData(int inputColumnIndex, Calculator calc){
		this(inputColumnIndex, calc, null);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 */
	public DefaultCrosstabData(	int inputColumIndex, 
								Calculator calc, 
								Format formatter){
		this(inputColumIndex, calc, formatter, HorizAlign.CENTER);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 * @param horizAlign
	 */
	public DefaultCrosstabData(	int inputColumnIndex, 
								Calculator calc, 
								Format formatter, 
								HorizAlign horizAlign){
		this(inputColumnIndex, calc, formatter, horizAlign, VertAlign.MIDDLE);
	}
	
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 * @param horizAlign
	 * @param vertAlign
	 */
	public DefaultCrosstabData(	int inputColumIndex, 
								Calculator calc, 
								Format formatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign){
		super(calc, formatter, horizAlign, vertAlign);
		setInputColumnIndex(inputColumIndex);
	}	
	
	
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow().get(inputColumnIndex);
	}

	public int getInputColumnIndex() {
		return inputColumnIndex;
	}

	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
}
