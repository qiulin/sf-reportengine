package net.sf.reportengine.out.neo;


import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Map;

import net.sf.reportengine.out.ReportOutputException;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.TemplateException;

/**
 * @author dragos balan
 *
 */
public class TestImplForReportOutput extends NewReportOutput {
	
	/**
	 * the default class path  for freemarker templates
	 */
	public final static String DEFAULT_TEMPLATES_CLASS_PATH = "/freemarker"; 
	
	/**
	 * the freemarker configuration
	 */
	private final Configuration fmConfig; 
	
	/**
	 * the output writer
	 */
	private final Writer writer = new StringWriter(); 
	
	

	/**
	 * 
	 */
	public TestImplForReportOutput(){
		
		fmConfig= new Configuration(); 
		fmConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		fmConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), 
										DEFAULT_TEMPLATES_CLASS_PATH)); 
	}
	
	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#open()
	 */
	public void open() {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see net.sf.reportengine.out.neo.NewReportOutput#close()
	 */
	public void close() {
		try {
			writer.close();
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}

	}
	
	public String getBuffer(){
		return ((StringWriter)writer).getBuffer().toString(); 
	}

	public void output(String templateName, Map rootModel) {
		try {
			fmConfig.getTemplate(templateName).process(rootModel, writer);
		} catch (TemplateException e) {
			throw new ReportOutputException(e); 
		} catch (IOException e) {
			throw new ReportOutputException(e); 
		}
	}

	@Override
	public OutputFormat getFormat() {
		return null;
	}
}
