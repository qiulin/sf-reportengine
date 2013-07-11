/**
 * 
 */
package net.sf.reportengine.out;

import java.io.Writer;

/**
 * Abstract output for all xml based report output
 * 
 * @author dragos balan (dragos dot balan at gmail dot com)
 * @since 0.3
 */
public abstract class AbstractXmlOutput extends AbstractCharBasedOutput {
	
	public final static String TAG_REPORT = "report";
	
    public final static String TAG_TITLE = "title";
    
    public final static String TAG_DATA_ROW = "data-row";
    
    public final static String TAG_DATA_CELL = "data-cell";
    
    public final static String TAG_TABLE_HEADER = "table-header";
    
    public final static String TAG_HEADER_ROW = "header-row";
    
    public final static String ATTR_ENGINE_VERSION = "engineVersion";
    
	public final static String ATTR_COLSPAN = "colspan";
	
    public final static String ATTR_COLUMN_NUMBER = "colNumber";
    
	public final static String ATTR_ROW_NUMBER = "rowNumber";
	
	public final static String ATTR_HORIZ_ALIGN = "horizAlign";
	
	public final static String ATTR_VERT_ALIGN = "vertAlign";
	
	/**
	 * empty constructor
	 */
	public AbstractXmlOutput(){}
	
	/**
	 * 
	 * @param fileName
	 */
	public AbstractXmlOutput(String fileName){
		super(fileName); 
	}
	
	/**
     * 
     * @param writer
     */
    public AbstractXmlOutput(Writer writer){
    	super(writer);
    }
}
