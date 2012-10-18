/*
 * Created on 25.03.2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;

/**
 * report cell properties 
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public class CellProps {

    
    private int colspan = 1;
    
    private ReportContent content = ReportContent.CONTENT_DATA;
    private Object value;
    
    /**
     * 
     */
    public CellProps(){
        this("");
    }
    
    /**
     * 
     * @param value
     */
    public CellProps(Object value){
    	this(value, 1);
    }
    
    /**
     * 
     * @param value
     * @param colspan
     */
    public CellProps(Object value, int colspan){
    	this(value, colspan, ReportContent.CONTENT_DATA);
    }
    
    /**
     * 
     * @param value
     * @param colspan
     * @param contentType
     */
    public CellProps(Object value, int colspan, ReportContent contentType){
    	setColspan(colspan);
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
    		        && getContentType() == anotherAsCP.getContentType();
    	}
    	return result;
    }
    
    @Override
    public int hashCode() {
    	int result =3;
    	result = 97 * result  + value.hashCode();
    	result = 97 * result + colspan;
    	result = 97 * result + content.hashCode();  
    	return result;
    }
    
    public String toString(){
    	StringBuffer result = new StringBuffer("CP[");
    	result.append(value);
    	result.append(",cspan="+colspan);
    	result.append(",cntnt="+content);
    	result.append("]");
    	return result.toString();
    }        
}