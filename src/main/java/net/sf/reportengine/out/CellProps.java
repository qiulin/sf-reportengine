/*
 * Created on 25.03.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;

/**
 * report cell properties bean
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 *
 */
public class CellProps {

    
    private int colspan = 1;
    private int rowspan = 1; 
    private ReportContent content = ReportContent.CONTENT_DATA;
    private Object value;
    
    public CellProps(){
        this("");
    }
    
    public CellProps(Object value){
    	this(value, 1);
    }
    
    public CellProps(Object value, int colspan){
    	this(value, colspan, 1, ReportContent.CONTENT_DATA);
    }
    
    public CellProps(Object value, int colspan, int rowspan){
    	this(value, colspan, rowspan, ReportContent.CONTENT_DATA); 
    }
    
    public CellProps(Object value, int colspan, int rowspan, ReportContent contentType){
    	setColspan(colspan);
    	setRowspan(rowspan);
        setContentType(contentType);
        setValue(value);
    }
    
   

    public int getColspan(){
        return colspan;
    }
    
    public void setColspan(int colspan){
        this.colspan = colspan;
    }
    
    public ReportContent getContentType(){
        return content;
    }
    
    public void  setContentType(ReportContent contentType){
        this.content = contentType;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
    
    @Override
    public boolean equals(Object another){
    	boolean result = false;
    	if(another instanceof CellProps){
    		CellProps anotherAsCP = (CellProps)another;
    		result = getValue().equals(anotherAsCP.getValue()) 
    		        && getColspan() == anotherAsCP.getColspan()
    		        && getRowspan() == anotherAsCP.getRowspan()
    		        && getContentType() == anotherAsCP.getContentType();
    	}
    	return result;
    }
    
    @Override
    public int hashCode() {
    	int result =3;
    	result = 97 * result  + value.hashCode();
    	result = 97 * result + colspan;
    	result = 97 * result + rowspan; 
    	result = 97 * result + content.hashCode();  
    	return result;
    }
    
    public String toString(){
    	StringBuffer result = new StringBuffer("CP[");
    	result.append(value);
    	result.append(",cspan="+colspan);
    	result.append(",rspan="+rowspan);
    	result.append(",cntnt="+content);
    	result.append("]");
    	return result.toString();
    }

	public int getRowspan() {
		return rowspan;
	}

	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
        
}