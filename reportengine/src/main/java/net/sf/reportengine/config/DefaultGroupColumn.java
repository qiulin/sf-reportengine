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
 * Grouping Column based on a single input column
 * 
 * @author dragos balan
 * @since 0.4
 */
public class DefaultGroupColumn extends AbstractGroupColumn {
	
	/**
	 * the index of the input column
	 */
	private int inputColumnIndex; 
	
	/**
	 * 
	 */
	public DefaultGroupColumn(){
		this(0);
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public DefaultGroupColumn(int inputColumnIndex){
		this("Column "+inputColumnIndex, inputColumnIndex, 0);
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 * @param groupingLevel
	 */
	public DefaultGroupColumn(int inputColumnIndex, int groupingLevel){
		this("Column "+inputColumnIndex, inputColumnIndex, groupingLevel);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel){
		this(header, inputColumnIndex, groupingLevel, null);
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter){
		this(header, inputColumnIndex, groupingLevel, formatter, HorizAlign.CENTER); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter, 
								HorizAlign horizAlign){
		this(header, inputColumnIndex, groupingLevel, formatter, horizAlign, false); 
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 * @param showDuplicates
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter, 
								HorizAlign horizAlign, 
								boolean showDuplicates){
		this(header, inputColumnIndex, groupingLevel, formatter, horizAlign, VertAlign.MIDDLE, showDuplicates); 
	}
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 * @param showDuplicates
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								boolean showDuplicates){
		this(header, inputColumnIndex, groupingLevel, formatter, horizAlign, vertAlign, showDuplicates, SortType.NONE /*no default sorting*/); 
	}
	
	
	/**
	 * 
	 * @param header
	 * @param inputColumnIndex
	 * @param groupingLevel
	 * @param formatter
	 * @param horizAlign
	 * @param showDuplicates
	 */
	public DefaultGroupColumn(	String header, 
								int inputColumnIndex, 
								int groupingLevel, 
								Format formatter, 
								HorizAlign horizAlign,
								VertAlign vertAlign, 
								boolean showDuplicates, 
								SortType sortType){
		super(header, groupingLevel, formatter, horizAlign, vertAlign, showDuplicates, sortType);
		setInputColumnIndex(inputColumnIndex);
	}
	
	/**
	 * 
	 * @param builder
	 */
	private DefaultGroupColumn(Builder builder){
		super(	builder.header, 
				builder.groupLevel, 
				builder.formatter, 
				builder.hAlign, 
				builder.vAlign, 
				builder.showDuplicateValues, 
				builder.sortType); 
		setInputColumnIndex(builder.columnIndex); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.config.GroupColumn#getValue(net.sf.reportengine.core.algorithm.NewRowEvent)
	 */
	public Object getValue(NewRowEvent newRowEvent) {
		return newRowEvent.getInputDataRow().get(inputColumnIndex);
	}
	
	/**
	 * 
	 * @return
	 */
	public int getInputColumnIndex() {
		return inputColumnIndex;
	}
	
	/**
	 * 
	 * @param inputColumnIndex
	 */
	public void setInputColumnIndex(int inputColumnIndex) {
		this.inputColumnIndex = inputColumnIndex;
	}
	
	/**
	 * 
	 */
	public String toString(){
		StringBuilder result = new StringBuilder("DefaultGroupColumn[");
		result.append("inputIdx=").append(inputColumnIndex);
		result.append(", groupLevel=").append(getGroupingLevel());
		result.append(", header=").append(getHeader());
		result.append(", hAlign=").append(getHorizAlign());
		result.append(", formatter=").append(getFormatter());
		result.append(", sortType=").append(getSortType()); 
		result.append("]");
		return result.toString(); 
	}
	
	public static class Builder{
		private int columnIndex; 
		private String header; 
		private HorizAlign hAlign = HorizAlign.CENTER; 
		private VertAlign  vAlign = VertAlign.MIDDLE; 
		private Format formatter = null; 
		private SortType sortType = SortType.NONE; 
		
		private int groupLevel = 0; 
		private boolean showDuplicateValues = false; 
		
		public Builder(int columnIndex){
			this.columnIndex = columnIndex; 
		}
		
		public Builder header(String header){
			this.header = header; 
			return this; 
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
		
		public Builder sortAsc(){
			this.sortType = SortType.ASC; 
			return this;
		}
		
		public Builder sortDesc(){
			this.sortType = SortType.DESC; 
			return this; 
		}
		
		public Builder level(int groupLevel){
			this.groupLevel = groupLevel; 
			return this; 
		}
		
		public Builder showDuplicateValues(){
			this.showDuplicateValues = true; 
			return this; 
		}
		
		public DefaultGroupColumn build(){
			return new DefaultGroupColumn(this); 
		}
	}
}
