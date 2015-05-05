package net.sf.reportengine.out.neo;


import java.io.StringWriter;

/**
 * @author dragos balan
 *
 */
public class MockReportOutput extends FreemarkerReportOutput {
	
	/**
	 * the default class path  for freemarker templates
	 */
	public final static String DEFAULT_TEMPLATES_CLASS_PATH = "/freemarker"; 
	
	/**
	 * 
	 */
	public MockReportOutput(){
		super(new StringWriter(), new MockOutputFormat());
	}
	
	
	
	public String getBuffer(){
		return ((StringWriter)getWriter()).getBuffer().toString(); 
	}

	@Override
	public OutputFormat getFormat() {
		return null;
	}

	@Override
	public String getTemplatesClasspath() {
		return DEFAULT_TEMPLATES_CLASS_PATH;
	}
}
