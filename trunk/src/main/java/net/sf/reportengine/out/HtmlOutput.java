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
 * The available options are :
 * 	1. Construct a html having inside default css (no control whatsoever)
 *  2. Construct a html based on outside css. (full view control)
 *  This outside css should contain any of following css definitions: 
 *  	table.reportTable
 *  	tr.reportTableHeader
 *  	tr.even
 *  	tr.odd
 *  
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.2
 */
public class HtmlOutput extends AbstractOutput {
    /**
     * css style
     */
    private static final String DEFAULT_INSIDE_PAGE_CSS_DECLARATION = new StringBuilder()
    																	.append("<style type=\"text/css\"> ")
    																	.append("body {background-color: #ffffff } ")
    																	.append("table.reportTable {border-width:thin; border-style:solid; border-color: #000000;} ")
    																	.append("tr.reportTableHeader {color: #ffffff; background-color: #000000; font-weight: bold;} ")
    																	.append("tr.even {color: #000000; background-color: #c0c0c0 }") //#f5f5f5;
    																	.append("tr.odd {color: #000000; background-color: #ffffff}" )
    																	.append("</style>")
    																	.toString();
    /**
     * the utf-8 content type meta declaration
     */
    private static final String ENCODING_DECLARATION = "<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />";
    
    /**
     * html start
     */
    private static final String HTML_HEAD_START = "<html><head><title>report</title>";
    
    /**
     * html head end and body start
     */
    private static final String HTML_HEAD_END = "</head><body>";
    
    /**
     * html end
     */
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
     * html output to the specified file
     * 
     * @param fileName
     */
    public HtmlOutput(String fileName){
    	super(fileName); 
    }
    
    
    /**
     * html output to the specified stream using default system encoding
     * 
     * @param out	the result stream
     */
    public HtmlOutput(OutputStream out) {
       super(out, SYSTEM_FILE_ENCODING);
    }
    
    /**
     * html output to the specified stream using the encoding specified as parameter
     * 
     * @param out		the output stream
     * @param encoding	the encoding
     */
    public HtmlOutput(OutputStream out, String encoding) {
        super(out, encoding);
    }
    
    /**
     * html output to the specified writer
     * 
     * @param writer
     */
    public HtmlOutput(Writer writer){
    	super(writer);
    }
    
   /**
    * opens the output
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
            getWriter().write("<table cellpading=\"0\" cellspacing=\"0\" class=\"reportTable\">");
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
    
    /**
     * start report row
     */
    public void startRow(RowProps rowProperties) {
        try {
            super.startRow(rowProperties);
            StringBuilder buffer = new StringBuilder();
            if(ReportContent.CONTENT_COLUMN_HEADERS.equals(rowProperties.getContent())){
            	buffer.append("<tr class=\"reportTableHeader\"/>");
            }else{
            	buffer.append("<tr class=\"");
                if(getRowCount() %2 ==0){
                    buffer.append("even");
                }else{
                    buffer.append("odd");
                }
                buffer.append("\" >");
            }
            getWriter().write(buffer.toString());            
        } catch (IOException ioExc) {
        	throw new ReportOutputException(ioExc);
        }
    }
    
   /**
    * end row
    */
    public void endRow(){
        try{
            super.endRow();
            if(ReportContent.CONTENT_COLUMN_HEADERS.equals(getCurrentRowProps().getContent())){
            	getWriter().write("</th>");
            }else{
            	getWriter().write("</tr>"); 
            }
            getWriter().write(LINE_SEPARATOR);
        }catch (IOException ioExc) {
        	throw new ReportOutputException(ioExc);
        }
    }
    
    /**
     * output cell contents
     */
    public void output(CellProps cellProps) {
        String toWrite = purifyData(cellProps.getValue());
        
        try {
            StringBuilder strContent = new StringBuilder("<td align =\"");
            strContent.append(translateHorizAlign(cellProps.getHorizontalAlign())); 
            strContent.append("\" colspan=\"");
            strContent.append(cellProps.getColspan());
            strContent.append("\" rowspan=\"1\" >");
            strContent.append(toWrite);
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
    
    /**
     * getter for css file path
     * @return
     */
	public String getCssPath() {
		return cssPath;
	}
	
	/**
	 * setter for css file path
	 * @param cssPath
	 */
	public void setCssPath(String cssPath) {
		this.cssPath = cssPath;
	}
}