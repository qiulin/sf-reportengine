/*
 Date: 17-11-2004
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import net.sf.reportengine.core.ReportContent;


/**
 * Simple implementation of the IReportOutput having 
 * as result a html page.
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class HtmlOutput extends AbstractOutput {
    
    public static final String DEFAULT_ODD_ROW_BG_COLOR = "#F5F5F5";
    public static final String DEFAULT_EVEN_ROW_BG_COLOR = "#FFFFFF";
    
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final String ENCODING_DECLARATION = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
    private static final String HTML_START ="<html><head><title>report</title>"+ENCODING_DECLARATION+"</head><body>";
    private static final String HTML_END = "</body></html>";
    
    
    /**
     * flag for standalone pages (default true)                    <br/>
     * If this flag is true then a full html page will be generated <br/>
     * otherwise only the table will be written as output
     */
    private boolean isStandalonePage = true;
    
    /**
     *
     * 
     * @param out	the output stream (the result stream)
     */
    public HtmlOutput(OutputStream out) {
        super(out);
    }
    
    /**
     * 
     * @param writer
     */
    public HtmlOutput(Writer writer){
    	super(writer);
    }
    
    /*
     *  (non-Javadoc)
     * @see net.sf.repoengine.out.Outputter#start()
     */
    public void open() {
        try {
            super.open();
            if(isStandalonePage){
            	getWriter().write(HTML_START);
            }
            getWriter().write("<table border=\"0\" cellpading=\"0\" cellspacing=\"0\">");
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see net.sf.repoengine.out.IEngineOut#stop()
     */
    public void close() {
        try {
            super.close();
            getWriter().write("</table>");
            getWriter().write(isStandalonePage ? HTML_END : "");
            getWriter().close();
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see net.sf.reportengine.out.IReportOutput#newLine()
     */
    public void startRow() {
        
        try {
            super.startRow();
            StringBuilder buffer = new StringBuilder("<tr bgcolor=\"");
            if(getRowCount() %2 ==0){
                buffer.append(DEFAULT_EVEN_ROW_BG_COLOR);
            }else{
                buffer.append(DEFAULT_ODD_ROW_BG_COLOR);
            }
            buffer.append("\" >");
            getWriter().write(buffer.toString());
            
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see net.sf.reportengine.out.IReportOutput#endLine()
     */
    public void endRow(){
        try{
            super.endRow();
            getWriter().write("</tr>"+LINE_SEPARATOR);
            
        }catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    /**
     * 
     */
    public void output(CellProps cellProps) {
        String toWrite = purifyData(cellProps.getValue());
        
        try {
        	ReportContent contentType = cellProps.getContentType();
        	
            StringBuilder strContent = new StringBuilder("<td align =\"center\" colspan=\"");
            strContent.append(cellProps.getColspan());
            strContent.append("\" rowspan=\"1\" >");
            
            if(contentType == ReportContent.CONTENT_COLUMN_HEADERS){
                strContent.append("<b>");
            }
            strContent.append(toWrite);
            if(contentType == ReportContent.CONTENT_COLUMN_HEADERS){
                strContent.append("</b>");
            }
            strContent.append("</td>");
            getWriter().write(strContent.toString());
        } catch (IOException ioExc) {
            throw new RuntimeException(ioExc);
        }
    }
    
    
    /**
     * checks null & other impossible to print values
     */
    protected String purifyData(Object val) {
    	String result = null;
    	if(val != null){
    		if(IReportOutput.WHITESPACE.equals(val)){
    			result = "&nbsp;";
    		}else{
    			result = val.toString();
    		}
    	}else{
    		result = getNullsReplacement();
    	}
        return result;
    }
}