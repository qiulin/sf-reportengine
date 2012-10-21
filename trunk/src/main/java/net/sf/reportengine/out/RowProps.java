/*
 * Created on Aug 7, 2006
 * Author : dragos balan 
 */
package net.sf.reportengine.out;

import net.sf.reportengine.core.ReportContent;

/**
 * <p>
 * output row properties.<br/>
 * FOR FUTURE USE !
 * </p>
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3 
 */
public class RowProps {
    
	/**
	 * 
	 */
    private ReportContent reportContent =  ReportContent.CONTENT_DATA;
    
    /**
     * 
     */
    public RowProps(){
    	
    }
    
    /**
     * 
     * @param content
     */
    public RowProps(ReportContent content){
    	setContent(content); 
    }
    
    /**
     * 
     * @return
     */
    public ReportContent getContent() {
        return reportContent;
    }
    
    /**
     * 
     * @param content
     */
    public void setContent(ReportContent content) {
        this.reportContent = content;
    }
}
