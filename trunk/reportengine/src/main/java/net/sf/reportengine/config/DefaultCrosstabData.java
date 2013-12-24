/**
 * 
 */
package net.sf.reportengine.config;

import net.sf.reportengine.core.algorithm.NewRowEvent;
import net.sf.reportengine.core.calc.GroupCalculator;


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
	public DefaultCrosstabData(int inputColumnIndex, GroupCalculator calc){
		this(inputColumnIndex, calc, null);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 */
	public DefaultCrosstabData(	int inputColumIndex, 
								GroupCalculator calc, 
								String valuesFormatter){
		this(inputColumIndex, calc, valuesFormatter, HorizAlign.CENTER);
	}
	
	/**
	 * 
	 * @param inputColumIndex
	 * @param calcFactory
	 * @param formatter
	 * @param horizAlign
	 */
	public DefaultCrosstabData(	int inputColumnIndex, 
								GroupCalculator calc, 
								String valuesFormatter, 
								HorizAlign horizAlign){
		this(inputColumnIndex, calc, valuesFormatter, horizAlign, VertAlign.MIDDLE);
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
								GroupCalculator calc, 
								String valuesFormatter, 
								HorizAlign horizAlign, 
								VertAlign vertAlign){
		super(calc, valuesFormatter, horizAlign, vertAlign);
		setInputColumnIndex(inputColumIndex);
	}	
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultCrosstabData(Builder builder){
		super(	builder.calculator, 
				builder.valuesFormatter, 
				builder.totalsFormatter, 
				builder.hAlign, 
				builder.vAlign); 
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
		private String  valuesFormatter = null;
		private String totalsFormatter = null;
		private GroupCalculator calculator = null; 
		
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
		
		public Builder valuesFormatter(String formatter){
			this.valuesFormatter = formatter; 
			return this; 
		}
		
		public Builder useCalculator(GroupCalculator calc){
			this.calculator = calc; 
			return this; 
		}
		
		public Builder useCalculator(GroupCalculator calc, String totalsFormatter){
			this.calculator = calc; 
			this.totalsFormatter = totalsFormatter; 
			return this; 
		}
		
		public DefaultCrosstabData build(){
			return new DefaultCrosstabData(this); 
		}
	}
}
