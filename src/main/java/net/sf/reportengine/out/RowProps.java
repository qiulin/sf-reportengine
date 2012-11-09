/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;

/**
 * <p>row properties holder</p>
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 */
public class RowProps {
    
	/**
	 * 
	 */
    private final ReportContent reportContent;
    
    /**
     * 
     */
    public RowProps(){
    	this(ReportContent.CONTENT_DATA);
    }
    
    /**
     * 
     * @param content
     */
    public RowProps(ReportContent content){
    	this.reportContent = content; 
    }
    
    /**
     * 
     * @return
     */
    public ReportContent getContent() {
        return reportContent;
    }
    
    
}
