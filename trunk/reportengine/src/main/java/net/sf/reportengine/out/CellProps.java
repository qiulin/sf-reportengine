/*
 * Created on 25.03.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.config.HorizAlign;
import net.sf.reportengine.config.VertAlign;
import net.sf.reportengine.core.ReportContent;

/**
 * immutable cell properties class. 
 * This is constructed internally to send data to the output. 
 * The only use of this class is inside the FreemarkerOutput and all outputs using FreemarkerOutput
 * inside
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public final class CellProps {
	
	/**
	 * empty cell having a colspan of 1 
	 */
	public static final CellProps EMPTY_CELL = new CellProps.Builder(ReportOutput.WHITESPACE).build(); 

	/**
     * the column span
     */
	private final int colspan;

	/**
     * the value to be displayed in the cell
     */
	private final Object value;

	/**
     * horizontal alignment
     */
	private final HorizAlign horizAlign;
	
	/**
	 * vertical align
	 */
	private final VertAlign vertAlign;
	
	/**
	 * row number
	 */
	private final int rowNbr; 

	
	/**
	 * constructor using the fluent builder for CellProps
	 * 
	 * @param propsBuilder
	 */
	private CellProps(Builder propsBuilder){
		this.value = propsBuilder.value; 
		this.colspan = propsBuilder.colspan; 
		this.horizAlign = propsBuilder.horizAlign; 
		this.vertAlign = propsBuilder.vertAlign; 
		this.rowNbr = propsBuilder.rowNumber; 
	}
	
	/**
	 * column span
	 * @return
	 */
	public int getColspan() {
		return colspan;
	}
	
	/**
	 * the value to be displayed
	 * @return
	 */
	public Object getValue() {
		return value;
	}
	
	/**
	 * the horizontal align
	 * @return
	 */
	public HorizAlign getHorizAlign() {
		return horizAlign;
	}
	
	/**
	 * 
	 * @return
	 */
	public VertAlign getVertAlign(){
		return vertAlign; 
	}
	
	/**
	 * 
	 * @return
	 */
	public int getRowNumber(){
		return rowNbr; 
	}
	
	/**
	 * equals
	 */
	@Override public boolean equals(Object another) {
		boolean result = false;
		if (another instanceof CellProps) {
			CellProps anotherAsCP = (CellProps) another;
			result = value.equals(anotherAsCP.getValue()) 
					 && (colspan == anotherAsCP.getColspan())
					 // TODO include the horizontal alignment in the equals
					 //&& (horizAlign.equals(anotherAsCP.getHorizontalAlign()))
					 //&& (vertAlign.equals(anotherAsCP.getVertAlign()))
					 //&& (rowNbr == anotherAsCP.getRowNumber())
					 ; 
		}
		return result;
	}
	
	/**
	 * hashCode
	 */
	@Override public int hashCode() {
		int result = 3;
		result = 97 * result + value.hashCode();
		result = 97 * result + colspan;
		result = 97 * result + horizAlign.hashCode(); 
		result = 97 * result + vertAlign.hashCode(); 
		result = 97 * result + rowNbr; 
		return result;
	}
	
	/**
	 * toString
	 */
	@Override public String toString() {
		return new StringBuilder("CP[")
		.append(value != null ? value.getClass().getSimpleName() : "ClassCannotBeIdentified")
		.append("(").append(value).append(")")
		.append(",cspan=").append(colspan)
		.append(", hAlign=").append(horizAlign)
		.append(", vAlign=").append(vertAlign) 
		.append(", rowNbr=").append(rowNbr)
		.append("]").toString(); 
	}
	
	/**
	 * builder class for CellProps
	 * 
	 * @author dragos balan
	 *
	 */
	public static class Builder{
		
		private final Object value;
		private int colspan = 1;
		
		private HorizAlign horizAlign = HorizAlign.CENTER;
		private VertAlign vertAlign = VertAlign.MIDDLE; 
		
		private int rowNumber = 0; 
		
		public Builder(Object value){
			this.value = value; 
		}
		
		public Builder colspan(int colspan){
			this.colspan = colspan; 
			return this; 
		}		
		
		public Builder horizAlign(HorizAlign align){
			this.horizAlign = align; 
			return this; 
		}
		
		public Builder vertAlign(VertAlign align){
			this.vertAlign = align; 
			return this; 
		}
		
		public Builder rowNumber(int rowNbr){
			this.rowNumber = rowNbr; 
			return this; 
		}
		
		public CellProps build(){
			return new CellProps(this); 
		}
	}
}