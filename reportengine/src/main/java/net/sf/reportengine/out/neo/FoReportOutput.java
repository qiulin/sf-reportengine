package net.sf.reportengine.out.neo;

import java.io.Writer;

public class FoReportOutput extends FreemarkerReportOutput {
	
	/**
	 * the default FO templates class path
	 */
	public final static String DEFAULT_FO_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/neo/fo";
	
	
	public FoReportOutput(Writer writer){
		this(writer, new FoOutputFormat()); 
	}
	
	public FoReportOutput(Writer writer, FoOutputFormat outputFormat){
		super(writer, outputFormat);
	}
	
	@Override
	public String getTemplatesClasspath() {
		return DEFAULT_FO_TEMPLATES_CLASS_PATH;
	}

}
