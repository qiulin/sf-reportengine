/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.config.DefaultDataColumn.Builder;
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
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultCrosstabData(Builder builder){
		super(builder.calculator, builder.formatter, builder.hAlign, builder.vAlign); 
		setInputColumnIndex(builder.columnIndex); 
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
	
	public static class Builder{
		private int columnIndex; 
		private HorizAlign hAlign = HorizAlign.CENTER; 
		private VertAlign  vAlign = VertAlign.MIDDLE; 
		private Format formatter = null; 
		private Calculator calculator = null; 
		
		public Builder(int columnIndex){
			this.columnIndex = columnIndex; 
		}
		
		public Builder horizAlign(HorizAlign hAlign){
			this.hAlign = hAlign; 
			return this; 
		}
		
		public Builder vertAlign(VertAlign vAlign){
			this.vAlign = vAlign; 
			return this; 
		}
		
		public Builder useFormatter(Format format){
			this.formatter = format; 
			return this; 
		}
		
		public Builder useCalculator(Calculator calc){
			this.calculator = calc; 
			return this; 
		}
		
		public DefaultCrosstabData build(){
			return new DefaultCrosstabData(this); 
		}
	}
}
