/*
 * Created on 25.03.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.config.HorizontalAlign;
import net.sf.reportengine.core.ReportContent;

/**
 * immutable cell properties class
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class CellProps {

	/**
     * 
     */
	private final int colspan;

	/**
     * 
     */
	private final ReportContent content;

	/**
     * 
     */
	private final Object value;

	/**
     * 
     */
	private final HorizontalAlign horizAlign;

	
//	private CellProps() {
//		this("");
//	}
//
//	
//	private CellProps(Object value) {
//		this(value, 1);
//	}
//
//	
//	private CellProps(Object value, int colspan) {
//		this(value, colspan, ReportContent.CONTENT_DATA);
//	}
//	
//	
//	private CellProps(Object value, int colspan, ReportContent contentType){
//		this(value, colspan, contentType, HorizontalAlign.CENTER); 
//	}
//	
//	
//	private CellProps(Object value, int colspan, ReportContent contentType, HorizontalAlign horizAlign) {
//		this.value = value; 
//		this.colspan = colspan; 
//		this.content = contentType;
//		this.horizAlign = horizAlign; 
//	}
	
	/**
	 * 
	 * @param propsBuilder
	 */
	private CellProps(Builder propsBuilder){
		this.value = propsBuilder.value; 
		this.colspan = propsBuilder.colspan; 
		this.content = propsBuilder.content; 
		this.horizAlign = propsBuilder.horizAlign; 
	}
	
	
	public int getColspan() {
		return colspan;
	}

//	public void setColspan(int colspan) {
//		this.colspan = colspan;
//	}

	public ReportContent getContentType() {
		return content;
	}

//	public void setContentType(ReportContent contentType) {
//		this.content = contentType;
//	}

	public Object getValue() {
		return value;
	}

//	public void setValue(Object value) {
//		this.value = value;
//	}
	
	public HorizontalAlign getHorizontalAlign() {
		return horizAlign;
	}

//	public void setHorizontalAlign(HorizontalAlign horizAlign) {
//		this.horizAlign = horizAlign;
//	}
	
	@Override
	public boolean equals(Object another) {
		boolean result = false;
		if (another instanceof CellProps) {
			CellProps anotherAsCP = (CellProps) another;
			result = getValue().equals(anotherAsCP.getValue())
					&& getColspan() == anotherAsCP.getColspan()
					&& getContentType() == anotherAsCP.getContentType();
		}
		return result;
	}

	@Override
	public int hashCode() {
		int result = 3;
		result = 97 * result + value.hashCode();
		result = 97 * result + colspan;
		result = 97 * result + content.hashCode();
		result = 97 * result + horizAlign.hashCode(); 
		return result;
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("CP[");
		result.append(value);
		result.append(",cspan=").append(colspan);
		result.append(",type=").append(content);
		result.append(", hAlign=").append(horizAlign);
		result.append("]");
		return result.toString();
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
		private ReportContent content = ReportContent.CONTENT_DATA;
		private HorizontalAlign horizAlign = HorizontalAlign.CENTER;
		
		public Builder(Object value){
			this.value = value; 
		}
		
		public Builder colspan(int colspan){
			this.colspan = colspan; 
			return this; 
		}		
		
		public Builder contentType(ReportContent type){
			this.content = type; 
			return this; 
		}
		
		public Builder horizAlign(HorizontalAlign align){
			this.horizAlign = align; 
			return this; 
		}
		
		public CellProps build(){
			return new CellProps(this); 
		}
	}
}