/**
 * 
 */
package net.sf.reportengine.out.neo;

/**
 * @author dragos balan
 *
 */
public class ExcelXmlOutputFormat extends AbstractOutputFormat{
	
	public static final String DEFAULT_EXCEL_XML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/excel/xml"; 
	
	public ExcelXmlOutputFormat() {
		super(DEFAULT_EXCEL_XML_TEMPLATES_CLASS_PATH);
	}
}
