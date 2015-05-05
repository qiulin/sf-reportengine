/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.Writer;

/**
 * @author dragos balan
 *
 */
public class ExcelXmlReportOutput extends FreemarkerReportOutput {
	
	public static final String DEFAULT_EXCEL_XML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/excel/xml"; 
	
	
	public ExcelXmlReportOutput(Writer writer){
		this(writer, new ExcelXmlOutputFormat()); 
	}
	
	public ExcelXmlReportOutput(Writer writer, ExcelXmlOutputFormat outputFormat){
		super(writer, outputFormat);
	}

	@Override
	public String getTemplatesClasspath() {
		return DEFAULT_EXCEL_XML_TEMPLATES_CLASS_PATH;
	}
	
}
