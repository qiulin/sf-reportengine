/*
 Date: 17-11-2004
 */
package net.sf.reportengine.out;

import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;

import net.sf.reportengine.config.HorizontalAlign;
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
    private static final String DEFAULT_INSIDE_PAGE_CSS_DECLARATION = "<style type=\"text/css\"> "+
    																		"body {background-color: #ffffff } " +
    																		"table.reportTable {} "+
    																		"th {background-color: grey } "+
    																		"tr.even {background-color: #f5f5f5; }"+
    																		"tr.odd {background-color: #ffffff}" +
    																	"</style>";
    
    private static final String ENCODING_DECLARATION = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
    private static final String HTML_HEAD_START = "<html><head><title>report</title>";
    private static final String HTML_HEAD_END = "</head><body>";
    
    private static final String HTML_END = "</body></html>";
    
    
    
    /**
     * flag for standalone pages (default true)                    <br/>
     * If this flag is true then a full html page will be generated <br/>
     * otherwise only the table will be written as output
     */
    private boolean isStandalonePage = true;
    
    /**
     * the path to a css file
     */
    private String cssPath ; 
    
    /**
     * empty constructor
     */
    public HtmlOutput(){}
    
    /**
     * 
     * @param fileName
     */
    public HtmlOutput(String fileName){
    	super(fileName); 
    }
    
    
    /**
     * html output
     * @param out	the result stream
     */
    public HtmlOutput(OutputStream out) {
       super(out, SYSTEM_FILE_ENCODING);
    }
    
    /**
     * 
     * @param out		the output stream
     * @param encoding	the encoding
     */
    public HtmlOutput(OutputStream out, String encoding) {
        super(out, encoding);
    }
    
    /**
     * 
     * @param writer
     */
    public HtmlOutput(Writer writer){
    	super(writer);
    }
    
   /**
    * open the output
    */
    public void open() {
        try {
            super.open();
            if(isStandalonePage){
            	Writer writer = getWriter(); 
            	writer.write(HTML_HEAD_START);
            	writer.write(LINE_SEPARATOR);
            	writer.write(ENCODING_DECLARATION);
            	writer.write(LINE_SEPARATOR); 
            	if(cssPath != null){
            		writer.write("<link rel=\"stylesheet\" href=\"");
            		writer.write(cssPath); 
            		writer.write("\">"); 
            	}else{
            		writer.write(DEFAULT_INSIDE_PAGE_CSS_DECLARATION); 
            	}
            	writer.write(HTML_HEAD_END);
            }
            getWriter().write("<table border=\"0\" cellpading=\"0\" cellspacing=\"0\" class=\"reportTable\">");
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    /**
     * closes the output
     */
    public void close() {
        try {
            getWriter().write("</table>");
            if(isStandalonePage){
            	getWriter().write(HTML_END);
            }
            super.close();//this closes the writer
        } catch (IOException ioExc) {
        	throw new RuntimeException(ioExc);
        }
    }
    
    /*
     *  (non-Javadoc)
     * @see net.sf.reportengine.out.IReportOutput#newLine()
     */
    public void startRow(RowProps rowProperties) {
        try {
            super.startRow(rowProperties);
            StringBuilder buffer = new StringBuilder("<tr class=\"");
            if(getRowCount() %2 ==0){
                buffer.append("even");
            }else{
                buffer.append("odd");
            }
            buffer.append("\" >");
            getWriter().write(buffer.toString());            
        } catch (IOException ioExc) {
        	throw new ReportOutputException(ioExc);
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
        	throw new ReportOutputException(ioExc);
        }
    }
    
    /**
     * 
     */
    public void output(CellProps cellProps) {
        String toWrite = purifyData(cellProps.getValue());
        
        try {
        	ReportContent contentType = cellProps.getContentType();
        	
            StringBuilder strContent = new StringBuilder("<td align =\"");
            strContent.append(translateHorizAlign(cellProps.getHorizontalAlign())); 
            strContent.append("\" colspan=\"");
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
            throw new ReportOutputException(ioExc);
        }
    }
    
    
    private String translateHorizAlign(HorizontalAlign horizAlign){
        return HorizontalAlign.CENTER.equals(horizAlign)? "center" :
        					HorizontalAlign.LEFT.equals(horizAlign) ?  "left" : "right";
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

	public String getCssPath() {
		return cssPath;
	}

	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}
}