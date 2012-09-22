/*
 Date: 17-11-2004
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;

import net.sf.reportengine.core.ReportConstants;
import net.sf.reportengine.core.ReportContent;


/**
 * A simple implementation of the IReportOutput having 
 * as result a html page.
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 * @since 0.2
 */
public class HtmlReportOutput extends AbstractOutput {
    
    public static final String DEFAULT_ODD_ROW_BG_COLOR = "#F5F5F5";
    public static final String DEFAULT_EVEN_ROW_BG_COLOR = "#FFFFFF";
    
    private static final String LINE_SEPARATOR = System.getProperty("line.separator");

    private static final String HTML_START ="<html><head><title>report</title></head><body>";
    private static final String HTML_END = "</body></html>";

    /**
     * flag for standalone pages (default true)                    <br/>
     * If this flag is true then a full html page will be generated <br/>
     * otherwise only the table will be written as output
     */
    private boolean isStandalonePage = true;
    
    /**
     * the constructor of this class 
     * 
     * @param out	the output stream (the result stream)
     */
    public HtmlReportOutput(OutputStream out) {
        super(out);
    }
    
    /*
     *  (non-Javadoc)
     * @see net.sf.repoengine.out.Outputter#start()
     */
    public void open() {
        try {
            super.open();
            getOutputStream().write((isStandalonePage ? HTML_START : "").getBytes());
            getOutputStream().write(("<table border=\"0\" cellpading=\"0\" cellspacing=\"0\">").getBytes());
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
            getOutputStream().write("</table>".getBytes());
            getOutputStream().write((isStandalonePage ? HTML_END : "").getBytes());
            getOutputStream().close();
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
            StringBuffer buffer = new StringBuffer("<tr bgcolor=\"");
            if(getRowCount() %2 ==0){
                buffer.append(DEFAULT_EVEN_ROW_BG_COLOR);
            }else{
                buffer.append(DEFAULT_ODD_ROW_BG_COLOR);
            }
            buffer.append("\" >");
            getOutputStream().write(buffer.toString().getBytes());
            
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
            getOutputStream().write(("</tr>"+LINE_SEPARATOR).getBytes());
            
        }catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    
    public void output(CellProps cellProps) {
        
        String toWrite = purifyData(cellProps.getValue());
        
        try {
        	ReportContent contentType = cellProps.getContentType();
            StringBuffer strContent = new StringBuffer("<td align =\"center\" colspan=\"" + cellProps.getColspan()+ "\" rowspan=\""+cellProps.getRowspan()+"\" >");
            if(contentType == ReportContent.CONTENT_COLUMN_HEADERS){
                strContent.append("<b>");
            }
            strContent.append(toWrite);
            if(contentType == ReportContent.CONTENT_COLUMN_HEADERS){
                strContent.append("</b>");
            }
            strContent.append("</td>");
            getOutputStream().write(strContent.toString().getBytes());
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