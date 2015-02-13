/**
 * 
 */
package net.sf.reportengine.out.neo;

import java.io.Writer;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;

/**
 * @author dragos balan
 *
 */
public class DefaultReportOutput implements NewReportOutput {
	
	/**
	 * the default class path  for freemarker templates
	 */
	public final static String DEFAULT_HTML_TEMPLATES_CLASS_PATH = "/net/sf/reportengine/freemarker/html"; 
	
	/**
	 * the freemarker configuration
	 */
	private final Configuration fmConfig; 
	
	/**
	 * the output writer
	 */
	private final Writer writer; 
	
	

	/**
	 * 
	 */
	public DefaultReportOutput(Writer writer){
		
		this.writer = writer;
		
		fmConfig= new Configuration(); 
		fmConfig.setObjectWrapper(new DefaultObjectWrapper()); 
		fmConfig.setTemplateLoader(
				new ClassTemplateLoader(getClass(), 
										DEFAULT_HTML_TEMPLATES_CLASS_PATH)); 
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
		// TODO Auto-generated method stub

	}
	
	public Writer getWriter() {
		return writer;
	}
	
	public Configuration getFmConfig(){
		return fmConfig; 
	}

}
