/**
 * 
 */
package net.sf.reportengine.out;

import java.io.OutputStream;

/**
 * Abstract output for all xml based report output
 * 
 * @author dragos balan (dragos.balan@gmail.com)
 *
 */
public abstract class AbstractXmlReportOutput extends AbstractOutput {
	
	public final static String REPORT_TAG_NAME = "report";
    public final static String REPORT_TITLE_TAG_NAME = "title";
    public final static String REC_DETAILS_TAG_NAME = "row";
    public final static String DATA_TAG_NAME = "data";
    public final static String TABLE_HEADER_TAG_NAME = "table-header";
    public final static String REC_DETAILS_HEADER_TAG_NAME = "row-header";
    
    public final static String ATTR_ENGINE_VERSION = "engineVersion";
	public final static String ATTR_COLSPAN = "colspan";
    public final static String ATTR_COLUMN_NUMBER = "colNumber";
	public final static String ATTR_CONTENT_TYPE = "contentType";
	
	public final static String ATTR_ROW_NUMBER = "rowNumber";
	
    public AbstractXmlReportOutput(OutputStream outputStream){
    	super(outputStream);
    }
}
