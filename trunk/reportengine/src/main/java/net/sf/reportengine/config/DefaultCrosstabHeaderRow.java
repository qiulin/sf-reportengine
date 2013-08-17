/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.config.DefaultGroupColumn.Builder;
import net.sf.reportengine.core.algorithm.NewRowEvent;


/**
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.4
 */
public class DefaultCrosstabHeaderRow extends AbstractCrosstabHeaderRow {

	/**
	 * 
	 */
	private int inputColumnIndex; 
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultCrosstabHeaderRow(int inputColumnIndex){
		this(inputColumnIndex, null);
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param formatter
	 */
	public DefaultCrosstabHeaderRow(int inputColumnIndex, 
									Format formatter){
		super(formatter); 
		setInputColumnIndex(inputColumnIndex);
	}
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultCrosstabHeaderRow(Builder builder){
		super(builder.formatter); 
		setInputColumnIndex(builder.columnIndex); 
	}
	
	public int getInputColumnIndex() {
		return inputColumnIndex;
	}
	
	public void setInputColumnIndex(int index){
		this.inputColumnIndex = index; 
	}
	
	public Object getValue(NewRowEvent newRowEvent){
		return newRowEvent.getInputDataRow().get(inputColumnIndex);
	}
	
	
	public static class Builder{
		private int columnIndex; 
		private Format formatter = null; 
		
		
		public Builder(int columnIndex){
			this.columnIndex = columnIndex; 
		}
		
		public Builder useFormatter(Format format){
			this.formatter = format; 
			return this; 
		}
		
		public DefaultCrosstabHeaderRow build(){
			return new DefaultCrosstabHeaderRow(this); 
		}
	}
}
