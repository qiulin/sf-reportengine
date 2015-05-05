/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.Writer;

/**
 * @author dragos balan
 *
 */
public class HtmlReportOutput extends FreemarkerReportOutput {
	
	/**
	 * the default class path  for freemarker templates
	 */
	public final static String DEFAULT_HTML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/html"; 
	
	
	public HtmlReportOutput(Writer writer){
		this(writer, new HtmlOutputFormat()); 
	}
	
	public HtmlReportOutput(Writer writer, HtmlOutputFormat outputFormat){
		super(writer, outputFormat);
	}

	@Override
	public String getTemplatesClasspath() {
		return DEFAULT_HTML_TEMPLATES_CLASS_PATH;
	}
}
