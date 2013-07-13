/**
 * 
 */
package net.sf.reportengine.config;

import java.text.Format;

import net.sf.reportengine.core.algorithm.NewRowEvent;

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
		this(header, inputColumnIndex, groupingLevel, formatter, horizAlign, vertAlign, showDuplicates, SortType.ASC); 
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
}
